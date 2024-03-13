package AopsTheater;//

import java.awt.Color;
import java.awt.Rectangle;

import java.util.List;
import java.util.ArrayList;

public abstract class Actor extends AopsTheaterComponent {
    private static final String DEFAULT_IMAGE = "AoPS.png";

    private transient Stage stage;
    private double x;
    private double y;
    private int z;  // TODO Think through how Z will work
    private double rotation;
    private double alpha = 1;
    private boolean isVisible = true;
    private String image;
    private double width = 60;
    private double height = 30;
    // TODO Should color be kept as an int, it'll lower the work needed for Json conversions
    private Color tint = Color.WHITE;

    public Actor() {
        this(DEFAULT_IMAGE);
    }

    public Actor(String image) {
        this.image = image;
        z = AopsTheaterHandler.nextZ();
    }

    public void act() {
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = Math.max(0.0, Math.min(1.0, alpha));
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRotation() {
        return this.rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = (rotation % 360 + 360) % 360;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Color getTint() {
        return tint;
    }

    public void setTint(Color tint) {
        this.tint = tint;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getZ() {
        return z;
    }

    public void move(double distance) {
        double newX = x + Math.cos(Math.toRadians(rotation)) * distance;
        double newY = y + Math.sin(Math.toRadians(rotation)) * distance;
        setLocation(newX, newY);
    }

    public void setLocation(double x, double y) {
        getStage().getSpatialHashGrid().updateLocation(this, x, y);
        this.x = x;
        this.y = y;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void turn(double degrees) {
        rotation += degrees;
    }

    public void turnTowards(double x, double y) {
        rotation = Math.atan2(y - this.y, x - this.x);
    }

    public boolean isIntersecting(Actor other) {
        Rectangle thisRect = new Rectangle((int)x, (int)y, (int)width, (int)height);
        Rectangle otherRect = new Rectangle((int)other.x, (int)other.y, (int)other.width, (int)other.height);
        return thisRect.intersects(otherRect);
    }

    private boolean isInRange(Actor other, double radius) {
        return Math.hypot(other.getX() - this.getX(), other.getY() - this.getY()) <= radius;
    }

    public List<Actor> getIntersectingActors() {
        return getIntersectingActors(Actor.class);
    }

    public <T extends Actor> List<T> getIntersectingActors(Class<T> type) {
        if (getStage() == null) {
            return null;
        }

        List<T> intersectingActors = new ArrayList<>();
        for (Actor other : getStage().getActors()) {
            if (this != other && type.isInstance(other) && isIntersecting(other)) {
                intersectingActors.add(type.cast(other));
            }
        }
        return intersectingActors;
    }

    public List<Actor> getActorsInRange(double radius) {
        return getActorsInRange(radius, Actor.class);
    }

    public <T extends Actor> List<T> getActorsInRange(double radius, Class<T> type) {
        if (getStage() == null) {
            return null;
        }

        List<T> actorsInRange = new ArrayList<>();
        for (Actor other : getStage().getActors()) {
            if (this != other && type.isInstance(other) && this.isInRange(other, radius)) {
                actorsInRange.add(type.cast(other));
            }
        }
        return actorsInRange;
    }

    public Actor getClosestActorInRange() {
        return getClosestActorInRange(Actor.class);
    }

    public <T extends Actor> T getClosestActorInRange(Class<T> type) {
        // TODO Change this to a radius range limit
        if (getStage() == null) {
            return null;
        }

        T closestActor = null;
        double minDistance = Double.MAX_VALUE;

        for (Actor other : getStage().getActors()) {
            if (this != other && type.isInstance(other)) {
                double distance = getDistance(other.getX(), other.getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestActor = type.cast(other);
                }
            }
        }
        return closestActor;
    }

    public double getDistance(double x, double y) {
        double dx = this.getX() - x;
        double dy = this.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void initializeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
