package StudentCode;
import AopsTheater.*;

public class Walker extends Actor {
    public Walker() {
        super("plane.png");
    }

    public void act() {
        move(1);
        if (getX() > getStage().getWidth() + 80) {
            setLocation(0, getY());
        }
    }
}
