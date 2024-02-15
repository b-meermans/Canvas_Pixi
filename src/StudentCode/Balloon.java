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

        // TODO Remove the UUID here, it was for testing
        setUuid("A");
    }



    public void act() { AopsTheaterHandler.addMethodToStack(19);
        if (Events.isKeyPressed(1,"up")) {
            AopsTheaterHandler.changeStatement(21);                    yVelocity -= ACCELERATION;
        }

        if (Events.isKeyPressed(1,"right")) {
            AopsTheaterHandler.changeStatement(25);                    xVelocity += ACCELERATION;
        }

        if (Events.isKeyPressed(1,"left")) {
            AopsTheaterHandler.changeStatement(29);                    xVelocity -= ACCELERATION;
        }

        AopsTheaterHandler.changeStatement(32);                yVelocity += GRAVITY;
        AopsTheaterHandler.changeStatement(33);                xVelocity *= FRICTION;

        AopsTheaterHandler.changeStatement(35);                setLocation(getX() + xVelocity, getY() + yVelocity);

        if (Events.isKeyPressed(1,"5")) {
            while (true) {AopsTheaterHandler.changeStatement(38);        AopsTheaterHandler.exitIfNeeded();}
        }

        if (Events.isKeyPressed(1, "6")) {
            AopsTheaterHandler.changeStatement(42);                    recur();
        }
AopsTheaterHandler.removeMethodFromStack();    }

    public void recur() { AopsTheaterHandler.addMethodToStack(46);
AopsTheaterHandler.changeStatement(47);        recur();
AopsTheaterHandler.removeMethodFromStack();    }
}
