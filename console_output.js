const logOutput = document.getElementById('log-output');

// Store the original console.log function
const originalConsoleLog = console.log;

// Override console.log to capture messages
console.log = function(message) {
    // Call the original console.log function
    originalConsoleLog.apply(console, arguments);

    // Append the message to the log output textarea
    if (message !== 'CheerpJ runtime ready') {
        logOutput.value += message;
    }

    // Optionally, scroll to the bottom of the textarea to show the latest logs
    logOutput.scrollTop = logOutput.scrollHeight;

};

function clearLog() {
    // Clear the content of the log output textarea
    logOutput.value = '';
}