package AopsGui;

import java.util.Arrays;

public abstract class Collider {
    protected Actor actor;

    public Collider(Actor actor) {
        this.actor = actor;
    }

    public abstract double[] getDimensions();

    public double getX() {
        return actor.getX();
    }
    public double getY() {
        return actor.getY();
    }

    public double getMaxDimension() {
        return Arrays.stream(getDimensions()).max().orElse(0);
    }
}
