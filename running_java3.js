// Setup PIXI Window Information
let app;


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
        try {
            // Get one update from Java
            const actors = await runner.act(mouseX, mouseY, isMouseDown, Array.from(keysPressed));
            updatePIXI(actors);

//            // Convert the Java list of Actors into a map of Sprites
//            // This uses a lot of awaits to get individual properties - current version uses JSON
//            const javaActors = new Map();
//            for (let i = 0; i < size; i++) {
//                const actor = await actors.get(i);
//                const x = await actor.getX();
//                const y = await actor.getY();
//                const rotation = await actor.getRotation();
//                const id = await actor.getID();
//                let image = "images/" + await actor.getImage();
//                if (image === 'images/AoPS.png') {
//                    image = 'images/balloon.png';
//                }
//                const sprite = PIXI.Sprite.from(image);
//
//                sprite.id = id;
//                sprite.x = x;
//                sprite.y = y;
//                sprite.rotation = rotation;
//                sprite.image = image;
//                javaActors.set(id, sprite);
//            }
//
//            // Sync up the results from Java onto PixiJS
//            synchronizeSprites(actorMap);
        } catch (error) {
            // console.error(error);
        }
    }
    setInterval(updateJava, 20);
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

//
//    PIXI.Loader.shared.add('background', filename).load(() => {
//      const backgroundSprite = new PIXI.Sprite(PIXI.Texture.from('background'));
//      backgroundSprite.x = 0;
//      backgroundSprite.y = 0;
//      app.stage.addChild(backgroundSprite);
//    });
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