package dev.kmunton.days.day16;

import static dev.kmunton.utils.Direction.DOWN;
import static dev.kmunton.utils.Direction.LEFT;
import static dev.kmunton.utils.Direction.RIGHT;
import static dev.kmunton.utils.Direction.UP;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 implements Day {
    private final List<List<Integer>> grid = new ArrayList<>();
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
            var rowList = new ArrayList<Integer>();
            for (var j = 0; j < row.length(); j++) {
                var c = row.charAt(j);
                if (c == '|') {
                    rowList.add(1);
                } else if (c == '-') {
                    rowList.add(2);
                } else if (c == '\\') {
                    rowList.add(3);
                } else if (c == '/') {
                    rowList.add(4);
                } else {
                    rowList.add(0);
                }
            }
            grid.add(rowList);
        }
    }

    public long part1() {
        return getTotalEnergisedTiles(new Beam(0, 0, RIGHT));

    }

    public long part2() {
        long max = 0;
        for (var i = 0; i < maxRow; i++) {
            for (var j = 0; j < maxCol; j++) {
                if ((j > 0 && j < maxCol - 1) && (i > 0 && i < maxRow - 1)) {
                    continue;
                }

                if (i == 0) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, DOWN));
                    if (total > max) {
                        max = total;
                    }
                }
                if (i == maxRow - 1) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, UP));
                    if (total > max) {
                        max = total;
                    }
                }

                if (j == 0) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, RIGHT));
                    if (total > max) {
                        max = total;
                    }
                }

                if (j == maxCol - 1) {
                    var total = getTotalEnergisedTiles(new Beam(i, j, LEFT));
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
            var type = grid.get(beam.getRow()).get(beam.getCol());
            switch (beam.getDirection()) {
                case RIGHT:
                    if (type == 1) {
                        splitBeamsVertical(beams, beam);
                        split = true;
                    } else if (type == 3) {
                        beam.setDirection(DOWN);
                    } else if (type == 4) {
                        beam.setDirection(UP);
                    }
                    break;
                case LEFT:
                    if (type == 1) {
                        splitBeamsVertical(beams, beam);
                        split = true;
                    } else if (type == 3) {
                        beam.setDirection(UP);
                    } else if (type == 4) {
                        beam.setDirection(DOWN);
                    }
                    break;
                case UP:
                    if (type == 2) {
                        splitBeamsHorizontal(beams, beam);
                        split = true;
                    } else if (type == 3) {
                        beam.setDirection(LEFT);
                    } else if (type == 4) {
                        beam.setDirection(RIGHT);
                    }
                    break;
                case DOWN:
                    if (type == 2) {
                        splitBeamsHorizontal(beams, beam);
                        split = true;
                    } else if (type == 3) {
                        beam.setDirection(RIGHT);
                    } else if (type == 4) {
                        beam.setDirection(LEFT);
                    }
                    break;
            }
            if (split) {
                continue;
            }
            var next = beam.getAdjacentPoint(beam.getDirection());
            if (next.isOnGrid(maxRow, maxCol)) {
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
        var nextRight = beam.getAdjacentPoint(RIGHT);
        if (nextRight.isOnGrid(maxRow, maxCol)) {
            beams.add(new Beam(nextRight.getRow(), nextRight.getCol(), RIGHT));
        }
        var nextLeft = beam.getAdjacentPoint(LEFT);
        if (nextLeft.isOnGrid(maxRow, maxCol)) {
            beams.add(new Beam(nextLeft.getRow(), nextLeft.getCol(), LEFT));
        }
    }

    private void splitBeamsVertical(List<Beam> beams, Beam beam) {
        var nextUp = beam.getAdjacentPoint(UP);
        if (nextUp.isOnGrid(maxRow, maxCol)) {
            beams.add(new Beam(nextUp.getRow(), nextUp.getCol(), UP));
        }
        var nextDown = beam.getAdjacentPoint(DOWN);
        if (nextDown.isOnGrid(maxRow, maxCol)) {
            beams.add(new Beam(nextDown.getRow(), nextDown.getCol(), DOWN));
        }
    }
}
