package dev.kmunton.days.day18;

import static dev.kmunton.utils.CustomMaths.polygonArea;
import static dev.kmunton.utils.Direction.DOWN;
import static dev.kmunton.utils.Direction.LEFT;
import static dev.kmunton.utils.Direction.RIGHT;
import static dev.kmunton.utils.Direction.UP;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Direction;
import dev.kmunton.utils.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 implements Day {

    private final List<DigStep> instructions = new ArrayList<>();

    public Day18(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        for (var line : input) {
            var parts = line.split(" ");
            Direction direction = switch (parts[0]) {
                case "U" -> UP;
                case "D" -> DOWN;
                case "R" -> RIGHT;
                case "L" -> LEFT;
                default -> throw new RuntimeException("Unknown direction: " + parts[0]);
            };
            var steps = Integer.parseInt(parts[1]);
            var hexColour = parts[2].substring(1, parts[2].length() - 1);
            instructions.add(new DigStep(direction, steps, hexColour));
        }
    }

    public long part1() {
        var start = new Point2D(0, 0);
        Set<Point2D> edge = new HashSet<>();
        for (var step : instructions) {
            for (var i = 0; i < step.steps(); i++) {
                start.move(step.direction());
                edge.add(new Point2D(start.getRow(), start.getCol()));

            }
        }
        var minRow = edge.stream().mapToInt(Point2D::getRow).min().getAsInt();
        var maxRow = edge.stream().mapToInt(Point2D::getRow).max().getAsInt();
        var minCol = edge.stream().mapToInt(Point2D::getCol).min().getAsInt();
        var maxCol = edge.stream().mapToInt(Point2D::getCol).max().getAsInt();
        var result = 0L;
        for (var r = minRow; r < maxRow + 1; r++) {
            var crossed = 0;
            for (var c = minCol ; c < maxCol + 1; c++) {
                if (edge.contains(new Point2D(r, c))) {
                    result++;
                    if (!edge.contains(new Point2D(r, c-1)) && !edge.contains(new Point2D(r, c+1))
                        || (!edge.contains(new Point2D(r, c-1)) && edge.contains(new Point2D(r+1, c)) && edge.contains(new Point2D(r, c+1)))
                        || (!edge.contains(new Point2D(r, c+1)) && edge.contains(new Point2D(r+1, c)) && edge.contains(new Point2D(r, c-1)))) {
                        crossed++;
                    }
                    continue;
                }
                if (crossed % 2 != 0) {
                    result++;
                }

            }
        }
        return result;

    }

    public long part2() {
        var newDigSteps = new ArrayList<DigStep>();
        for (var step : instructions) {
            var hex = step.hexColour();
            var number = Integer.parseInt(hex.substring(1, hex.length()-1), 16);
            var direction = switch(hex.charAt(hex.length()-1)) {
                case '0' -> RIGHT;
                case '1' -> DOWN;
                case '2' -> LEFT;
                case '3' -> UP;
                default -> throw new RuntimeException("Unknown direction: " + hex.charAt(hex.length()-1));
            };
            newDigSteps.add(new DigStep(direction, number, hex));
        }

        var start = new Point2D(0, 0);
        List<Point2D> corners = new ArrayList<>();
        for (var step : newDigSteps) {
            for (var i = 0; i < step.steps(); i++) {
                start.move(step.direction());
            }
            corners.add(new Point2D(start.getRow(), start.getCol()));
        }
        var maxRight = newDigSteps.stream().filter(d -> d.direction().equals(RIGHT)).mapToLong(DigStep::steps).sum();
        var maxDown = newDigSteps.stream().filter(d -> d.direction().equals(DOWN)).mapToLong(DigStep::steps).sum();
        corners.remove(new Point2D(0, 0));
        corners.add(0, new Point2D(0, 0));
        var rows = corners.stream().map(Point2D::getRow).map(r -> (double) r).toList();
        var cols = corners.stream().map(Point2D::getCol).map(c -> (double) c).toList();
        var n = corners.size();

        return (long) polygonArea(cols, rows, n) + (maxRight + maxDown + 1);
    }
}
