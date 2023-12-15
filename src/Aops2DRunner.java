import AopsGui.*;
import StudentCode.*;

import java.util.List;

public class Aops2DRunner {

    private final Stage stage;

    public Aops2DRunner() {
        stage = new MyStage();
    }

    public void act(double mouseX, double mouseY, String[] keysPressed) {

        for (String key : keysPressed) {
            System.out.println(key);
        }
        AopsGui.updateMouse(mouseX, mouseY);

        stage.act();

        for (Actor actor : stage.getActors()) {
            actor.act();
        }
    }

    public List<Actor> getActors() {
        return stage.getActors();
    }

    public static double getMouseX() {
        return AopsGui.getMouseX();
    }

    public static double getMouseY() {
        return AopsGui.getMouseY();
    }
}
