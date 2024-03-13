package AopsTheater;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
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
        return new Cell((int) (x / cellSize), (int) (y / cellSize), rows, columns);
    }

    private Cell getContainingCell(Actor actor) {
        return getContainingCell(actor.getX(), actor.getY());
    }

    public void insertNew(Actor actor) {
        Cell key = getContainingCell(actor);
        System.out.println("Inserting new actor at " + key.toString());
        grid.computeIfAbsent(key, k -> new ArrayList<Actor>()).add(actor);
    }

    public void updateLocation(Actor actor, double newX, double newY) {
        Cell key = getContainingCell(actor);
        grid.get(key).remove(actor);
        Cell newKey = getContainingCell(newX, newY);
        grid.computeIfAbsent(newKey, k -> new ArrayList<Actor>()).add(actor);
    }

    public void removeActor(Actor actor) {
        Cell key = getContainingCell(actor);
        grid.get(key).remove(actor);
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
