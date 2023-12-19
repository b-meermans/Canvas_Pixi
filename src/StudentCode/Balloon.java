package StudentCode;

import AopsGui.Actor;
import AopsGui.AopsGui;

public class Balloon extends Actor {

    private static final double ACCELERATION = 0.1;
    private static final double GRAVITY = 0.02;
    private static final double FRICTION = 0.97;
    private double xVelocity = 0.0;
    private double yVelocity = 0.0;

    public Balloon() {
        super("balloon.png");
    }

    public void act() {
        if (AopsGui.isKeyPressed("up")) {
            yVelocity -= ACCELERATION;
        }

        if (AopsGui.isKeyPressed("right")) {
            xVelocity += ACCELERATION;
        }

        if (AopsGui.isKeyPressed("left")) {
            xVelocity -= ACCELERATION;
        }

        yVelocity += GRAVITY;
        xVelocity *= FRICTION;

        setLocation(getX() + xVelocity, getY() + yVelocity);
    }

}
