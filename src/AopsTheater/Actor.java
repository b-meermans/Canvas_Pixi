package AopsTheater;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

public abstract class Actor {
    // TODO AnimatedActor class

    private static final String DEFAULT_IMAGE = "AoPS.png";

    UUID uuid;

    private Stage stage;
    private double x;
    private double y;
    private int z;
    private double rotation;
    private String image;
    private double width = 60;
    private double height = 30;
    private Color tint = Color.WHITE;
    private double alpha = 1;

    public Actor() {
        this(DEFAULT_IMAGE);
    }

    public Actor(String image) {
        this.image = image;
        uuid = UUID.randomUUID();
    }


    public void act() {}

    public void move(double distance) {
        x += Math.cos(rotation) * distance;
        y += Math.sin(rotation) * distance;
    }

    public void turn(double degrees) {
        rotation += degrees;
    }

    public void turnTowards(double x, double y) {
        rotation = Math.atan2(y - this.y, x - this.x);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Color getTint() {
        return tint;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setTint(Color tint) {
        this.tint = tint;
    }

    public void setAlpha(double alpha) {
        this.alpha = Math.max(0.0, Math.min(1.0, alpha));
    }

    String getID() {
        return uuid.toString();
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRotation() {
        return this.rotation % 360;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Actor var2 = (Actor)o;
            return Double.compare(this.x, var2.x) == 0 && Double.compare(this.y, var2.y) == 0 && Double.compare(this.rotation, var2.rotation) == 0;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.x, this.y, this.rotation});
    }

    public String toString() {
        return "AopsGui.Actor{x=" + this.x + ", y=" + this.y + ", rotation=" + this.rotation + "}";
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }


}
