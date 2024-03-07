package AopsGui;

import java.util.*;
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
    public Cell getCell(Coordinate coord) {
        int r = (int)(coord.getY() / xCellSize);
        int c = (int)(coord.getX() / yCellSize);
        return new Cell(r,c);
    }
    public Cell getCell(Actor actor) {
        return getCell(Coordinate.getCoordinate(actor));
    }
    public void insertNew(Actor actor) {
        grid.get(getCell(actor)).add(actor);
    }
    public void insertNew(List<Actor> actors) {
        for (Actor actor : actors) {
            insertNew(actor);
        }
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
    public<A extends Actor> List<A> getAllWithinRadius(Class<A> cls, Actor source, int radius) {
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

        int maxDc = Math.max(maxCol - source.getCol(), source.getCol() - minCol);
        int maxDr = Math.max(maxRow - source.getRow(), source.getRow() - minRow);
        List<A> allInRadius = new ArrayList<>();
        for (int dr = -maxDr; dr <= maxDr; dr++) {
            for (int dc = -maxDc; dc <= maxDc; dc++) {
                Cell current = new Cell(source.row + dr, source.col + dc);
                if (!isValidCell(current)) continue;
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
    public<A extends Actor> List<A> getKNearestInRadius(Class<A> cls, Actor source, int k, double radius) {
        List<A> neighbors = getKNearestWithinRadius(cls, Coordinate.getCoordinate(source), k + 1, radius); // k + 1 os l nearest + self
        neighbors.remove(source); // remove self
        return neighbors;
    }
    public<A extends Actor> List<A> getKNearestWithinRadius(Class<A> cls, Coordinate origin, int k, double radius) {
        PriorityQueue<A> kNearestQueue = new PriorityQueue<>(Comparator.comparingDouble(a -> calculateQuadrature((Actor)a, origin)));
        List<A> actorsInRadius = getAllWithinRadius(cls, origin, radius);
        kNearestQueue.addAll(actorsInRadius);
        List<A> kNearestList = new ArrayList<>();
        for (int i = 0; i < Math.min(k, kNearestQueue.size()); i++) {
            kNearestList.add(kNearestQueue.poll());
        }
        return kNearestList;
    }
    public double calculateQuadrature(Actor actor, Coordinate coord) {
        double dx = actor.getX() - coord.getX();
        double dy = actor.getY() - coord.getY();
        return dx*dx + dy*dy;
    }
    public boolean isValidCell(Cell cell) {
        return isBetween(cell.getRow(), 0, rows)
                && isBetween(cell.getCol(), 0, cols);
    }
    public boolean isBetween(int x, int a, int b) {
        return x >= a && x < b;
    }
    public static class Cell
    {
        private final int row;
        private final int col;
        public Cell(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
        @Override
        public int hashCode() {
            return 31 * row + col;
        }
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (!(obj instanceof Cell)) return false;
            Cell cell = (Cell) obj;
            return this.row == cell.row && this.col == cell.col;
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
    }
}
