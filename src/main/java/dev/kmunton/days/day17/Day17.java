package dev.kmunton.days.day17;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Day17 implements Day {

    private final List<List<Block>> grid = new ArrayList<>();
    private int maxRow;
    private int maxCol;

    public Day17(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        maxRow = input.size();
        maxCol = input.get(0).length();
        for (int row = 0; row < maxRow; row++) {
            var rowList = new ArrayList<Block>();
            var rowArray = input.get(row).split("");
            for (int col = 0; col < maxCol; col++) {
                long heat = Long.parseLong(rowArray[col]);
                rowList.add(new Block(row, col, heat));
            }
            grid.add(rowList);
        }
    }

    public long part1() {
        return solve(2, 0);
    }

    public long part2() {
        return solve(9, 3);
    }

    private void addUpIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited, Crucible crucible,
                              Point up, int minStraightSteps) {
        if (isValidMove(up) && crucible.getStraightSteps() >= minStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(up);
            var key = getKey(up, "UP", 0);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(up.getRow(), up.getCol(), "UP", heat, 0));
            }
        }
    }

    private void addDownIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited, Crucible crucible,
                                Point down, int minStraightSteps) {
        if (isValidMove(down) && crucible.getStraightSteps() >= minStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(down);
            var key = getKey(down, "DOWN", 0);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(down.getRow(), down.getCol(), "DOWN", heat, 0));
            }
        }
    }

    private void addRightIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited, Crucible crucible,
                                 Point right, int minStraightSteps) {
        if (isValidMove(right) && crucible.getStraightSteps() >= minStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(right);
            var key = getKey(right, "RIGHT", 0);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(right.getRow(), right.getCol(), "RIGHT", heat, 0));
            }
        }
    }

    private void addLeftIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited, Crucible crucible,
                                Point left, int minStraightSteps) {
        if (isValidMove(left) && crucible.getStraightSteps() >= minStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(left);
            var key = getKey(left, "LEFT", 0);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(left.getRow(), left.getCol(), "LEFT", heat, 0));
            }
        }
    }

    private long solve(int maxStraightSteps, int minStraightSteps) {
        var queue = new PriorityQueue<>(Comparator.comparing(Crucible::getTotalHeatLoss));
        var start1 = new Crucible(1, 0, "DOWN", getHeatLoss(new Point(1, 0)), 0);
        var start2 = new Crucible(0, 1, "RIGHT", getHeatLoss(new Point(0, 1)), 0);
        queue.add(start1);
        queue.add(start2);
        var visited = new HashMap<String, Long>();
        visited.put(start1.getKey(), 0L);
        visited.put(start2.getKey(), 0L);
        var minHeatLoss = Long.MAX_VALUE;
        while(!queue.isEmpty()) {
            var crucible = queue.remove();
            if (isAtEnd(crucible) && crucible.getStraightSteps() >= minStraightSteps) {
                minHeatLoss = Math.min(minHeatLoss, crucible.getTotalHeatLoss());
                continue;
            }
            switch(crucible.getDirection()) {
                case "RIGHT":
                    var up = crucible.getAdjacentPoint("UP");
                    addUpIfValid(queue, visited, crucible, up, minStraightSteps);
                    var down = crucible.getAdjacentPoint("DOWN");
                    addDownIfValid(queue, visited, crucible, down, minStraightSteps);
                    var right = crucible.getAdjacentPoint("RIGHT");
                    addSameDirectionRightIfValid(queue, visited, maxStraightSteps, crucible, right);
                    break;
                case "DOWN":
                    var left = crucible.getAdjacentPoint("LEFT");
                    addLeftIfValid(queue, visited, crucible, left, minStraightSteps);
                    var right2 = crucible.getAdjacentPoint("RIGHT");
                    addRightIfValid(queue, visited, crucible, right2, minStraightSteps);
                    var down2 = crucible.getAdjacentPoint("DOWN");
                    addSameDirectionDownIfValid(queue, visited, maxStraightSteps, crucible, down2);
                    break;
                case "LEFT":
                    var up2 = crucible.getAdjacentPoint("UP");
                    addUpIfValid(queue, visited, crucible, up2, minStraightSteps);
                    var down3 = crucible.getAdjacentPoint("DOWN");
                    addDownIfValid(queue, visited, crucible, down3, minStraightSteps);
                    var left2 = crucible.getAdjacentPoint("LEFT");
                    addSameDirectionLeftIfValid(queue, visited, maxStraightSteps, crucible, left2);
                    break;
                case "UP":
                    var left3 = crucible.getAdjacentPoint("LEFT");
                    addLeftIfValid(queue, visited, crucible, left3, minStraightSteps);
                    var right3 = crucible.getAdjacentPoint("RIGHT");
                    addRightIfValid(queue, visited, crucible, right3, minStraightSteps);
                    var up3 = crucible.getAdjacentPoint("UP");
                    addSameDirectionUpIfValid(queue, visited, maxStraightSteps, crucible, up3);
                    break;
            }

        }
        return minHeatLoss;
    }

    private void addSameDirectionRightIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited,
                                              int maxStraightSteps, Crucible crucible, Point right) {
        if (isValidMove(right) && crucible.getStraightSteps() < maxStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(right);
            var newSteps = crucible.getStraightSteps() + 1;
            var key = getKey(right, "RIGHT", newSteps);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(right.getRow(), right.getCol(), "RIGHT", heat, newSteps));
            }
        }
    }

    private void addSameDirectionDownIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited,
                                             int maxStraightSteps, Crucible crucible, Point down) {
        if (isValidMove(down) && crucible.getStraightSteps() < maxStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(down);
            var newSteps = crucible.getStraightSteps() + 1;
            var key = getKey(down, "DOWN", newSteps);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(down.getRow(), down.getCol(), "DOWN", heat, newSteps));
            }
        }
    }

    private void addSameDirectionLeftIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited,
                                             int maxStraightSteps, Crucible crucible, Point left) {
        if (isValidMove(left) && crucible.getStraightSteps() < maxStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(left);
            var newSteps = crucible.getStraightSteps() + 1;
            var key = getKey(left, "LEFT", newSteps);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(left.getRow(), left.getCol(), "LEFT", heat, newSteps));
            }
        }
    }

    private void addSameDirectionUpIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited,
                                           int maxStraightSteps, Crucible crucible, Point up) {
        if (isValidMove(up) && crucible.getStraightSteps() < maxStraightSteps) {
            var heat = crucible.getTotalHeatLoss() + getHeatLoss(up);
            var newSteps = crucible.getStraightSteps() + 1;
            var key = getKey(up, "UP", newSteps);
            if (notVisited(visited, key, heat)) {
                visited.put(key, heat);
                queue.add(new Crucible(up.getRow(), up.getCol(), "UP", heat, newSteps));
            }
        }
    }

    private boolean isAtEnd(Crucible crucible) {
        return crucible.getRow() == maxRow - 1 && crucible.getCol() == maxCol - 1;
    }

    private boolean isValidMove(Point point) {
        return point.getRow() >= 0 && point.getRow() < maxRow && point.getCol() >= 0 && point.getCol() < maxCol;
    }

    private long getHeatLoss(Point point) {
        return grid.get(point.getRow()).get(point.getCol()).getHeatLoss();
    }

    private boolean notVisited(Map<String, Long> visited, String key, long heatLoss) {
        if (!visited.containsKey(key)) {
            return true;
        }
        return visited.get(key) > heatLoss;
    }

    private String getKey(Point point, String direction, int straightSteps) {
        return point.getRow() + "_" + point.getCol() + "_" + direction + "_" + straightSteps;
    }
}
