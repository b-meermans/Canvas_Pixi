// Setup PIXI Window Information
let app;

// State variables
const originalSprites = new Map();

// Pixi Interactive Variables
let clickX, clickY;
let mouseX = 0;
let mouseY = 0;
let isMouseDown = false;
const keysPressed = new Set();

let loaded = false;
let lib;

async function loadPIXI() {
    if (app) {
        app.destroy(true);
    }

    if (!loaded) {
        await cheerpjInit();
        loaded = true;
        lib = await cheerpjRunLibrary("/app/build/AopsApp.jar");
    }

    const Aops2DRunner = await lib.AopsGui.Aops2DRunner;
    const runner = await new Aops2DRunner();

    // Set up the Pixi App to match the Java Stage
    stage = await runner.getStage();
    setUpPixi(await stage.getWidth(), await stage.getHeight(), await stage.getImage());

    requestAnimationFrame(() => updateJava(runner));
}

loadPIXI();

// Asks for the Java actor details, then asks to update PIXI to match
async function updateJava(runner) {
    const actors = await runner.act(mouseX, mouseY, isMouseDown, Array.from(keysPressed));
    updatePIXI(actors);
    requestAnimationFrame(() => updateJava(runner));
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

