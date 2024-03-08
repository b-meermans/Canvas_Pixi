package AopsGui;

public class RectangularCollider extends Collider {
    private double height, width;
    public RectangularCollider(Actor sprite, double height, double width) {
        super(sprite);
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double[] getDimensions() {
        return new double[]{height, width};
    }
}