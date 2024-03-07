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

    private final SpatialHashMap spatialHashMap;

    public Stage(int width, int height) {
        this(width, height, DEFAULT_IMAGE);
    }

    public Stage(int width, int height, String imageName) {
        this.width = width;
        this.height = height;
        this.image = imageName;
        actors = new ArrayList<>();
        spatialHashMap = new SpatialHashMap(this, 20, 20);
    }

    public void act() {}

    public void addActor(Actor actor, double x, double y) {
        actors.add(actor);
        actor.setLocation(x, y);
        actor.setStage(this);
        actor.addedToStage(this);
        spatialHashMap.insertNew(actor);
    }
    public void removeActor(Actor actor) {
        spatialHashMap.remove(actor);
        actors.remove(actor);
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
    SpatialHashMap getSpatialHashMap() {
        return spatialHashMap;
    }

    public<A extends Actor> List<A> getObjectsInRadius(Class<A> cls, double x, double y, double radius) {
        Coordinate coordinate = new Coordinate(x,y);
        return spatialHashMap.getAllWithinRadius(cls, coordinate, radius);
    }

    public<A extends Actor> List<A> getKNearestObjectsInRadius(Class<A> cls, double x, double y, double radius, int k) {
        Coordinate coordinate = new Coordinate(x, y);
        return spatialHashMap.getKNearestWithinRadius(cls, coordinate, radius, k);
    }
}
