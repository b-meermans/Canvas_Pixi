package StudentCode;

import AopsTheater.*;

public class Balloon extends Actor {

    private static final double ACCELERATION = 0.1;
    private static final double GRAVITY = 0.02;
    private static final double FRICTION = 0.97;
    private double xVelocity = 0.0;
    private double yVelocity = 0.0;

    public Balloon() {
        super("balloon.png");
    }

    public void act() { AopsTheaterHandler.addMethodToStack(19);
        if (Gui.isKeyPressed("up")) {
            AopsTheaterHandler.changeStatement(21);                    yVelocity -= ACCELERATION;
        }

        if (Gui.isKeyPressed("right")) {
            AopsTheaterHandler.changeStatement(25);                    xVelocity += ACCELERATION;
        }

        if (Gui.isKeyPressed("left")) {
            AopsTheaterHandler.changeStatement(29);                    xVelocity -= ACCELERATION;
        }

        AopsTheaterHandler.changeStatement(32);                yVelocity += GRAVITY;
        AopsTheaterHandler.changeStatement(33);                xVelocity *= FRICTION;

        AopsTheaterHandler.changeStatement(35);                setLocation(getX() + xVelocity, getY() + yVelocity);

        if (Gui.isKeyPressed("5")) {
            while (true) {
                AopsTheaterHandler.changeStatement(38);        AopsTheaterHandler.exitIfNeeded();}
        }

        if (Gui.isKeyPressed("6")) {
            AopsTheaterHandler.changeStatement(42);                    recur();
        }
AopsTheaterHandler.removeMethodFromStack();    }

    public void recur() { AopsTheaterHandler.addMethodToStack(46);
AopsTheaterHandler.changeStatement(47);        recur();
AopsTheaterHandler.removeMethodFromStack();    }
}
