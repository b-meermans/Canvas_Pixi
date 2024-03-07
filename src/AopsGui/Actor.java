package AopsGui;//

import java.util.Objects;
import java.util.UUID;

public abstract class Actor {
    private static final String DEFAULT_IMAGE = "AoPS.png";
    private Stage stage;

    // TODO Get the uuid out of here. Do not want students to see it.
    private UUID uuid;
    private double x;
    private double y;
    private double degrees;
    private String image;

    public Actor() {
        this(DEFAULT_IMAGE);
    }

    public Actor(String image) {
        this.image = image;
        uuid = UUID.randomUUID();
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

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
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
        return uuid.toString().hashCode();
    }

    public String toString() {
        return "AopsGui.Actor{x=" + this.x + ", y=" + this.y + ", rotation=" + this.degrees + "}";
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addedToStage(Stage stage) {
    }
}