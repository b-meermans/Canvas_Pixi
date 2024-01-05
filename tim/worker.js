
function add(a, b) {
    return a + b;
}

function addALot(a, b) {
    for (let i = 0; i < 1000000000; ++i) {
        a += b;
    }
    return a;
}

function stuck() {
    while (true) {}
}

const executableFunctions = {
    add,
    addALot,
    stuck
}

onmessage = (message) => {
    let data = message.data

    switch (data.type) {
        case "exec": {
            let id = data.execID
            let method = data.method

            let fn = executableFunctions[method]
            if (!fn) {
                postMessage({
                    type: "exec-error",
                    execID: id,
                    error: "Nonexistent function name"
                })
            } else {
                try {
                    let r = executableFunctions[method](...JSON.parse(data.args))

                    postMessage({
                        type: "exec-result",
                        execID: id,
                        result: JSON.stringify(r)
                    })
                } catch (e) {
                    postMessage({
                        type: "exec-error",
                        execID: id,
                        error: e.toString()
                    })
                }
            }

            break
        }
    }
}
