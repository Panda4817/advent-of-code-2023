package dev.kmunton.utils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Point {

    private int row;
    private int col;

    public Point(int row, int col) {
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
        Point point = (Point) o;
        return row == point.row && col == point.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "[" + row + ", " + col + "]";
    }

    public List<Point> getAdjacentPoints() {
        return List.of(
            new Point(row - 1, col),
            new Point(row + 1, col),
            new Point(row, col - 1),
            new Point(row, col + 1),
            new Point(row - 1, col - 1),
            new Point(row - 1, col + 1),
            new Point(row + 1, col - 1),
            new Point(row + 1, col + 1)
        );
    }

    public Point getAdjacentPoint(String direction) {
        return switch (direction) {
            case "UP" -> new Point(row - 1, col);
            case "DOWN" -> new Point(row + 1, col);
            case "LEFT" -> new Point(row, col - 1);
            case "RIGHT" -> new Point(row, col + 1);
            case "UP_LEFT" -> new Point(row - 1, col - 1);
            case "UP_RIGHT" -> new Point(row - 1, col + 1);
            case "DOWN_LEFT" -> new Point(row + 1, col - 1);
            case "DOWN_RIGHT" -> new Point(row + 1, col + 1);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    public long calculateManhattanDistance(Point other) {
        return (long) Math.abs(this.row - other.getRow()) + Math.abs(this.col - other.getCol());
    }

    public void move(String direction) {
        switch (direction) {
            case "UP" -> this.row--;
            case "DOWN" -> this.row++;
            case "LEFT" -> this.col--;
            case "RIGHT" -> this.col++;
            case "UP_LEFT" -> {
                this.row--;
                this.col--;
            }
            case "UP_RIGHT" -> {
                this.row--;
                this.col++;
            }
            case "DOWN_LEFT" -> {
                this.row++;
                this.col--;
            }
            case "DOWN_RIGHT" -> {
                this.row++;
                this.col++;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    @SafeVarargs
    public final boolean canMoveUp(Set<Point>... blockers) {
        if (this.getRow() == 0) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point(this.getRow() - 1, this.getCol()))) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final boolean canMoveDown(int maxRows, Set<Point>... blockers) {
        if (this.getRow() == maxRows - 1) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point(this.getRow() + 1, this.getCol()))) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final boolean canMoveLeft(Set<Point>... blockers) {
        if (this.getCol() == 0) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point(this.getRow(), this.getCol() - 1))) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final boolean canMoveRight(int maxCols, Set<Point>... blockers) {
        if (this.getCol() == maxCols - 1) {
            return false;
        }
        for (var blocker : blockers) {
            if (blocker.contains(new Point(this.getRow(), this.getCol() + 1))) {
                return false;
            }
        }
        return true;
    }
}
