public class Follower extends Actor {
    public Follower(int x, int y) {
        super(x, y);
    }

    public void act() {
        turnTowards(Runner2D.getMouseX(), Runner2D.getMouseY());
        move(1);
    }
}
