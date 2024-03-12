package AopsTheater;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class SpatialHashMap
{
    private final int xCellSize;
    private final int yCellSize;
    private final int rows;
    private final int cols;
    Map<Cell, List<Actor>> grid;
    /**
     * Constructor for objects of class SpatialHashGrid
     */
    public SpatialHashMap(Stage stage, int xCellSize, int yCellSize) {
        this.xCellSize = xCellSize;
        this.yCellSize = yCellSize;
        this.rows = stage.getHeight() / this.xCellSize;
        this.cols = stage.getWidth() / this.yCellSize;
        grid = new HashMap<>();
    }
    public Cell getCell(Coordinate coordinate) {
        int r = (int)(coordinate.getY() / xCellSize);
        int c = (int)(coordinate.getX() / yCellSize);
        return new Cell(r,c, rows, cols);
    }
    public Cell getCell(Actor actor) {
        return getCell(Coordinate.getCoordinate(actor));
    }
    public void insertNew(Actor actor) {
        Cell key = getCell(actor);
        grid.computeIfAbsent(key, k -> new ArrayList<>()).add(actor);
    }
    public void insertNew(List<Actor> actors) {
        for (Actor actor : actors) {
            insertNew(actor);
        }
    }

    public void insertNew(Actor actor, double x, double y) {
        Cell key = getCell(new Coordinate(x, y));
        grid.computeIfAbsent(key, k -> new ArrayList<>()).add(actor);
    }

    public void remove(Actor actor) {
        grid.get(getCell(actor)).remove(actor);
    }
    private void remove(Actor actor, Coordinate previousCoordinate) {
        grid.get(getCell(previousCoordinate)).remove(actor);
    }
    public void update(Actor actor, Coordinate previousCoordinate) {
        if (getCell(actor) == getCell(previousCoordinate)) return;
        remove(actor, previousCoordinate);
        insertNew(actor);
    }
    public<A extends Actor> List<A> getAllWithinRadius(Class<A> cls, Actor source, double radius) {
        return getAllWithinRadius(cls, Coordinate.getCoordinate(source), radius);
    }
    public<A extends Actor> List<A> getAllWithinRadius(Class<A> cls, Coordinate origin, double radius){
        Cell source = getCell(origin);
        double minX = origin.x - radius;
        double maxX = origin.x + radius;
        int maxCol = (int) (maxX / xCellSize);
        int minCol = (int) (minX / xCellSize);

        double minY = origin.y - radius;
        double maxY = origin.y + radius;
        int maxRow = (int) (maxY / yCellSize);
        int minRow = (int) (minY / yCellSize);

        List<A> allInRadius = new ArrayList<>();
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                Cell current = new Cell(row, col, rows, cols);
                if (!current.isValid()) continue;
                List<Actor> actorsInCell = grid.get(current);
                for (Actor actor : actorsInCell) {
                    if (!cls.isInstance(actor)) continue;
                    if (calculateQuadrature(actor, origin) < radius * radius){
                        allInRadius.add(cls.cast(actor));
                    }
                }
            }
        }
        return allInRadius;
    }
    public<A extends Actor> List<A> getKNearestInRadius(Class<A> cls, Actor source, double radius, int k) {
        List<A> neighbors = getKNearestWithinRadius(cls, Coordinate.getCoordinate(source), radius, k + 1); // k + 1 os k-nearest + self
        neighbors.removeIf(actor -> actor.equals(source));
        return neighbors;
    }
    public <A extends Actor> List<A> getKNearestWithinRadius(Class<A> cls, Coordinate origin, double radius, int k) {
        List<A> actorsInRadius = getAllWithinRadius(cls, origin, radius);

        actorsInRadius.sort((a1, a2) -> Double.compare(
                calculateQuadrature(a1, origin),
                calculateQuadrature(a2, origin)
        ));

        return actorsInRadius.stream().limit(k).collect(Collectors.toList());
    }
    public double calculateQuadrature(Actor actor, Coordinate coord) {
        double dx = actor.getX() - coord.getX();
        double dy = actor.getY() - coord.getY();
        return dx*dx + dy*dy;
    }

    public static class Cell
    {
        private final int row, col, rows, cols;
        public Cell(int row, int col, int rows, int cols)
        {
            this.row = row;
            this.col = col;
            this.cols = cols;
            this.rows = rows;
        }
        @Override
        public int hashCode() {
            return cols * row + col;
        }
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (!(obj instanceof Cell)) return false;
            Cell cell = (Cell) obj;
            return this.row == cell.row && this.col == cell.col && this.rows == cell.rows && this.cols == cell.cols;
        }
        public int getRow() {
            return row;
        }
        public int getCol() {
            return col;
        }
        @Override
        public String toString() {
            return "Row: " + row + " Col: " + col;
        }
        public boolean isValid() {
            return 0 <= row && row < rows && 0 <= col && col < cols;
        }
    }
}
