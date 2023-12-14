(async function () {
    await cheerpjInit();

    // Load the main Java Class
    const lib = await cheerpjRunLibrary("/app/build/AopsApp.jar");
    const AopsTest = await lib.AopsTest;
    const aopsMain = await new AopsTest();

    // Sending parameters and receiving return values
    await aopsMain.textIndexNegativeOne();
});