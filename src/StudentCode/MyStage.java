package StudentCode;

import AopsGui.Stage;

public class MyStage extends Stage {

    public MyStage() {
        super(640, 360);
        addActor(new Follower(300, 200));
        addActor(new CircleMover(300, 200));
        addActor(new Directional(40, 100));

        for (int i = 0; i <= 360; i += 36) {
            addActor(new Walker(0, i));
        }
    }
}
