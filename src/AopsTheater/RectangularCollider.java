package AopsTheater;

public class RectangularCollider implements Collider {
    private final Actor actor;
    private double height;
    private double width;
    private boolean isImageBased;

    public RectangularCollider(Actor actor, double width, double height) {
        this.actor = actor;
        this.height = height;
        this.width = width;
        isImageBased = false;
    }
    public RectangularCollider(Actor actor) {
        this.actor = actor;
        isImageBased = true;
    }
    public double getX() {
        return actor.getX();
    }
    public double getY() {
        return actor.getY();
    }

    public double getHeight() {
        if (isImageBased) {
            return actor.getHeight();
        }
        return height;
    }

    public double getWidth() {
        if (isImageBased) {
            return actor.getWidth();
        }
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        isImageBased = false;
    }

    public void setHeight(double height) {
        this.height = height;
        isImageBased = false;
    }

    @Override
    public Class<? extends Collider> getColliderClass() {
        return this.getClass();
    }

    @Override
    public double getBoundingRadius() {
        return Math.max(width, height);
    }
}
