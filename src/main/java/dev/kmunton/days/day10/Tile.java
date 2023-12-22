package dev.kmunton.days.day10;

import dev.kmunton.utils.Point2D;
import java.util.List;

public class Tile {

    private final String type;
    private final Point2D point;
    private final List<Point2D> possibleNeighbours;

    public Tile(String type, Point2D point) {
        this.type = type;
        this.point = point;
        this.possibleNeighbours = setPossibleNeighbours(point);
    }

    private List<Point2D> setPossibleNeighbours(Point2D point) {
        return switch (type) {
            case "S" -> List.of(
                new Point2D(point.getRow() - 1, point.getCol()),
                new Point2D(point.getRow() + 1, point.getCol()),
                new Point2D(point.getRow(), point.getCol() - 1),
                new Point2D(point.getRow(), point.getCol() + 1)
            );
            case "|" -> List.of(
                new Point2D(point.getRow() - 1, point.getCol()),
                new Point2D(point.getRow() + 1, point.getCol())
            );
            case "-" -> List.of(
                new Point2D(point.getRow(), point.getCol() - 1),
                new Point2D(point.getRow(), point.getCol() + 1)
            );
            case "F" -> List.of(
                new Point2D(point.getRow() + 1, point.getCol()),
                new Point2D(point.getRow(), point.getCol() + 1)
            );
            case "L" -> List.of(
                new Point2D(point.getRow() - 1, point.getCol()),
                new Point2D(point.getRow(), point.getCol() + 1)
            );
            case "J" -> List.of(
                new Point2D(point.getRow() - 1, point.getCol()),
                new Point2D(point.getRow(), point.getCol() - 1)
            );
            case "7" -> List.of(
                new Point2D(point.getRow() + 1, point.getCol()),
                new Point2D(point.getRow(), point.getCol() - 1)
            );
            default -> List.of();
        };
    }

    public String getType() {
        return type;
    }

    public List<Point2D> getPossibleNeighbours() {
        return possibleNeighbours;
    }

    public boolean isMainLoop(List<Point2D> mainLoopPoints) {
        return mainLoopPoints.contains(point);
    }

    @Override
    public String toString() {
        return "Tile{" +
            "type=" + type +
            ", point=" + point +
            ", possibleNeighbours=" + possibleNeighbours +
            '}';
    }

    public Point2D getRowCol() {
        return point;
    }
}
