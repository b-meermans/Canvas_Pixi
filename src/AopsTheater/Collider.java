package AopsTheater;

public interface Collider {
    Class<? extends Collider> getColliderClass();
    double getBoundingRadius();
}
