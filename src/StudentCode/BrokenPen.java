package StudentCode;

import AopsGui.*;

public class BrokenPen extends Actor {
    public BrokenPen() {
        super("pen.png");
    }

    public void act() {
        moveTowardsMouse();
        if (Gui.isMousePressed()) {
            drawDot();
        }
    }

    public void moveTowardsMouse() {
        Aops2DRunner.addMethodToStack();

        if (Math.hypot(Gui.getMouseX() - getX(), Gui.getMouseY() - getY()) > 1) {
            turnTowards(Gui.getMouseX(), Gui.getMouseY());
            move(1);
            moveTowardsMouse();
            setRotation(0);
        }


        Aops2DRunner.removeMethodFromStack();
    }

    public void drawDot() {
        getStage().addActor(new Dot(), getX(), getY());
    }


}
