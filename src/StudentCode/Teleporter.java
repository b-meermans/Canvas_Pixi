package StudentCode;

import AopsGui.*;

public class Teleporter extends Actor {

    public void act() {
        if (AopsGui.isMousePressed()) {
            setLocation(AopsGui.getMouseX(), AopsGui.getMouseY());
        }
    }
}
