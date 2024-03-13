package StudentCode;

import AopsTheater.*;

public class MyStage extends Stage {

    public MyStage() {
        super(800, 600, "sky.png");



        addText(new Text("Hello, World!"), 400, 100);
        addSound("alpha.mp3");
        addActor(new BrokenPen(), 10, 10);
    }
}
