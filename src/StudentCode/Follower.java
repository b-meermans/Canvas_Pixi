package StudentCode;
import AopsTheater.*;

public class Follower extends Actor {
    public void act() {
        double xDif = getX() - Gui.getMouseX();
        double yDif = getY() - Gui.getMouseY();

        if (Math.sqrt(xDif * xDif + yDif * yDif) > 1) {
            turnTowards(Gui.getMouseX(), Gui.getMouseY());
            move(1);
        }

    }
}
