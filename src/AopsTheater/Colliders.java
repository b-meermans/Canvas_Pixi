package AopsGui;

public class Colliders {
    public static boolean isIntersecting(Collider colliderA, Collider colliderB) {
        if (colliderA instanceof RectangularCollider rectangularColliderA && colliderB instanceof RectangularCollider rectangularColliderB) {
            return isIntersecting(rectangularColliderA, rectangularColliderB);
        }
        if (colliderA instanceof CircularCollider circularColliderA && colliderB instanceof CircularCollider circularColliderB) {
            return isIntersecting(circularColliderA, circularColliderB);
        }
        if (colliderA instanceof CircularCollider circularCollider && colliderB instanceof RectangularCollider rectangularCollider) {
            return isIntersecting(rectangularCollider, circularCollider);
        }
        if (colliderB instanceof CircularCollider circularCollider && colliderA instanceof RectangularCollider rectangularCollider) {
            return isIntersecting(rectangularCollider, circularCollider);
        }
        return false;
    }
    public static boolean isIntersecting(CircularCollider circularColliderA, CircularCollider circularColliderB) {
        double ax = circularColliderA.getX();
        double ay = circularColliderA.getY();
        double ar = circularColliderA.getRadius();

        double bx = circularColliderB.getX();
        double by = circularColliderB.getY();
        double br = circularColliderB.getRadius();

        return (ax - bx) * (ax - bx) + (ay - by) * (ay - by) <= (ar + br) * (ar + br);
    }

    public static boolean isIntersecting(RectangularCollider rectangularCollider, CircularCollider circularCollider) {
        double circleDistanceX = Math.abs(circularCollider.getX() - rectangularCollider.getX());
        double circleDistanceY = Math.abs(circularCollider.getY() - rectangularCollider.getY());

        if (circleDistanceX > (rectangularCollider.getWidth() / 2 + circularCollider.getRadius())) {
            return false;
        }
        if (circleDistanceY > (rectangularCollider.getHeight() / 2 + circularCollider.getRadius())) {
            return false;
        }

        if (circleDistanceX <= (rectangularCollider.getWidth() / 2)) {
            return true;
        }
        if (circleDistanceY <= (rectangularCollider.getHeight() / 2)) {
            return true;
        }

        double cornerDistanceSq = Math.pow(circleDistanceX - rectangularCollider.getWidth() / 2, 2) +
                Math.pow(circleDistanceY - rectangularCollider.getHeight() / 2, 2);

        return (cornerDistanceSq <= Math.pow(circularCollider.getRadius(), 2));
    }

    public static boolean isIntersecting(RectangularCollider rectangularColliderA, RectangularCollider rectangularColliderB) {
        double rightEdge1 = rectangularColliderA.getX() + rectangularColliderA.getWidth();
        double rightEdge2 = rectangularColliderB.getX() + rectangularColliderB.getWidth();
        double bottomEdge1 = rectangularColliderA.getY() + rectangularColliderA.getHeight();
        double bottomEdge2 = rectangularColliderB.getY() + rectangularColliderB.getHeight();

        boolean horizontallyOverlapping = rectangularColliderA.getX() <= rightEdge2 && rightEdge1 >= rectangularColliderB.getX();
        boolean verticallyOverlapping = rectangularColliderA.getY() <= bottomEdge2 && bottomEdge1 >= rectangularColliderB.getY();

        return horizontallyOverlapping && verticallyOverlapping;
    }
}
