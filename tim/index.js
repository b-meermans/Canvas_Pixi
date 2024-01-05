
let worker = null

// Messages that have been sent that haven't yet received a response
// Map from message ID to the resolver
let queuedMessages = new Map()

// Monotonically increasing message ID to discriminate messages
let messageID = 0

function handleWorkerMessage(msg) {
    let data = msg.data

    switch (data.type) {
        case "exec-result": {
            // Execution succeeded, pass the result back please!
            let execID = data.execID
            let queued = queuedMessages.get(execID)


            if (queued) {
                queued.resolve(JSON.parse(data.result))
                queuedMessages.delete(execID)
            }

            return
        }
        case "exec-error": {
            let execID = data.execID
            let queued = queuedMessages.get(execID)


            if (queued) {
                queued.reject(JSON.parse(data.error))
                queuedMessages.delete(execID)
            }

            return
        }
    }
}

function getWorker() {
    if (worker) return worker

    worker = new Worker("worker.js")
    worker.onmessage = handleWorkerMessage
    return worker
}

function terminateWorker() {
    worker.terminate()
    worker = null

    for (let resolver of queuedMessages.values()) {
        resolver.reject("Worker terminated")
    }

    queuedMessages.clear()
}

// Execute a given method name with the given arguments
function workerExec(methodName /* string */, ...args /* arguments as you'd pass normally */) {
    getWorker()

    let execID = messageID++

    worker.postMessage({
        type: "exec",
        method: methodName,
        execID: execID,
        args: JSON.stringify(args)
    })

    return new Promise((resolve, reject) => {
        queuedMessages.set(execID, { resolve, reject })
    })
}

async function doAdd() {
    // Fetch the values from the worker
    let a = Number(document.getElementById("addend1").value)
    let b = Number(document.getElementById("addend2").value)

    let addResult = document.getElementById("add-result")
    addResult.innerText = "pending"

    try {
        // The interesting part!
        let result = await workerExec("add", a, b)

        console.log("hi")

        addResult.innerText = result
    } catch (e) {
        console.log("doAdd threw error: ", e)
    }
}

async function doAddALot() {
    // Fetch the values from the worker
    let a = Number(document.getElementById("addend3").value)
    let b = Number(document.getElementById("addend4").value)

    let addResult = document.getElementById("addalot-result")
    addResult.innerText = "pending"

    try {
        // The interesting part!
        let result = await workerExec("addALot", a, b)

        addResult.innerText = result
    } catch (e) {
        console.log("addALot threw error: ", e)
    }
}

async function stuck() {
    await workerExec("stuck")
}
