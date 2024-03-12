package AopsTheater;

public class AopsImage {
    private double height, width, tint, alpha;
    public AopsImage(String filename) {
        //TODO: Do something with this image
    }

    void setDimension(double height, double width) { //to be called when PIXI sends back picture information
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    public void scale(int height, int width) {
        setDimension(height, width);
        //TODO: add something to JSON to let pixi know to scale
    }
    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = Math.max(0.0, Math.min(1.0, alpha));
    }
}
