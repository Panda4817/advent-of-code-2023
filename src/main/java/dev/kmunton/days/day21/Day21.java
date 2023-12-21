package dev.kmunton.days.day21;

import static dev.kmunton.utils.Direction.DOWN;
import static dev.kmunton.utils.Direction.LEFT;
import static dev.kmunton.utils.Direction.RIGHT;
import static dev.kmunton.utils.Direction.UP;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day21 implements Day {

    private int[][] gardenMap;
    private Point start;
    private final Set<Point> rocks = new HashSet<>();

    private record State(Point position, int steps) {}
    private record StateInfiniteGarden(Point positionOnGrid, Point positionOffMap, int steps) {}

    public Day21(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        gardenMap = new int[input.size()][input.get(0).length()];
        for (var r = 0; r < input.size(); r++) {
            for (var c = 0; c < input.get(r).length(); c++) {
                gardenMap[r][c] = input.get(r).charAt(c) == '#' ? -1 : 0;
                if (input.get(r).charAt(c) == 'S') {
                    start = new Point(r, c);
                }
                if (input.get(r).charAt(c) == '#') {
                    rocks.add(new Point(r, c));
                }

            }
        }
    }

    public long part1() {
        return calculateGardenPlotsBySteps(64);

    }

    // Thanks to Reddit AOC thread, it turned out to be a quadratic equation
    public long part2() {
        long stepsToTake = 26501365;
        long width = 131;
        long x = stepsToTake % width;
        long d = stepsToTake / width;
        var y = calculateGardenPlotsByStepsForInfiniteGarden(x);
        var y1 = calculateGardenPlotsByStepsForInfiniteGarden(x + width);
        var y2 = calculateGardenPlotsByStepsForInfiniteGarden(x + (width * 2L));
        long c = y;
        long a = (y2 + c - (2 * y1)) / 2;
        long b = y1 - y - a;
        return (a * d * d) + (b * d) + c;
    }

    public long calculateGardenPlotsBySteps(int steps) {
        var visited = new HashSet<String>();
        var queue = new LinkedList<State>();
        var plots = 0;
        queue.add(new State(start, 0));
        visited.add(start.toString() + 0);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.steps() == steps) {
                plots++;
                continue;
            }
            var currentPoint = current.position();
            var nextPoints = new ArrayList<Point>();
            if (currentPoint.canMoveUp(rocks)) {
                nextPoints.add(currentPoint.getAdjacentPoint(UP));
            }
            if (currentPoint.canMoveDown(gardenMap.length, rocks)) {
                nextPoints.add(currentPoint.getAdjacentPoint(DOWN));
            }
            if (currentPoint.canMoveLeft(rocks)) {
                nextPoints.add(currentPoint.getAdjacentPoint(LEFT));
            }
            if (currentPoint.canMoveRight(gardenMap[0].length, rocks)) {
                nextPoints.add(currentPoint.getAdjacentPoint(RIGHT));
            }
            for (var next : nextPoints) {
                if (!visited.contains(next.toString() + current.steps() + 1 )) {
                    visited.add(next.toString() + current.steps() + 1);
                    queue.add(new State(next, current.steps() + 1));
                }
            }
        }
        return plots;
    }


    public long calculateGardenPlotsByStepsForInfiniteGarden(long steps) {
        var visited = new HashSet<String>();
        var queue = new LinkedList<StateInfiniteGarden>();
        var plots = 0;
        var first = new StateInfiniteGarden(start, start, 0);
        queue.add(first);
        visited.add(first.toString());
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.steps() == steps) {
                plots++;
                continue;
            }
            var currentPoint = current.positionOnGrid();
            var pointOffGrid = current.positionOffMap();
            var nextPoints = new ArrayList<StateInfiniteGarden>();
            nextPoints.add(new StateInfiniteGarden(currentPoint.getAdjacentPoint(UP),
                pointOffGrid.getAdjacentPoint(UP), current.steps() + 1));
            nextPoints.add(new StateInfiniteGarden(currentPoint.getAdjacentPoint(DOWN),
                pointOffGrid.getAdjacentPoint(DOWN), current.steps() + 1));
            nextPoints.add(new StateInfiniteGarden(currentPoint.getAdjacentPoint(LEFT),
                pointOffGrid.getAdjacentPoint(LEFT), current.steps() + 1));
            nextPoints.add(new StateInfiniteGarden(currentPoint.getAdjacentPoint(RIGHT),
                pointOffGrid.getAdjacentPoint(RIGHT), current.steps() + 1));
            for (var next : nextPoints) {
                int adjustedRow;
                int adjustedCol;
                if (next.positionOnGrid.getRow() == gardenMap.length) {
                    adjustedRow = 0;
                }
                else if (next.positionOnGrid.getRow() == -1) {
                    adjustedRow = gardenMap.length - 1;
                }
                else {
                    adjustedRow = next.positionOnGrid.getRow();
                }
                if (next.positionOnGrid.getCol() == gardenMap[0].length) {
                    adjustedCol = 0;
                }
                else if (next.positionOnGrid.getCol() == -1) {
                    adjustedCol = gardenMap[0].length - 1;
                }
                else {
                    adjustedCol = next.positionOnGrid.getCol();
                }
                var nextOnGrid = new Point(adjustedRow, adjustedCol);
                if (rocks.contains(nextOnGrid)) {
                    continue;
                }
                next = new StateInfiniteGarden(nextOnGrid, next.positionOffMap(), next.steps());
                if (!visited.contains(next.toString())) {
                    visited.add(next.toString());
                    queue.add(next);
                }
            }
        }
        return plots;
    }
}
