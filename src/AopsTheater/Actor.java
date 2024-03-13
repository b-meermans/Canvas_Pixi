package AopsTheater;//

import java.awt.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    private transient Collider collider;

    // TODO Should color be kept as an int, it'll lower the work needed for Json conversions
    private Color tint = Color.WHITE;

    public Actor() {
        this(DEFAULT_IMAGE);
    }

    public Actor(String image) {
        this.image = image;
        collider = new RectangularCollider(this);
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
        if (other == this) {
            return false;
        }
        return Colliders.isIntersecting(this.collider, other.collider);
    }

    public List<Actor> getIntersectingActors() {
        return getIntersectingActors(Actor.class);
    }

    public <T extends Actor> List<T> getIntersectingActors(Class<T> cls) {
        if (getStage() == null) {
            return null;
        }
        try {
            double maxDistance = getBoundingRadius() + cls.newInstance().getBoundingRadius();
            return getActorsInRange(cls, maxDistance).stream()
                    .filter(this::isIntersecting)
                    .collect(Collectors.toList());
        } catch (Exception e){
            System.out.println("got an error");
            throw new RuntimeException(e.getMessage() + " so the class you passed wasn't intersect-able");
        }
    }

    public <T extends Actor> T getOneIntersectingActor(Class<T> cls) {
        return getIntersectingActors(cls).stream().findFirst().orElse(null);
    }

    double getBoundingRadius() {
        return collider.getBoundingRadius();
    }

    public List<Actor> getActorsInRange(double radius) {
        return getActorsInRange(Actor.class, radius);
    }

    public <T extends Actor> List<T> getActorsInRange(Class<T> cls, double radius) {
        if (getStage() == null) {
            return null;
        }
        return getStage().getObjectsInRange(cls, getX(), getY(), radius).stream()
                .filter(actor -> actor != this)
                .collect(Collectors.toList());
    }

    public Actor getClosestActorInRange(double radius) {
        return getClosestActorInRange(Actor.class, radius);
    }

    public <T extends Actor> T getClosestActorInRange(Class<T> cls, double radius) {
        if (getStage() == null) {
            return null;
        }
        return getActorsInRange(cls, radius).stream()
                .min(Comparator.comparingDouble(actor -> actor.getDistance(x, y)))
                .orElse(null);
    }

    public double getDistance(double x, double y) {
        double dx = this.getX() - x;
        double dy = this.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void initializeLocation(double x, double y) {
        this.x = x;
        this.y = y;
        getStage().getSpatialHashGrid().insertNew(this);
    }

    Collider getCollider() {
        return collider;
    }

    void setCollider(Collider collider) {
        this.collider = collider;
    }
}
