const canvas = document.getElementById('gridCanvas');
const ctx = canvas.getContext('2d');

let jsActors = [];
let textInput = [];

const image = new Image();
const image_url = 'https://upload.wikimedia.org/wikipedia/commons/1/12/Right_arrow.svg';
image.src = image_url;

let updated = false;
let mouseX = 0;
let mouseY = 0;

function update() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  drawGrid();
  jsActors.forEach(drawActor);
}

function drawGrid() {
  const gridSize = 20;
  const gridColor = '#ccc';

  for (let x = 0; x < canvas.width; x += gridSize) {
    for (let y = 0; y < canvas.height; y += gridSize) {
      ctx.strokeStyle = gridColor;
      ctx.strokeRect(x, y, gridSize, gridSize);
    }
  }
}

function animate() {
    if (updated) {
        update();
        updated = false;
    }
    requestAnimationFrame(animate);
}

function drawActor(actor) {
    const width = 10;
    const height = 10;

    ctx.save();
    ctx.translate(actor.x, actor.y);
    ctx.rotate(actor.r);
    ctx.drawImage(image, -width / 2, -height / 2, width, height);
    ctx.restore();
}

animate();



function handleMouseMove(event) {
    mouseX = event.clientX - canvas.getBoundingClientRect().left;
    mouseY = event.clientY - canvas.getBoundingClientRect().top;
}

canvas.addEventListener('mousemove', handleMouseMove);


function submitForm() {
    event.preventDefault();
    const inputValue = document.getElementById('inputText').value;
    textInput.push(inputValue);

    document.getElementById('inputText').value = "";
}


(async function () {
    await cheerpjInit();

    // Load the main Java Class
    const lib = await cheerpjRunLibrary("/app/build/AopsApp.jar");
    const Runner2D = await lib.Runner2D;
    const runner = await new Runner2D();

    // Update the data
    async function updateJava() {
        try {
            await runner.act(mouseX, mouseY);

            const actors = await runner.getActors();
            const size = await actors.size();

            let javaActors = [];
            for (let i = 0; i < size; i++) {
                const actor = await actors.get(i);
                const x = await actor.getX();
                const y = await actor.getY();
                const r = await actor.getRotation();
                javaActors.push({x, y, r});
            }
            jsActors = [...javaActors];

            runner.updateInputStream(textInput);
            textInput = [];

            updated = true;
        } catch (error) {
            // console.error("An error occurred:", error)
        }

        if (textInput.length > 0) {
            try {
                await runner.updateInputStream(textInput);
                textInput = [];
            } catch (error) {

            }
        }
    }
    setInterval(updateJava, 20);


})();