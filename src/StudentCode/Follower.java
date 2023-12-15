package StudentCode;
import AopsGui.*;

public class Follower extends Actor {
    public Follower(int x, int y) {
        super(x, y);
    }

    public void act() {
        double xDif = getX() - AopsGui.getMouseX();
        double yDif = getY() - AopsGui.getMouseY();

        if (Math.sqrt(xDif * xDif + yDif * yDif) > 1) {
            turnTowards(AopsGui.getMouseX(), AopsGui.getMouseY());
            move(1);
        }

    }
}
