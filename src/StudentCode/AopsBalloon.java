package StudentCode;

import AopsTheater.Actor;
import AopsTheater.Events;

import java.awt.*;

public class AopsBalloon extends Actor {

    private long startTime;

    public AopsBalloon() {
        super("balloon.png");
        setTint(new Color(100, 200, 50));
        startTime = System.currentTimeMillis();
    }

    public void act() {
        long elapsedTime = System.currentTimeMillis() - startTime;

        if (elapsedTime < 5000) {
            setLocation(getX(), getY() - 2);
        } else if (elapsedTime > 6000 && elapsedTime < 8000) {
            setLocation(getX(), getY() - 1);
        }

        if (getY() < 500) {
            setLocation(getX() + 1, getY() + 1);
        }

        if (getX() >= getStage().getWidth()) {
            setLocation(0, getY());
        }
    }
}
