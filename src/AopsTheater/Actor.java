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
    private double degrees;
    private double alpha = 1;
    private boolean isVisible = true;
    private String image;
    private double width = 60;
    private double height = 30;

    private Collider collider;

    // TODO Should color be kept as an int, it'll lower the work needed for Json conversions
    private Color tint = Color.WHITE;

    public Actor() {
        this(DEFAULT_IMAGE);
    }

    public Actor(String imageFilename) {
        //this.image = new AopsImage(imageFilename);
        this.image = imageFilename;
        z = AopsTheaterHandler.nextZ();
        collider = new RectangularCollider(this, height, width);
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public void act() {
    }



//    public AopsImage getImage() {
//        return image;
//    }
    public void setImage(String imageFilename) {
        this.image = imageFilename;
    }

//    public void setImage(AopsImage image) {
//        this.image = image;
//    }

    public double getRotation() {
        return this.degrees;
    }

    public void setRotation(double degrees) {
        this.degrees = (degrees % 360 + 360) % 360;
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
        Coordinate previousCoordinate = new Coordinate(x, y);
        x += Math.cos(Math.toRadians(degrees)) * distance;
        y += Math.sin(Math.toRadians(degrees)) * distance;
        getStage().getSpatialHashMap().update(this, previousCoordinate);
    }


    public void setLocation(double x, double y) {
        Coordinate previousCoordinate = new Coordinate(this.x, this.y);
        this.x = x;
        this.y = y;
        getStage().getSpatialHashMap().update(this, previousCoordinate);
    }

    void initializeLocation(double x, double y) {
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
        setRotation(this.degrees + degrees);
    }

    public void turnTowards(double x, double y) {
        setRotation(Math.toDegrees(Math.atan2(y - this.y, x - this.x)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actor)) {
            return false;
        }
        Actor actor = (Actor) o;
        return getUUID().equals(actor.getUUID());

    }
    @Override
    public int hashCode() {
        return getUUID().hashCode();
    }

    public<A extends Actor> List<A> getIntersectingObjects(Class<A> cls) {
        List<A> actors = stage.getObjectsInRange(cls, getX(), getY(), collider.getMaxDimension());
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
        return stage.getSpatialHashMap().getAllWithinRadius(cls, this, radius);
    }

    public double getDistance(double x, double y) {
        return Math.hypot(this.x - x, this.y  - y);
    }

    public void addedToStage(Stage stage) {
    }

    //temporarily disabling AopsImage dependency for testing
    public String getImage() {
        return image;
    }
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
