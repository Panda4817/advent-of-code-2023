package dev.kmunton.days.day16;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 implements Day {

    private final List<Point> vSplitters = new ArrayList<>();
    private final List<Point> hSplitters = new ArrayList<>();
    private final List<Point> bMirrors = new ArrayList<>();
    private final List<Point> fMirrors = new ArrayList<>();
    private int maxRow;
    private int maxCol;

    public Day16(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        maxRow = input.size();
        maxCol = input.get(0).length();
        for (var i = 0; i < input.size(); i++) {
            var row = input.get(i);
            for (var j = 0; j < row.length(); j++) {
                var c = row.charAt(j);
                if (c == '|') {
                    vSplitters.add(new Point(i, j));
                } else if (c == '-') {
                    hSplitters.add(new Point(i, j));
                } else if (c == '\\') {
                    bMirrors.add(new Point(i, j));
                } else if (c == '/') {
                    fMirrors.add(new Point(i, j));
                }
            }
        }
    }

    public long part1() {
        return getTotalEnergisedTiles(new Beam(0, 0, "RIGHT"));

    }

    private boolean isOnGrid(Point next) {
        if (next.getRow() >= 0 && next.getRow() < maxRow && next.getCol() >= 0 && next.getCol() < maxCol) {
            return true;
        }
        return false;
    }

    public long part2() {
        long max = 0;
        for (var i = 0; i < maxRow; i++) {
            for (var j = 0; j < maxCol; j++) {
                if ((j > 0 && j < maxCol - 1) && (i > 0 && i < maxRow - 1)) {
                    continue;
                }

                if (i == 0) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, "DOWN"));
                    if (total > max) {
                        max = total;
                    }
                }
                if (i == maxRow - 1) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, "UP"));
                    if (total > max) {
                        max = total;
                    }
                }

                if (j == 0) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, "RIGHT"));
                    if (total > max) {
                        max = total;
                    }
                }

                if (j == maxCol - 1) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, "LEFT"));
                    if (total > max) {
                        max = total;
                    }
                }
            }
        }
        return max;
    }


    private long getTotalEnergisedTiles(Beam start) {
        List<Beam> beams = new ArrayList<>();
        beams.add(new Beam(start.getRow(), start.getCol(), start.getDirection()));
        Map<String, Boolean> visitedMap = new HashMap<>();

        while(!beams.isEmpty()) {
            var beam = beams.remove(0);
            if (visitedMap.containsKey(beam.getKey())) {
                continue;
            }
            visitedMap.put(beam.getKey(), true);
            var split = false;
            switch (beam.getDirection()) {
                case "RIGHT":
                    if (vSplitters.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        splitBeamsVertical(beams, beam);
                        split = true;
                    } else if (bMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("DOWN");
                    } else if (fMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("UP");
                    }
                    break;
                case "LEFT":
                    if (vSplitters.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        splitBeamsVertical(beams, beam);
                        split = true;
                    } else if (bMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("UP");
                    } else if (fMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("DOWN");
                    }
                    break;
                case "UP":
                    if (hSplitters.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        splitBeamsHorizontal(beams, beam);
                        split = true;
                    } else if (bMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("LEFT");
                    } else if (fMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("RIGHT");
                    }
                    break;
                case "DOWN":
                    if (hSplitters.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        splitBeamsHorizontal(beams, beam);
                        split = true;
                    } else if (bMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("RIGHT");
                    } else if (fMirrors.stream().anyMatch(v -> v.getRow() == beam.getRow() && v.getCol() == beam.getCol())) {
                        beam.setDirection("LEFT");
                    }
                    break;
            }
            if (split) {
                continue;
            }
            var next = beam.getAdjacentPoint(beam.getDirection());
            if (isOnGrid(next)) {
                beams.add(new Beam(next.getRow(), next.getCol(), beam.getDirection()));
            }

        }

        Set<Point> visitedPoints = new HashSet<>();
        for (var entry : visitedMap.entrySet()) {
            var parts = entry.getKey().split("_");
            visitedPoints.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
        return visitedPoints.size();
    }

    private void splitBeamsHorizontal(List<Beam> beams, Beam beam) {
        var nextRight = beam.getAdjacentPoint("RIGHT");
        if (isOnGrid(nextRight)) {
            beams.add(new Beam(nextRight.getRow(), nextRight.getCol(), "RIGHT"));
        }
        var nextLeft = beam.getAdjacentPoint("LEFT");
        if (isOnGrid(nextLeft)) {
            beams.add(new Beam(nextLeft.getRow(), nextLeft.getCol(), "LEFT"));
        }
    }

    private void splitBeamsVertical(List<Beam> beams, Beam beam) {
        var nextUp = beam.getAdjacentPoint("UP");
        if (isOnGrid(nextUp)) {
            beams.add(new Beam(nextUp.getRow(), nextUp.getCol(), "UP"));
        }
        var nextDown = beam.getAdjacentPoint("DOWN");
        if (isOnGrid(nextDown)) {
            beams.add(new Beam(nextDown.getRow(), nextDown.getCol(), "DOWN"));
        }
    }
}
