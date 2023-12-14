public class Walker extends Actor {
    public Walker(int x, int y) {
        super(x, y);
    }

    public void act() {
        move(1);
        if (getX() > 400) {
            setX(0);
        }
    }
}
