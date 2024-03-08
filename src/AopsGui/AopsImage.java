package AopsGui;

public class AopsImage {
    private int height, width;
    public AopsImage(String filename) {
        //TODO: Do something with this image
    }

    void setDimension(int height, int width) { //to be called when PIXI sends back picture information
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void scale(int height, int width) {
        setDimension(height, width);
        //TODO: add something to JSON to let pixi know to scale
    }
}
