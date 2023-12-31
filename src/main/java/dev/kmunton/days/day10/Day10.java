package dev.kmunton.days.day10;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day10 implements Day {

    private final List<List<Tile>> grid = new ArrayList<>();
    private Point2D start;

    public Day10(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        for (var r = 0; r < input.size(); r++) {
            var row = input.get(r).split("");
            var rowList = new ArrayList<Tile>();
            for (var c = 0; c < row.length; c++) {
                var tile = new Tile(row[c], new Point2D(r, c));
                if (Objects.equals(tile.getType(), "S")) {
                    start = new Point2D(r, c);
                }
                rowList.add(tile);
            }
            grid.add(rowList);
        }

    }

    public long part1() {
        var mainLoopRowCols = getMainLoopRowCols();
        return mainLoopRowCols.size() / 2;

    }

    public long part2() {
        var mainLoopRowCols = getMainLoopRowCols();
        var enclosedTotal = 0;
        for (var row = 0; row < grid.size(); row++) {
            if (row == 0 || row == grid.size() - 1) {
                continue;
            }
            var mainLoopPipeCount = 0;
            for (var col = 0; col < grid.get(row).size(); col++) {
                if (col == grid.get(row).size() - 1) {
                    continue;
                }
                var tile = grid.get(row).get(col);
                if (tile.isMainLoop(mainLoopRowCols)) {
                    if (tile.getType().equals("|")) {
                        mainLoopPipeCount++;
                    }
                    if (tile.getType().equals("7") || tile.getType().equals("F")) {
                        mainLoopPipeCount += 1;
                    }
                } else {
                    var countMainPipesAfter = mainLoopRowCols.stream()
                        .filter(mainLoopRowCol -> mainLoopRowCol.getRow() == tile.getRowCol().getRow() && mainLoopRowCol.getCol() > tile.getRowCol().getCol())
                        .count();
                    if (countMainPipesAfter == 0) {
                        continue;
                    }
                    if (mainLoopPipeCount % 2 != 0) {
                        enclosedTotal++;
                    }
                }

            }
        }
        return enclosedTotal;
    }

    private List<Point2D> getMainLoopRowCols() {
        List<Point2D> queue = new ArrayList<>();
        queue.add(start);
        var visited = new ArrayList<Point2D>();
        var mainLoopRowCols = new ArrayList<Point2D>();
        while (!queue.isEmpty()) {
            var current = queue.remove(0);
            visited.add(current);
            var tile = grid.get(current.getRow()).get(current.getCol());

            var adjacentRowCols = tile.getPossibleNeighbours();
            var count = 0;
            for (var adjacentRowCol : adjacentRowCols) {
                if (adjacentRowCol.getRow() < 0 || adjacentRowCol.getRow() >= grid.size()) {
                    continue;
                }
                if (adjacentRowCol.getCol() < 0 || adjacentRowCol.getCol() >= grid.get(adjacentRowCol.getRow()).size()) {
                    continue;
                }
                if (queue.contains(adjacentRowCol) || visited.contains(adjacentRowCol)) {
                    count++;
                    continue;
                }
                var adjacentTile = grid.get(adjacentRowCol.getRow()).get(adjacentRowCol.getCol());
                if (adjacentTile.getPossibleNeighbours().contains(current)) {
                    queue.add(adjacentRowCol);
                    count++;
                }
            }

            if (count >= 2) {
                mainLoopRowCols.add(current);
            }

        }
        return mainLoopRowCols;
    }

    private void printGrid(List<Point2D> mainLoopPoints, boolean onlyMainLoop, List<Point2D> enclosedPoints) {
        for (var row : grid) {
            for (var tile : row) {
                if (onlyMainLoop) {
                    System.out.print(tile.isMainLoop(mainLoopPoints) ? tile.getType() : ".");
                    continue;
                } else if (enclosedPoints != null) {
                    System.out.print(enclosedPoints.contains(tile.getRowCol())
                        ? "X"
                        : tile.isMainLoop(mainLoopPoints) ? tile.getType() : ".");
                    continue;
                }
                System.out.print(tile.getType());
            }
            System.out.println();
        }
    }


}
