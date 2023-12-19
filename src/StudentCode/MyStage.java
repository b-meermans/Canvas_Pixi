package StudentCode;

import AopsGui.Stage;

import java.awt.*;

public class MyStage extends Stage {

    public MyStage() {
        super(800, 600, "sky.png");

        for (int i = 0; i < getHeight() / 3; i += getHeight() / 10.0) {
            for (int j = 0; j <= getWidth(); j+= getWidth() / 10.0) {
                addActor(new Walker(), j, i);

            }
        }

        addActor(new Follower(), 300, 200);
        addActor(new CircleMover(), 300, 200);
        addActor(new Directional(), getWidth() / 3, getHeight() / 2);
        addActor(new Teleporter(), 0, 0);
        addActor(new ImageChanger(), 50, 500);
        addActor(new Balloon(), getWidth() / 2.0, getHeight() / 2.0);


    }
}
