package AopsGui;

import StudentCode.CircleMover;
import StudentCode.Follower;
import StudentCode.Walker;

import java.util.ArrayList;
import java.util.List;

public abstract class Stage {
    private static final String DEFAULT_IMAGE = "dots.png";

    private final int width;
    private final int height;
    private String image;

    private final List<Actor> actors;

    public Stage(int width, int height) {
        this(width, height, DEFAULT_IMAGE);
    }

    public Stage(int width, int height, String imageName) {
        this.width = width;
        this.height = height;
        this.image = imageName;
        actors = new ArrayList<>();
    }

    public void act() {}

    public void addActor(Actor actor, double x, double y) {
        actors.add(actor);
        actor.setLocation(x, y);
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
