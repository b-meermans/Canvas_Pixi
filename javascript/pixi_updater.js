
// State variables
const originalSprites = new Map();

// Pixi Interactive Variables
let clickX, clickY;
let mouseX = 0;
let mouseY = 0;
let isMouseDown = false;
const keysPressed = new Set();
let fullSize = true;

// Setup PIXI Window Information
let app;
let runner;
let isRunning = false;
let currentScale = 1;

window.addEventListener('message', function (event) {
  if (event.data && event.data.action === 'loadPIXI') {
      loadPIXI()
      .then(result => {
        window.parent.postMessage({ action: 'loadPIXI', result: result }, '*');
      })
      .catch(error => {
            console.error('Error in async method:', error);
      });
  } else if (event.data && event.data.action === 'stepPIXI') {
      stepPIXI()
      .then(result => {
        window.parent.postMessage({ action: 'stepPIXI', result: result }, '*');
      })
      .catch(error => {
            console.error('Error in async method:', error);
      });
    } else if (event.data && event.data.action === 'toggleRun') {
      toggleRun()
      .then(result => {
        window.parent.postMessage({ action: 'toggleRun', result: result }, '*');
      })
      .catch(error => {
            console.error('Error in async method:', error);
      });
    }
});

async function loadPIXI() {

    destroyCurrentPixi();
    originalSprites.clear();
    isRunning = false;

    let lib;
    if (window.parent && window.parent.getLib) {
        lib = window.parent.getLib();
    }

    if (!lib) {
        console.error("The CheerpJ library was not available");
    }

    const Aops2DRunner = await lib.AopsGui.Aops2DRunner;
    runner = await new Aops2DRunner();

    // Set up the Pixi App to match the Java Stage
    const javaStage = await runner.getStage();
    setUpPixi(await javaStage.getWidth(), await javaStage.getHeight(), await javaStage.getImage());

    updatePIXI(await runner.getActors());
    updatePIXI(await runner.getActors());

    new Audio('sounds/incorrect.mp3').play();
}

window.addEventListener('unhandledrejection', function (event) {
  event.preventDefault();
}, false);

async function runPIXI() {
    stepPIXI();
    if (isRunning) {
        requestAnimationFrame(() => runPIXI());
    }
}

async function toggleRun() {
    new Audio('sounds/correct.mp3').play();

    isRunning = !isRunning;
    if (isRunning) {
        var pixiIframe = parent.document.getElementById('pixi-iframe');
        if (pixiIframe) {
            pixiIframe.focus();
        }
        runPIXI();
    }
}

async function stepPIXI() {
    const actors = await runner.act(1, mouseX, mouseY, isMouseDown, Array.from(keysPressed));

    if (actors === null) {
        loadPIXI();
        return;
    }

    updatePIXI(actors);
}

function destroyCurrentPixi() {
    if (app) {
        app.destroy(true);
    }
}

function updatePIXI(actors) {
    const actorArray = JSON.parse(actors);

    const actorMap = convertActorsToSprites(actorArray);
    synchronizeSprites(actorMap);
}

function convertActorsToSprites(actors) {
    const actorMap = new Map();
    actors.forEach(actor => {
        const filename = 'images/' + actor.image;
        const sprite = new PIXI.Sprite(PIXI.Texture.from(filename));
        sprite.x = actor.x;
        sprite.y = actor.y;
        sprite.image = filename;
        sprite.rotation = actor.rotation;
        actorMap.set(actor.uuid, sprite);
    });

    return actorMap;
}

// Update PIXI's sprites with the currently desired sprites
function synchronizeSprites(incomingSprites) {
    // Some PIXI sprites may have been removed, take them off PIXI
    for (const [uuid, originalSprite] of originalSprites) {
        if (!incomingSprites.has(uuid)) {
            originalSprite.destroy();
            originalSprites.delete(uuid);
        }
    }

    // Every remaining sprite is either old or new
    for (const [uuid, incomingSprite] of incomingSprites) {

        if (originalSprites.has(uuid)) {
            // Old sprite - update it.
            const originalSprite = originalSprites.get(uuid);
            if (originalSprite.image !== incomingSprite.image) {
                originalSprite.texture = PIXI.Texture.from(incomingSprite.image);
                originalSprite.image = incomingSprite.image;
            }

            originalSprite.x = incomingSprite.x;
            originalSprite.y = incomingSprite.y;
            originalSprite.pivot.x = incomingSprite.width / 2;
            originalSprite.pivot.y = incomingSprite.height / 2;
            originalSprite.rotation = incomingSprite.rotation;
        } else {
            // New sprite
            incomingSprite.texture.on('update', () => {
                incomingSprite.pivot.y = incomingSprite.height / 2;
                incomingSprite.pivot.x = incomingSprite.width / 2;
            });
            app.stage.addChild(incomingSprite);
            originalSprites.set(uuid, incomingSprite);

            incomingSprite.uuid = uuid;
            incomingSprite.eventMode = 'dynamic';
            incomingSprite.on('click', onClick);
            incomingSprite.on('rightup', onRightClick);
        }
    }
}

