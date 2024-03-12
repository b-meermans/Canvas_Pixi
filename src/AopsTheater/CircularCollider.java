package AopsTheater;

public class CircularCollider extends Collider {
    private double radius;
    public CircularCollider(Actor sprite, double radius) {
        super(sprite);
        this.radius = radius;
    }

    public double getWidth() {
        return radius;
    }

    public double getHeight() {
        return radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double[] getDimensions() {
        return new double[]{radius};
    }
}
