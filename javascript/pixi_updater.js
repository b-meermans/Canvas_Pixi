
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
let aopsTheater;
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

    const AopsTheaterClass = await lib.AopsTheater.AopsTheater;
    aopsTheater = await AopsTheaterClass.build();

    const theaterState = await aopsTheater.getState();
    setUpPixi(theaterState);
    updatePIXI(theaterState);

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
    const json = await aopsTheater.update(jsonify());

    if (json === null) {
        loadPIXI();
        return;
    }

    updatePIXI(json);
}

function destroyCurrentPixi() {
    if (app) {
        app.destroy(true);
    }
}

function updatePIXI(json) {
    const jsonObject = JSON.parse(json);

    const stageData = jsonObject.stage;
    const actorData = jsonObject.actors;
    const textsData = jsonObject.texts;
    const soundsData = jsonObject.sounds;
    const shapesData = jsonObject.shapes;

    const actorMap = convertActorsToSprites(actorData);
    synchronizeSprites(actorMap);
}

function convertActorsToSprites(actors) {
    const actorMap = new Map();
    actors.forEach(actor => {
        let filename = 'images/' + actor.image;

        if (actor.image.includes("http")) {
            filename = actor.image;
        }
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
        const methods = await aopsTheater.getMethodsJSON(this.uuid);
        console.log(methods);
    }
}

async function onClick() {
    if (!isRunning) {
//        // Grabs all of the method names, comes back as an object [] for now.
//        // As the demo, the array has index 0 as the class name
//        // The next indices are the methods under that class.
//        // If there is a parent class, that class name will be an element at some point
//        // Ie: [Person, void act(), String getName(), Actor, void setLocation(int, int), Object]
//        const methods = await runner.getMethodInformation(this.uuid);
//
//        // For a demonstration - grab the first actual method
//        // We need to strip away everything but the method name
//        const testGrab = await methods[1].toString();
//        const spaceIndex = testGrab.indexOf(' ');
//        const parenIndex = testGrab.indexOf('(');
//        const methodName = testGrab.substring(spaceIndex + 1, parenIndex);
//
//        // Which Actor has this UUID?
//        sprite = await runner.getSpriteByUUID(this.uuid);
//        // Ask the Actor to use the method
//        await sprite[methodName]();
//        // Redraw everything in case this modified other Actors
//        updatePIXI(await runner.getState());
    }
}

function setUpPixi(theaterState) {
    const jsonObject = JSON.parse(theaterState);
    const stageData = jsonObject.stage;

    app = new PIXI.Application({
        width: stageData.width,
        height: stageData.height
    });

    currentScale = 1;

    app.stage.originalWidth = stageData.width;
    app.stage.originalHeight = stageData.height;

    document.body.appendChild(app.view);
    addMouseListener();
    addKeyboardListener();

    let filename = 'images/' + stageData.image;

    if (stageData.image.includes("http")) {
        filename = stageData.image;
    }

    const backgroundSprite = new PIXI.Sprite(PIXI.Texture.from(filename));
    backgroundSprite.width = stageData.width;;
    backgroundSprite.height = stageData.height;
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

function jsonify() {

    // TODO This is just a mock up of jsonifying the events

    const data = {
      numberOfUpdates: 1,
      playerEvents: [
        {
          playerId: 1,
          mouseX: mouseX,
          mouseY: mouseY,
          leftMouseClick: isMouseDown,
          rightMouseClick: isMouseDown,
          pressedKeys: Array.from(keysPressed)
        }
      ]
    };

    const jsonString = JSON.stringify(data, null, 2);
    return jsonString;
}