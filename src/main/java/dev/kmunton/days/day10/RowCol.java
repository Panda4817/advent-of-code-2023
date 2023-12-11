package dev.kmunton.days.day10;

import java.util.List;
import java.util.Objects;

public class RowCol {

    private int row;
    private int col;

    public RowCol(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RowCol rowCol = (RowCol) o;
        return row == rowCol.row && col == rowCol.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "[" + row + ", " + col + "]";
    }

    public List<RowCol> getAdjacentRowCols() {
        return List.of(
            new RowCol(row - 1, col),
            new RowCol(row + 1, col),
            new RowCol(row, col - 1),
            new RowCol(row, col + 1),
            new RowCol(row - 1, col - 1),
            new RowCol(row - 1, col + 1),
            new RowCol(row + 1, col - 1),
            new RowCol(row + 1, col + 1)
        );
    }

    public RowCol getAdjacentRowCol(String direction) {
        return switch (direction) {
            case "UP" -> new RowCol(row - 1, col);
            case "DOWN" -> new RowCol(row + 1, col);
            case "LEFT" -> new RowCol(row, col - 1);
            case "RIGHT" -> new RowCol(row, col + 1);
            case "UP_LEFT" -> new RowCol(row - 1, col - 1);
            case "UP_RIGHT" -> new RowCol(row - 1, col + 1);
            case "DOWN_LEFT" -> new RowCol(row + 1, col - 1);
            case "DOWN_RIGHT" -> new RowCol(row + 1, col + 1);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    public long calculateManhattanDistance(RowCol other) {
        return (long) Math.abs(this.row - other.getRow()) + Math.abs(this.col - other.getCol());
    }
}
