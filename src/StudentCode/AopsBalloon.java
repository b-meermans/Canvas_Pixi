package StudentCode;

import AopsTheater.Actor;
import AopsTheater.Events;

import java.awt.*;

public class AopsBalloon extends Actor {
    public AopsBalloon() {
        super("balloon.png");
        setTint(new Color(100, 200, 50));
    }

    public void act() {
        long elapsedTime = Events.getElapsedTime();

        if (elapsedTime < 10000) {
            setLocation(getX(), getY() - 2);
        } else if (elapsedTime > 20000 && elapsedTime < 30000) {
            setLocation(getX(), getY() - 1.5);
        }

        if (getY() < 500) {
            setLocation(getX() + 1, getY() + 0.2);
        }

        if (getX() >= getStage().getWidth()) {
            setLocation(0, getY());
        }
    }
}