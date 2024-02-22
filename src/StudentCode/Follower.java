package StudentCode;
import AopsTheater.*;

public class Follower extends Actor {
    public void act() {
        double mouseX = Events.getMouseX();
        double mouseY = Events.getMouseY();

        double xDif = getX() - mouseX;
        double yDif = getY() - mouseY;

        if (Math.sqrt(xDif * xDif + yDif * yDif) > 1) {
            turnTowards(mouseX, mouseY);
            move(1);
        }

    }
}
