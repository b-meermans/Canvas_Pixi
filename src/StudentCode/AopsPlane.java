package StudentCode;

import AopsTheater.Actor;

public class AopsPlane extends Actor {
    public AopsPlane() {
        setImage("plane.png");
    }

    public void act() {
        move(1);

        if (getX() > getStage().getWidth()) {
            setLocation(0, getY());
        }
    }
}
