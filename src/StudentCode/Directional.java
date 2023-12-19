package StudentCode;

import AopsGui.Actor;
import AopsGui.AopsGui;

public class Directional extends Actor {
    public void act() {
        if (AopsGui.isKeyPressed("SPACE")) {
            move(30);
        }


        if (AopsGui.isKeyPressed("up")) {
            setLocation(getX(), getY() - 1);
        }
        if (AopsGui.isKeyPressed("DowN")) {
            setLocation(getX(), getY() + 1);
        }
        if (AopsGui.isKeyPressed("lefT")) {
            setLocation(getX() - 1, getY());
        }
        if (AopsGui.isKeyPressed("RIGHT")) {
            setLocation(getX() + 1, getY());
        }
    }
}
