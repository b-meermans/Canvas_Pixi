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
            while (true) {}
        }

        if (Gui.isKeyPressed("6")) {
            recur();
        }

        try {
            if (Gui.isKeyPressed("5")) {
                while (true) {}
            }
        } catch (Exception e) {
            System.out.println("This should not occur, as the hidden inserted catch should be first in line");
        }

    }

    public void recur() {
        recur();
    }
}
