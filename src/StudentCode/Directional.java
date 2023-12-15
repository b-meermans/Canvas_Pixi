package StudentCode;

import AopsGui.Actor;
import AopsGui.AopsGui;

public class Directional extends Actor {
    public Directional(int x, int y) {
        super(x, y);
    }

    public void act() {
        if (AopsGui.isKeyPressed("arrowup")) {
            setLocation(getX(), getY() - 1);
        }
        if (AopsGui.isKeyPressed("arrowdown")) {
            setLocation(getX(), getY() + 1);
        }
        if (AopsGui.isKeyPressed("arrowleft")) {
            setLocation(getX() - 1, getY());
        }
        if (AopsGui.isKeyPressed("arrowright")) {
            setLocation(getX() + 1, getY());
        }
    }
}
