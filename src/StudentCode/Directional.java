package StudentCode;

import AopsGui.Actor;
import AopsGui.Gui;

public class Directional extends Actor {
    public void act() {
        if (Gui.isKeyPressed("SPACE")) {
            move(30);
        }


        if (Gui.isKeyPressed("up")) {
            setLocation(getX(), getY() - 1);
        }
        if (Gui.isKeyPressed("DowN")) {
            setLocation(getX(), getY() + 1);
        }
        if (Gui.isKeyPressed("lefT")) {
            setLocation(getX() - 1, getY());
        }
        if (Gui.isKeyPressed("RIGHT")) {
            setLocation(getX() + 1, getY());
        }
    }
}
