import AopsGui.*;
import StudentCode.*;
import Utility.JsonConversion;

public class Aops2DRunner {

    private final Stage stage;

    public Aops2DRunner() {
        stage = new MyStage();
    }

    public String act(double mouseX, double mouseY, boolean isMousePressed, String[] keysPressed) {
        AopsGui.updateKeys(keysPressed);
        AopsGui.updateMouse(mouseX, mouseY, isMousePressed);

        stage.act();

        for (Actor actor : stage.getActors()) {
            actor.act();
        }


        return JsonConversion.getActorJason(stage.getActors());
    }

    public Stage getStage() {
        return stage;
    }
}