async function onRightClick() {
    if (!isRunning) {
        const methods = await runner.getMethodsJSON(this.uuid);
        console.log(methods);
    }
}

async function onClick() {
    if (!isRunning) {
        // Grabs all of the method names, comes back as an object [] for now.
        // As the demo, the array has index 0 as the class name
        // The next indices are the methods under that class.
        // If there is a parent class, that class name will be an element at some point
        // Ie: [Person, void act(), String getName(), Actor, void setLocation(int, int), Object]
        const methods = await runner.getMethodInformation(this.uuid);

        // For a demonstration - grab the first actual method
        // We need to strip away everything but the method name
        const testGrab = await methods[1].toString();
        const spaceIndex = testGrab.indexOf(' ');
        const parenIndex = testGrab.indexOf('(');
        const methodName = testGrab.substring(spaceIndex + 1, parenIndex);

        // Which Actor has this UUID?
        sprite = await runner.getSpriteByUUID(this.uuid);
        // Ask the Actor to use the method
        await sprite[methodName]();
        // Redraw everything in case this modified other Actors
        updatePIXI(await runner.getActors());
    }
}

function setUpPixi(stageWidth, stageHeight, stageImage) {
    app = new PIXI.Application({
        width: stageWidth,
        height: stageHeight
    });

    currentScale = 1;

    app.stage.originalWidth = stageWidth;
    app.stage.originalHeight = stageHeight;

    document.body.appendChild(app.view);
    addMouseListener();
    addKeyboardListener();

    const filename = 'images/' + stageImage;
    const backgroundSprite = new PIXI.Sprite(PIXI.Texture.from(filename));
    backgroundSprite.width = stageWidth;
    backgroundSprite.height = stageHeight;
    app.stage.addChild(backgroundSprite);

    document.addEventListener('contextmenu', (event) => {
        // Check if the mouse is inside the Pixi application
        const appBounds = app.view.getBoundingClientRect();
        const mouseX = event.clientX;
        const mouseY = event.clientY;

        if (
            mouseX >= appBounds.left &&
            mouseX <= appBounds.right &&
            mouseY >= appBounds.top &&
            mouseY <= appBounds.bottom
        ) {
            // Prevent the default context menu
            event.preventDefault();
        }
    });
}

function addMouseListener() {
    app.view.addEventListener('pointermove', (event) => {
        const relativeMouseX = event.clientX - app.view.getBoundingClientRect().left;
        const relativeMouseY = event.clientY - app.view.getBoundingClientRect().top;

        const scaledMouse = app.stage.toLocal(new PIXI.Point(relativeMouseX, relativeMouseY));
        mouseX = scaledMouse.x;
        mouseY = scaledMouse.y;
    });

    app.view.addEventListener('pointerdown', () => {
        isMouseDown = true;
    });

    app.view.addEventListener('pointerup', () => {
        isMouseDown = false;
    });
}

function addKeyboardListener() {
    document.addEventListener('keydown', (event) => {
        keyCode = simplifyKeyEventName(event);
        keysPressed.add(keyCode);
    });

    document.addEventListener('keyup', (event) => {
        keyCode = simplifyKeyEventName(event);
        keysPressed.delete(keyCode);
    });
}

window.addEventListener('keydown', (event) => {
    if (event.key === '-') {
        currentScale *= 0.9;

        const newWidth = app.stage.originalWidth * currentScale
        const newHeight = app.stage.originalHeight * currentScale;

        app.renderer.resize(newWidth, newHeight);
        app.stage.scale.set(currentScale, currentScale);
    }

    else if (event.key === '+' || event.key === '=') {
        currentScale *= 1.1;

        const newWidth = app.stage.originalWidth * currentScale
        const newHeight = app.stage.originalHeight * currentScale;

        app.renderer.resize(newWidth, newHeight);
        app.stage.scale.set(currentScale, currentScale);
    }
});

function simplifyKeyEventName(event) {
    return event.code.replace(/Digit|Key|Arrow/g, '').toUpperCase();
}
