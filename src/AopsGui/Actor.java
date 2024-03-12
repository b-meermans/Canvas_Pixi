package AopsGui;//

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class Actor {
    private static final String DEFAULT_IMAGE_FILE_NAME = "AoPS.png";
    private Stage stage;

    // TODO Get the uuid out of here. Do not want students to see it.
    private UUID uuid;
    private double x;
    private double y;
    private double degrees;
    private AopsImage image;

    private Collider collider;

    public Actor() {
        this(DEFAULT_IMAGE_FILE_NAME);
    }

    public Actor(String imageFileName) {
        this.image = new AopsImage(imageFileName);
        uuid = UUID.randomUUID();
        collider = new RectangularCollider(this);
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public void act() {}

    public void move(double distance) {
        Coordinate previousCoordinate = new Coordinate(x, y);
        x += Math.cos(Math.toRadians(degrees)) * distance;
        y += Math.sin(Math.toRadians(degrees)) * distance;
        getStage().getSpatialHashMap().update(this, previousCoordinate);
    }

    public void turn(double degrees) {
        this.degrees += degrees;
        this.degrees %= 360; // degrees between -360 and 360
    }

    public void turnTowards(double x, double y) {
        degrees = Math.toDegrees(Math.atan2(y - this.y, x - this.x));
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setLocation(double x, double y) {
        Coordinate previousCoordinate = new Coordinate(this.x, this.y);
        this.x = x;
        this.y = y;
        getStage().getSpatialHashMap().update(this, previousCoordinate);
    }

    public String getID() {
        return uuid.toString();
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRotation() {
        return this.degrees;
    }

    public void setRotation(double degrees) {
        this.degrees = degrees % 360;
    }

    public void setImage(AopsImage image) {
        this.image = image;
    }
    public void setImage(String filename) {
        setImage(new AopsImage(filename));
    }
    public AopsImage getImage() {
        return image;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Actor otherActor = (Actor)o;
            return this.uuid.toString().equals(otherActor.uuid.toString());
        }
        return false;
    }

    public int hashCode() {
        return uuid.hashCode();
    }

    public String toString() {
        return "AopsGui.Actor{x=" + this.x + ", y=" + this.y + ", rotation=" + this.degrees + "}";
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addedToStage(Stage stage) {
    }

    public<A extends Actor> List<A> getIntersectingObjects(Class<A> cls) {
        if (stage == null) {
            return new ArrayList<>();
        }
        List<A> actors = stage.getObjectsInRadius(cls, getX(), getY(),collider.getMaxDimension());
        actors.removeIf(actor -> this.equals(actor) || !isIntersecting(actor));
        return actors;
    }

    public<A extends Actor> A getOneIntersectingObject(Class<A> cls) {
        List<A> intersectingActors = getIntersectingObjects(cls);
        if (intersectingActors.isEmpty()) {
            return null;
        }
        return intersectingActors.get(0);
    }

    public boolean isIntersecting(Actor actor) {
        if (actor == null || actor.stage == null) {
            return false;
        }
        return Colliders.isIntersecting(this.collider, actor.collider);
    }

    public<A extends Actor> List<A> getActorsWithinRadius(Class<A> cls, double radius) {
        if (stage == null) {
            return new ArrayList<>();
        }
        List<A>
    }
}