package AopsTheater;//

import java.awt.*;

public abstract class Actor extends AopsTheaterComponent {
    // TODO Adjust tint to use a number instead of a color?

    private static final String DEFAULT_IMAGE = "AoPS.png";

    private Stage stage;
    private double x;
    private double y;
    private int z;  // TODO Think through how Z will work
    private double rotation;
    private double alpha = 1;
    private boolean isVisible = true;
    private String image;
    private double width = 60;
    private double height = 30;
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
        this.rotation = rotation % 360;
        this.rotation += (this.rotation < 0) ? 360 : 0;
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
        x += Math.cos(rotation) * distance;
        y += Math.sin(rotation) * distance;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void turn(double degrees) {
        rotation += degrees;
    }

    public void turnTowards(double x, double y) {
        rotation = Math.atan2(y - this.y, x - this.x);
    }
}
