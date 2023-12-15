import AopsGui.Actor;

import java.util.ArrayList;
import java.util.List;

public class Aops2d {
    private final List<Actor> actors;
    private static double mouseX;
    private static double mouseY;

    public Aops2d() {
        actors = new ArrayList<>();
    }

    public void act(double mouseX, double mouseY) {
        Aops2d.mouseX = mouseX;
        Aops2d.mouseY = mouseY;

        for (Actor actor : actors) {
            actor.act();
        }
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
}
