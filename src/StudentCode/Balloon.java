package StudentCode;

import AopsGui.Actor;
import AopsGui.Aops2DRunner;
import AopsGui.Gui;

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
        if (Gui.isKeyPressed("up")) {
            yVelocity -= ACCELERATION;
        }

        if (Gui.isKeyPressed("right")) {
            xVelocity += ACCELERATION;
        }

        if (Gui.isKeyPressed("left")) {
            xVelocity -= ACCELERATION;
        }

        yVelocity += GRAVITY;
        xVelocity *= FRICTION;

        setLocation(getX() + xVelocity, getY() + yVelocity);

        if (Gui.isKeyPressed("5")) {
            while (true) {Aops2DRunner.exitIfNeeded();
            }
        }

        if (Gui.isKeyPressed("6")) {
            recur();
        }
    }

    public void recur() {Aops2DRunner.addMethodToStack();
        recur();

    Aops2DRunner.removeMethodFromStack();}

}
