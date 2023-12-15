package StudentCode;
import AopsGui.*;

public class CircleMover extends Actor {
    public CircleMover(int x, int y) {
        super(x, y);
    }

    public void act() {
        turn(0.1);
        move(10);
    }
}
