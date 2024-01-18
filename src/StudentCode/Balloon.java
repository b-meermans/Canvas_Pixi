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

    public void act() { Aops2DRunner.addMethodToStack(19);
        if (Gui.isKeyPressed("up")) {
            Aops2DRunner.changeStatement(21);                    yVelocity -= ACCELERATION;
        }

        if (Gui.isKeyPressed("right")) {
            Aops2DRunner.changeStatement(25);                    xVelocity += ACCELERATION;
        }

        if (Gui.isKeyPressed("left")) {
            Aops2DRunner.changeStatement(29);                    xVelocity -= ACCELERATION;
        }

        Aops2DRunner.changeStatement(32);                yVelocity += GRAVITY;
        Aops2DRunner.changeStatement(33);                xVelocity *= FRICTION;

        Aops2DRunner.changeStatement(35);                setLocation(getX() + xVelocity, getY() + yVelocity);

        if (Gui.isKeyPressed("5")) {
            while (true) {Aops2DRunner.changeStatement(38);        Aops2DRunner.exitIfNeeded();}
        }

        if (Gui.isKeyPressed("6")) {
            Aops2DRunner.changeStatement(42);                    recur();
        }
Aops2DRunner.removeMethodFromStack();    }

    public void recur() { Aops2DRunner.addMethodToStack(46);
Aops2DRunner.changeStatement(47);        recur();
Aops2DRunner.removeMethodFromStack();    }
}
