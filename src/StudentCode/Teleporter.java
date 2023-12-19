package StudentCode;

import AopsGui.*;

public class Teleporter extends Actor {

    public void act() {
        if (Gui.isMousePressed()) {
            setLocation(Gui.getMouseX(), Gui.getMouseY());
        }
    }
}
