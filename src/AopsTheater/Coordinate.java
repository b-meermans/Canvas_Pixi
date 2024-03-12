package AopsTheater;

public class Coordinate
{
    public final double x;
    public final double y;
    public Coordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    @Override
    public int hashCode() {
        return 31 * (int)x + (int)y;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate coordinate = (Coordinate) obj;
        return Double.compare(coordinate.x, this.x) == 0 && Double.compare(coordinate.y, this.y) == 0;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public static Coordinate getCoordinate(Actor actor) {
        return new Coordinate(actor.getX(), actor.getY());
    }
}
