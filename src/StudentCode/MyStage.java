package StudentCode;

import AopsTheater.*;

public class MyStage extends Stage {

    public MyStage() {
        super(800, 600, "sky.png");
        addActor(new AopsBalloon(), 100, 500);
        addActor(new StudentBalloon(), 200, 500);
        addActor(new AopsPlane(), 0, 100);


//        for (int i = 0; i < getHeight() / 3; i += getHeight() / 10.0) {
//            for (int j = 0; j <= getWidth(); j+= getWidth() / 10.0) {
//                addActor(new Walker(), j, i);
//
//            }
//        }
//
//        addActor(new Follower(), 300, 200);
//        addActor(new CircleMover(), 300, 200);
//        addActor(new Directional(), getWidth() / 3, getHeight() / 2);
//        addActor(new ImageChanger(), 50, 500);
//        addActor(new Balloon(), getWidth() / 2.0, getHeight() / 2.0);
//        addActor(new BrokenPen(), 0, 0);


//        addText(new Text("Hello, World!"), 400, 100);
//        addSound("alpha.mp3");
//        addActor(new AnimatedActor(), 0, 0);

    }
}
