package dev.kmunton.days.day11;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Day11 implements Day {

    private final List<Point2D> galaxies = new ArrayList<>();
    private final List<Point2D> space = new ArrayList<>();
    private final List<Integer> emptyRows = new ArrayList<>();
    private final List<Integer> emptyCols = new ArrayList<>();

    public Day11(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        for (var row = 0; row < input.size(); row++) {
            var cols = input.get(row).split("");
            for (var col = 0; col < cols.length; col++) {
                if (cols[col].equals(".")) {
                    space.add(new Point2D(row, col));
                } else {
                    galaxies.add(new Point2D(row, col));
                }
            }
        }

        var initialRows = input.size();
        var initialCols= input.get(0).length();

        for (var row = 0; row < initialRows; row++) {
            var rowFinal = row;
            if (space.stream().filter(s -> s.getRow() == rowFinal).count() == initialCols) {
                emptyRows.add(row);
            }
        }
        for (var col = 0; col < initialCols; col++) {
            var colFinal = col;
            if (space.stream().filter(s -> s.getCol() == colFinal).count() == initialRows) {
                emptyCols.add(col);
            }
        }
    }

    public long part1() {
        return getTotalSteps(1);

    }

    public long part2() {
        return getTotalSteps(999999);
    }

    public long getTotalSteps(int universeRate) {
        var pairs = getPairs();
        var totalOfSteps = 0L;
        for (var pair : pairs) {
            var pair1 = new Point2D(pair.get(0).getRow(), pair.get(0).getCol());
            var pair2 = new Point2D(pair.get(1).getRow(), pair.get(1).getCol());
            adjustRowsByUniversalRate(pair1, pair.get(0), universeRate);
            adjustRowsByUniversalRate(pair2, pair.get(1), universeRate);
            adjustColsByUniversalRate(pair1, pair.get(0), universeRate);
            adjustColsByUniversalRate(pair2, pair.get(1), universeRate);
            totalOfSteps += pair1.calculateManhattanDistance(pair2);
        }
        return totalOfSteps;
    }

    private List<List<Point2D>> getPairs() {
        var pairs = new ArrayList<List<Point2D>>();
        for (var i = 0; i < galaxies.size(); i++) {
            for (var j = i + 1; j < galaxies.size(); j++) {
                pairs.add(List.of(galaxies.get(i), galaxies.get(j)));
            }
        }
        return pairs;
    }

    private void adjustRowsByUniversalRate(Point2D newPoint, Point2D oldPoint, int universeRate) {
        for (var row : emptyRows) {
            if (oldPoint.getRow() >= row) {
                newPoint.setRow(newPoint.getRow() + universeRate);
            }
        }
    }

    private void adjustColsByUniversalRate(Point2D newPoint, Point2D oldPoint, int universeRate) {
        for (var col : emptyCols) {
            if (oldPoint.getCol() >= col) {
                newPoint.setCol(newPoint.getCol() + universeRate);
            }
        }
    }


}
