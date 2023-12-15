package AopsGui;

import StudentCode.CircleMover;
import StudentCode.Follower;
import StudentCode.Walker;

import java.util.ArrayList;
import java.util.List;

public abstract class Stage {
    private final int width;
    private final int height;
    private String image;

    private final List<Actor> actors;

    public Stage(int width, int height) {
        this(width, height, null);
    }

    public Stage(int width, int height, String imageName) {
        this.width = width;
        this.height = height;
        this.image = imageName;
        actors = new ArrayList<>();
    }

    public void act() {}

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.setStage(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
