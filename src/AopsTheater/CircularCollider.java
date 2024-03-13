package AopsTheater;

public class CircularCollider implements Collider{
        private final Actor actor;
        private double radius;

        public CircularCollider(Actor actor, double radius) {
            this.actor = actor;
            this.radius = radius;
        }
        public double getX() {
            return actor.getX();
        }
        public double getY() {
            return actor.getY();
        }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public Class<? extends Collider> getColliderClass() {
        return this.getClass();
    }

    @Override
    public double getBoundingRadius() {
        return radius;
    }
}
