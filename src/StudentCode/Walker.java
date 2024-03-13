package StudentCode;
import AopsTheater.*;

import java.awt.*;

public class Walker extends Actor {
    public Walker() {
        super("plane.png");
    }

    public void act() {
        setTint(Color.GREEN);
        move(1);
        if (getX() > getStage().getWidth() + 80) {
            setLocation(0, getY());
        }
    }
}
