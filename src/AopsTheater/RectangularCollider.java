package AopsTheater;

public class RectangularCollider extends Collider {
    private double height, width;
    private AopsImage image;
    public RectangularCollider(Actor sprite, double height, double width) {
        super(sprite);
        this.height = height;
        this.width = width;
    }
    public RectangularCollider(Actor sprite) {
        super(sprite);
        this.image = null;//sprite.getImage();
    }
    public double getWidth() {
        if (image != null) {
            return image.getWidth();
        }
        return width;
    }

    public double getHeight() {
        if (image != null) {
            return image.getHeight();
        }
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double[] getDimensions() {
        return new double[]{getWidth(), getHeight()};
    }
}