package AopsTheater;

import java.util.*;
import java.util.stream.Collectors;

public class SpatialHashGrid {
    private transient final HashMap<Cell, List<Actor>> grid;
    private final int rows, columns, cellSize;
    public SpatialHashGrid(int height, int width, int cellSize) {
        rows = height / cellSize;
        columns = width / cellSize;
        this.cellSize = cellSize;
        grid = new HashMap<>();
    }

    private Cell getContainingCell(double x, double y) {
        return new Cell((int) Math.round(x / cellSize), (int) Math.round(y / cellSize), rows, columns);
    }

    private Cell getContainingCell(Actor actor) {
        return getContainingCell(actor.getX(), actor.getY());
    }

    public void insertNew(Actor actor) {
        Cell key = getContainingCell(actor);
        //System.out.println("Inserting new actor at " + key.toString());
        grid.computeIfAbsent(key, k -> new ArrayList<>()).add(actor);
    }

    public void updateLocation(Actor actor, double newX, double newY) {
        Cell key = getContainingCell(actor);
        Cell newKey = getContainingCell(newX, newY);
        if (key.equals(newKey)) {
            return;
        }
        grid.get(key).remove(actor);
        grid.computeIfAbsent(newKey, k -> new ArrayList<>()).add(actor);
        //System.out.println("Updating actor from " + key + "to " + newKey);
    }

    public void removeActor(Actor actor) {
        Cell key = getContainingCell(actor);
        grid.get(key).remove(actor);
    }

    public<A extends Actor> List<A> getObjectsInRange(Class<A> cls, double x, double y, double radius) {
        List<A> actors = new ArrayList<>();
        Cell minCell = getContainingCell(x - radius, y - radius);
        Cell maxCell = getContainingCell(x + radius, y + radius);
        for (int row = minCell.row; row <= maxCell.row; row++) {
            for (int column = minCell.column; column <= maxCell.column; column++) {
                Cell key = new Cell(row, column, rows, columns);
                grid.getOrDefault(key, Collections.emptyList())
                            .stream()
                            .filter(actor -> cls.isInstance(actor) && (Math.hypot(actor.getX() - x, actor.getY() - y) < radius))
                            .map(cls::cast)
                            .forEach(actors::add);
            }
        }
        return actors;
    }

    public<A extends Actor> List<A> getKNearestObjectsInRange(Class<A> cls, int k, double x, double y, double radius) {
        return getObjectsInRange(cls, x, y, radius).stream()
                .sorted(Comparator.comparingDouble(actor -> Math.hypot(actor.getX() - x, actor.getY() - y)))
                .limit(k)
                .collect(Collectors.toList());
    }

    private static class Cell {
        private final int row, column, rows, columns;

        public Cell(int row, int column, int rows, int columns) {
            this.row = row;
            this.column = column;
            this.rows = rows;
            this.columns = columns;
        }

        public int hashCode() {
            return row * columns + column;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Cell)) {
                return false;
            }
            Cell cell = (Cell) o;
            return cell.row == row && cell.column == column && cell.rows == rows && cell.columns == columns;
        }

        public String toString() {
            return String.format("(%d, %d)", row, column);
        }
    }
}
