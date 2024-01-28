package StudentCode;

import AopsTheater.*;

public class Directional extends Actor {
    public void act() {
        if (Events.isKeyPressed("SPACE")) {
            move(30);
        }

        if (Events.isKeyPressed("up")) {
            setLocation(getX(), getY() - 1);
        }
        if (Events.isKeyPressed("DowN")) {
            setLocation(getX(), getY() + 1);
        }
        if (Events.isKeyPressed("lefT")) {
            setLocation(getX() - 1, getY());
        }
        if (Events.isKeyPressed("RIGHT")) {
            setLocation(getX() + 1, getY());
        }
    }
}
