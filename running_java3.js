// Setup PIXI Window Information
let app = new PIXI.Application({
    width: 640,
    height: 360
});

document.body.appendChild(app.view);




// Interactive Variables
const sprites = [];
let clickX, clickY;
let changed = false;



// Demo Sprites
function demoSprites() {
    for (let i = 0; i < 100; i++) {
        let sprite = PIXI.Sprite.from('./images/balloon.png');
        sprite.y = i - sprite.width / 2;
        sprite.spins = true;
        app.stage.addChild(sprite);
        sprites.push(sprite);
    }

    let sprite = PIXI.Sprite.from('./images/plane.png');
    app.stage.addChild(sprite);
    sprite = PIXI.Sprite.from('./images/plane.png');
    sprite.y = 100;
    app.stage.addChild(sprite);
}

demoSprites();




function updateChildren() {
    // Re-write. I want to check to see if there are new children to be added, old children to be removed, and then
    // update the correct values of the same children.

    // Remove list
    // Add list
    // Update list

    app.stage.removeChildren();

    sprites.forEach (sprite => {
        app.stage.addChild(sprite);
    });

}


function updateSprites() {
    let elapsed = 0.0;

    app.ticker.add((delta) => {
        if (changed) {
            updateChildren();
            changed = false;
        }

        elapsed += delta;
        sprites.forEach (sprite => {
            if (clickX && clickY && sprite.spins) {
                sprite.rotation = Math.atan2(clickY - sprite.y, clickX - sprite.x);
                sprite.x = app.renderer.width / 2 + Math.cos(elapsed/(app.renderer.width / 4)) * app.renderer.width / 2.0;
            }
        });
    });
}

updateSprites();


function mouseListener() {

    app.view.addEventListener('pointerdown', (event) => {
        clickX = event.clientX - app.view.getBoundingClientRect().left;
        clickY = event.clientY - app.view.getBoundingClientRect().top;

        let sprite = PIXI.Sprite.from('./images/balloon.png');
        sprite.y = clickY - sprite.height / 2;
        sprite.x = clickX - sprite.width / 2;

        app.stage.addChild(sprite);
        sprites.push(sprite);
        changed = true;
        console.log(sprite);
    });
}

mouseListener();