// Setup PIXI Window Information
let app;

// Sync variables for timing out
let isUpdateJavaInProgress = false;
let intervalId;


// State variables
const originalSprites = new Map();
let stageWidth;
let stageHeight;

// Pixi Interactive Variables
let clickX, clickY;
let mouseX = 0;
let mouseY = 0;
let isMouseDown = false;
const keysPressed = new Set();


(async function () {
    // Load the Java Runner
    await cheerpjInit();
    const lib = await cheerpjRunLibrary("/app/build/AopsApp.jar");
    const Aops2DRunner = await lib.Aops2DRunner;
    const runner = await new Aops2DRunner();

    // Set up the Pixi App to match the Java Stage
    stage = await runner.getStage();
    setUpPixi(await stage.getWidth(), await stage.getHeight(), await stage.getImage());


    // Main Runner. Ask the Java project to perform one update loop.
    // Take the results from the Java update and update PIXI to match
    async function updateJava() {
        if (isUpdateJavaInProgress) {
            console.warn('Previous act call still in progress.');
            return;
        }

        isUpdateJavaInProgress = true;

        try {
            const actors = await Promise.race([
                runner.act(mouseX, mouseY, isMouseDown, Array.from(keysPressed)),
                new Promise((_, reject) => {
                    setTimeout(() => {
                        reject(new Error('Timeout exceeded.'));
                    })
                })
            ]);

            updatePIXI(actors);
        } catch (error) {
            console.error('Act failed: ', error.message);
        } finally {
            isUpdateJavaInProgress = false;
        }

        intervalId = requestAnimationFrame(updateJava)
    }
    // intervalId = setInterval(updateJava, 50);
    intervalId = requestAnimationFrame(updateJava);

    setTimeout(() => {
        //clearInterval(intervalId);
        clearAnimationFrame(intervalId)
        console.log('Interval stopped after 20 seconds.');

    }, 20000);
})();


function updatePIXI(actors) {
    const actorArray = JSON.parse(actors);

    const actorMap = new Map();
    actorArray.forEach(actor => {
        const filename = 'images/' + actor.image;
        const sprite = new PIXI.Sprite(PIXI.Texture.from(filename));
        sprite.x = actor.x;
        sprite.y = actor.y;
        sprite.image = filename;
        sprite.rotation = actor.rotation;
        actorMap.set(actor.uuid, sprite);
    });

    synchronizeSprites(actorMap);
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
            originalSprite.x = incomingSprite.x;
            originalSprite.y = incomingSprite.y;
            originalSprite.pivot.x = incomingSprite.width / 2;
            originalSprite.pivot.y = incomingSprite.height / 2;
            originalSprite.rotation = incomingSprite.rotation;
            if (originalSprite.image !== incomingSprite.image) {
                originalSprite.texture = PIXI.Texture.from(incomingSprite.image)
                originalSprite.image = incomingSprite.image;
                originalSprite.pivot.x = originalSprite.width / 2;
                originalSprite.pivot.y = originalSprite.height / 2;
            }
        } else {
            // New sprite
            originalSprites.set(uuid, incomingSprite);
            app.stage.addChild(incomingSprite);
            incomingSprite.pivot.x = incomingSprite.width / 2;
            incomingSprite.pivot.y = incomingSprite.height / 2;
        }
    }
}


function setUpPixi(stageWidth, stageHeight, stageImage) {
    app = new PIXI.Application({
        width: stageWidth,
        height: stageHeight
    });

    document.body.appendChild(app.view);
    addMouseListener();
    addKeyboardListener();

    const filename = 'images/' + stageImage;
    const backgroundSprite = new PIXI.Sprite(PIXI.Texture.from(filename));
    backgroundSprite.width = stageWidth;
    backgroundSprite.height = stageHeight;
    app.stage.addChild(backgroundSprite);
}

function timeout(ms, promise) {
    return new Promise((resolve, reject) => {
        const timeoutId = setTimeout(() => {
            clearTimeout(timeoutId);
            reject(new Error('Timeout exceeded'));
        }, ms);

        promise.then(
            (result) => {
                clearTimeout(timeoutId);
                resolve(result);
            },
            (error) => {
                clearTimeout(timeoutId);
                reject(error);
            }
        );
    });
}

function addMouseListener() {
    app.view.addEventListener('pointermove', (event) => {
        mouseX = event.clientX - app.view.getBoundingClientRect().left;
        mouseY = event.clientY - app.view.getBoundingClientRect().top;
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

function simplifyKeyEventName(event) {
    return event.code.replace(/Digit|Key|Arrow/g, '').toUpperCase();
}

