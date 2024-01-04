package AopsGui;

import StudentCode.*;

public class Aops2DRunner {

    private final Stage stage;

    public Aops2DRunner() {
        stage = new MyStage();
    }

    public String act(double mouseX, double mouseY, boolean isMousePressed, String[] keysPressed) {
        Gui.updateKeys(keysPressed);
        Gui.updateMouse(mouseX, mouseY, isMousePressed);

        try {
            stage.act();

            for (Actor actor : stage.getActors()) {

                    actor.act();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return JsonConversion.getActorJason(stage.getActors());
    }

    public Stage getStage() {
        return stage;
    }

    public String getActors() {
        return JsonConversion.getActorJason(stage.getActors());
    }
}
