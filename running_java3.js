// Setup PIXI Window Information
let app;


// Interactive Variables
originalSprites = new Map();
let clickX, clickY;
let mouseX = 0;
let mouseY = 0;

(async function () {
    // Start up Cheerpj
    await cheerpjInit();

    // Build and add the PIXI Application to the screen
    if (!app) {
        app = new PIXI.Application({
            width: 640,
            height: 360
        });
    }
    document.body.appendChild(app.view);

    // Keep track of the mouse (single player)
    app.view.addEventListener('pointermove', (event) => {
        mouseX = event.clientX - app.view.getBoundingClientRect().left;
        mouseY = event.clientY - app.view.getBoundingClientRect().top;
    });

    // Load the Java Runner
    const lib = await cheerpjRunLibrary("/app/build/AopsApp.jar");
    const Aops2DRunner = await lib.Aops2DRunner;
    const runner = await new Aops2DRunner();

    // Main Runner. Ask the Java project to perform one update loop.
    // Take the results from the Java update and update PIXI to match
    async function updateJava() {
        try {
            // Tell Java where the mouse is and to perform one update
            await runner.act(mouseX, mouseY);

            // Ask for the current state of the actors
            const actors = await runner.getActors();
            const size = await actors.size();

            // Convert the Java list of Actors into a map of Sprites
            const javaActors = new Map();
            for (let i = 0; i < size; i++) {
                const actor = await actors.get(i);
                const x = await actor.getX();
                const y = await actor.getY();
                const rotation = await actor.getRotation();
                const id = await actor.getID();

                const sprite = PIXI.Sprite.from("images/balloon.png");
                sprite.id = id;
                sprite.x = x;
                sprite.y = y;
                sprite.rotation = rotation;
                javaActors.set(id, sprite);
            }

            // Sync up the results from Java onto PixiJS
            synchronizeSprites(javaActors);
        } catch (error) {
            // console.error(error);
        }
    }
    setInterval(updateJava, 20);
})();


// Takes in a map of Sprites that Java calculated
// Some sprites may be new, some may not be around anymore (removed)
// Check for all removed sprites - and remove them from Pixi
// For all other sprites - update them on Pixi
function synchronizeSprites(incomingSprites) {
    // Remove sprites that are not present in the incoming list
    for (const [uuid, originalSprite] of this.originalSprites) {
        if (!incomingSprites.has(uuid)) {
            originalSprite.destroy();
            this.originalSprites.delete(uuid);
        }
    }

    // Add new sprites or update existing ones
    for (const [uuid, incomingSprite] of incomingSprites) {

        if (this.originalSprites.has(uuid)) {
            // Update existing sprite
            const originalSprite = this.originalSprites.get(uuid);
            originalSprite.x = incomingSprite.x;
            originalSprite.y = incomingSprite.y;
            originalSprite.pivot.x = originalSprite.width / 2;
            originalSprite.pivot.y = originalSprite.height / 2;
            originalSprite.rotation = incomingSprite.rotation;
        } else {

            this.originalSprites.set(uuid, incomingSprite);
            // Add the new sprite to the screen or perform other actions
            app.stage.addChild(incomingSprite);
            incomingSprite.pivot.x = incomingSprite.width / 2;
            incomingSprite.pivot.y = incomingSprite.height / 2;
        }
    }
}