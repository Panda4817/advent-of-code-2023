package dev.kmunton.days.day23;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Direction;
import dev.kmunton.utils.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

public class Day23 implements Day {

    private List<List<Long>> map = new ArrayList<>();
    private int maxRow;
    private int maxCol;
    private record State(Point2D position, long steps, Set<String> visited) {
    }

    public Day23(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        maxRow = input.size();
        maxCol = input.get(0).length();
        for (var line : input) {
            var row = new ArrayList<Long>();
            for (var c : line.split("")) {
                switch (c) {
                    case "#":
                        row.add(-1L);
                        break;
                    case ".":
                        row.add(0L);
                        break;
                    case ">":
                        row.add(1L);
                        break;
                    case "<":
                        row.add(2L);
                        break;
                    case "^":
                        row.add(3L);
                        break;
                    case "v":
                        row.add(4L);
                        break;
                    default:
                        throw new RuntimeException("Unknown character: " + c);
                }
            }
            map.add(row);
        }

    }

    public long part1() {
        var start = new Point2D(0, 1);
        var longestPath = 0L;
        var queue = new PriorityQueue<State>(Comparator.comparing(State::steps).reversed());
        queue.add(new State(start, 0L, Set.of(start.toString())));
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (isAtEnd(current.position())) {
                longestPath = Math.max(longestPath, current.steps());
                continue;
            }

            var point = current.position();
            var tileType = map.get(point.getRow()).get(point.getCol());
            var newSteps = current.steps() + 1;
            var visited = current.visited();
            var nextTiles = new ArrayList<Point2D>();
            if (tileType == 1L) {
                nextTiles.add(point.getAdjacentPoint(Direction.RIGHT));
            } else if (tileType == 2L) {
                nextTiles.add(point.getAdjacentPoint(Direction.LEFT));
            } else if (tileType == 3L) {
                nextTiles.add(point.getAdjacentPoint(Direction.UP));
            } else if (tileType == 4L) {
                nextTiles.add(point.getAdjacentPoint(Direction.DOWN));
            } else {
                nextTiles.add(point.getAdjacentPoint(Direction.UP));
                nextTiles.add(point.getAdjacentPoint(Direction.DOWN));
                nextTiles.add(point.getAdjacentPoint(Direction.LEFT));
                nextTiles.add(point.getAdjacentPoint(Direction.RIGHT));
            }

            for (var nextTile : nextTiles) {
                if (nextTile.isOnGrid(maxRow, maxCol)
                    && isNotForest(nextTile)
                    && isNotVisited(nextTile, visited)) {
                    queue.add(new State(nextTile, newSteps, getDeepCopyOfVisited(visited, nextTile)));
                }
            }
        }

        return longestPath;

    }


    public long part2() {
        var start = new Point2D(0, 1);
        var end = new Point2D(maxRow - 1, maxCol - 2);
        var longestPath = 0L;

        var junctions = new HashMap<String, Point2D>();
        junctions.put(start.toString(), start);
        junctions.put(end.toString(), end);
        for (var r = 0; r < maxRow; r++) {
            for (var c = 0; c < maxCol; c++) {
                var point = new Point2D(r, c);
                var nextTiles = Stream.of(
                    point.getAdjacentPoint(Direction.UP),
                    point.getAdjacentPoint(Direction.LEFT),
                    point.getAdjacentPoint(Direction.DOWN),
                    point.getAdjacentPoint(Direction.RIGHT)
                ).filter(p -> p.isOnGrid(maxRow, maxCol) && isNotForest(p)).count();
                if (nextTiles > 2) {
                    junctions.put(point.toString(), point);
                }
            }
        }
        record NodeState(Point2D position, long steps) {
        }
        var nodeMap = new HashMap<String, Map<String, NodeState>>();
        for (var entry : junctions.entrySet()) {
            var queue = new ArrayList<State>();
            queue.add(new State(entry.getValue(), 0L, Set.of(entry.getKey())));
            nodeMap.put(entry.getKey(), new HashMap<>());
            while(!queue.isEmpty()) {
                var current = queue.remove(0);
                if (junctions.containsKey(current.position().toString())
                    && !current.position().equals(entry.getValue())) {
                    nodeMap
                        .get(entry.getKey())
                        .put(current.position().toString(), new NodeState(current.position(), current.steps()));
                    continue;
                }

                var point = current.position();
                var nextTiles = Stream.of(
                    point.getAdjacentPoint(Direction.UP),
                    point.getAdjacentPoint(Direction.LEFT),
                    point.getAdjacentPoint(Direction.DOWN),
                    point.getAdjacentPoint(Direction.RIGHT)
                ).filter(p -> p.isOnGrid(maxRow, maxCol) && isNotForest(p) && isNotVisited(p, current.visited())).toList();
                for (var nextTile : nextTiles) {
                    queue.add(new State(nextTile, current.steps() + 1, getDeepCopyOfVisited(current.visited(), nextTile)));
                }
            }
        }

        var queue = new PriorityQueue<>(Comparator.comparing(State::steps).reversed());
        queue.add(new State(start, 0L, Set.of(start.toString())));
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (isAtEnd(current.position())) {
                if (current.steps() > longestPath) {
                    longestPath = current.steps();
                }
                continue;
            }

            var point = current.position();
            var visited = current.visited();
            nodeMap.get(point.toString()).entrySet().stream()
                .filter(e -> !visited.contains(e.getKey()))
                .map(e -> new State(e.getValue().position(), current.steps() + e.getValue().steps(), getDeepCopyOfVisited(visited, e.getValue().position())))
                .forEach(queue::add);
        }

        return longestPath;
    }



    private boolean isNotForest(Point2D point) {
        return map.get(point.getRow()).get(point.getCol()) != -1L;
    }

    private boolean isNotVisited(Point2D point, Set<String> visited) {
        return !visited.contains(point.toString());
    }

    private boolean isAtEnd(Point2D point2D) {
        return point2D.getRow() == maxRow - 1 && point2D.getCol() == maxCol - 2;
    }

    private Set<String> getDeepCopyOfVisited(Set<String> visited, Point2D point) {
        var copy = new HashSet<>(visited);
        copy.add(point.toString());
        return copy;
    }
}
