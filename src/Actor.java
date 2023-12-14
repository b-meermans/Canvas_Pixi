//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Objects;

public abstract class Actor {
    private double x;
    private double y;
    private double rotation;

    public Actor() {
        this(0, 0);
    }

    public Actor(double x, double y) {
        this.x = x;
        this.y = y;
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
        return "Actor{x=" + this.x + ", y=" + this.y + ", rotation=" + this.rotation + "}";
    }
}
