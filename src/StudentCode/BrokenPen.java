package StudentCode;

import AopsTheater.*;

public class BrokenPen extends Actor {
    public BrokenPen() {
        super("pen.png");
    }

    public void act() {
        moveTowardsMouse();
        if (Events.isMousePressed()) {
            drawDot();
        }
    }

    public void moveTowardsMouse() {
        double mouseX = Events.getMouseX();
        double mouseY = Events.getMouseY();

        if (Math.hypot(mouseX - getX(), mouseY - getY()) > 1) {
            turnTowards(mouseX, mouseY);
            move(1);
            moveTowardsMouse();
            setRotation(0);
        }
    }

    public void drawDot() {
        getStage().addActor(new Dot(), getX(), getY());
    }


}
