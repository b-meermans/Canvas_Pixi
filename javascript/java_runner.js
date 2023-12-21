let lib;

loadCheerp();
reset();

async function loadCheerp() {
    await cheerpjInit();
    lib = await cheerpjRunLibrary('/app/build/AopsApp.jar');

    const pixiIframe = document.getElementById('pixi-iframe')
    if (pixiIframe && pixiIframe.contentWindow) {
        pixiIframe.contentWindow.loadPIXI();
    }
}

function getLib() {
    return lib;
}

function reset() {
    const pixiIframe = document.getElementById('pixi-iframe');
    const runButton = document.getElementById('runPauseButton');
    const stepButton = document.getElementById('stepButton');

    if (pixiIframe && pixiIframe.contentWindow) {
        pixiIframe.contentWindow.postMessage({ action: 'loadPIXI' }, '*');
        runButton.textContent = 'Run';
        stepButton.disabled = false;
    } else {
        console.error('Iframe not found.');
    }
}

function step() {
    const pixiIframe = document.getElementById('pixi-iframe');
    if (pixiIframe && pixiIframe.contentWindow) {
        pixiIframe.contentWindow.postMessage({ action: 'stepPIXI' }, '*');
    } else {
        console.error('Iframe not found.');
    }
}

function toggleRun() {
    const pixiIframe = document.getElementById('pixi-iframe');
    const runButton = document.getElementById('runPauseButton');
    const stepButton = document.getElementById('stepButton');

    if (pixiIframe && pixiIframe.contentWindow) {
        pixiIframe.contentWindow.postMessage({ action: 'toggleRun' }, '*');
        if (runButton.textContent === 'Run') {
            runButton.textContent = 'Pause';
            stepButton.disabled = true;
        } else {
            runButton.textContent = 'Run';
            stepButton.disabled = false;
        }
    } else {
        console.error('Iframe not found.');
    }
}

