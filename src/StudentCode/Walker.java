package StudentCode;
import AopsTheater.*;


public class Walker extends Actor {
    public Walker() {
        super("plane.png");
        turn(Math.random() * 360);
    }

    public void act() {
        move(1);
        if (getOneIntersectingActor(Walker.class) != null) {
            turn(180);
        }
        if (getX() > getStage().getWidth() + 80) {
            setLocation(0, getY());
        }
    }
}
