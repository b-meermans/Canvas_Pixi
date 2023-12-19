package StudentCode;
import AopsGui.*;

public class Walker extends Actor {
    public Walker() {
        super("plane.png");
    }

    public void act() {
        move(1);
        if (getX() > getStage().getWidth() + 80) {
            setX(0);
        }
    }
}
