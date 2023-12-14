package dev.kmunton.days.day14;

import dev.kmunton.days.Day;
import dev.kmunton.utils.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day14 implements Day {

    private Set<Point> rocks = new HashSet<>();
    private final Set<Point> walls = new HashSet<>();
    private record State(long cycle, HashSet<Point> rocks) {}
    private final Map<Integer, Integer> rowToLoad = new HashMap<>();
    private int maxRows = 0;
    private int maxCols = 0;

    public Day14(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        var load = input.size() + 1;
        maxRows = input.size();
        maxCols = input.get(0).length();
        for (int r = 0; r< input.size(); r++) {
            load--;
            rowToLoad.put(r, load);

            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) == 'O') {
                    rocks.add(new Point(r, c));
                }
                if (input.get(r).charAt(c) == '#') {
                    walls.add(new Point(r, c));
                }
            }
        }
    }

    public long part1() {
        tiltRocksUp();
        return rocks.stream().mapToInt(r -> rowToLoad.get(r.getRow())).sum();

    }

    public long part2() {
        var cycle = 1000000000;
        var states = new HashMap<String, State>();
        long i = 1;
        while (i <= cycle) {
            var key = rocks.stream().map(Point::toString).reduce("", String::concat);
            if (states.containsKey(key)) {
                rocks = new HashSet<>(states.get(key).rocks);
                i += ((cycle - i) / (i - states.get(key).cycle) * (i - states.get(key).cycle)) + 1;
                continue;
            }

            tiltRocksUp();
            tiltRocksLeft();
            tiltRocksDown();
            tiltRocksRight();

            states.put(key, new State(i, new HashSet<>(rocks)));
            i++;

        }
        return rocks.stream().mapToInt(r -> rowToLoad.get(r.getRow())).sum();
    }

    private void tiltRocksUp() {
        for (var r = 0; r < maxRows; r++) {
            for (var c = 0; c < maxCols; c++) {
                var rock = new Point(r, c);
                if (walls.contains(rock)) {
                    continue;
                }
                if (!rocks.contains(rock)) {
                    continue;
                }
                rocks.remove(rock);
                while (rock.canMoveUp(walls, rocks)) {
                    rock.move("UP");
                }
                rocks.add(rock);
            }
        }
    }

    private void tiltRocksLeft() {
        for (var c = 1; c < maxCols; c++) {
            for (var r = 0; r < maxRows; r++) {
                var rock = new Point(r, c);
                if (walls.contains(rock)) {
                    continue;
                }
                if (!rocks.contains(rock)) {
                    continue;
                }
                rocks.remove(rock);
                while (rock.canMoveLeft(walls, rocks)) {
                    rock.move("LEFT");
                }
                rocks.add(rock);
            }
        }
    }

    private void tiltRocksDown() {
        for (var r = maxRows-2; r >= 0; r--) {
            for (var c = 0; c < maxCols; c++) {
                var rock = new Point(r, c);
                if (walls.contains(rock)) {
                    continue;
                }
                if (!rocks.contains(rock)) {
                    continue;
                }
                rocks.remove(rock);
                while (rock.canMoveDown(maxRows, walls, rocks)) {
                    rock.move("DOWN");
                }
                rocks.add(rock);
            }
        }
    }

    private void tiltRocksRight() {
        for (var c = maxCols-2; c >= 0; c--) {
            for (var r = 0; r < maxRows; r++) {
                var rock = new Point(r, c);
                if (walls.contains(rock)) {
                    continue;
                }
                if (!rocks.contains(rock)) {
                    continue;
                }
                rocks.remove(rock);
                while (rock.canMoveRight(maxCols, walls, rocks)) {
                    rock.move("RIGHT");
                }
                rocks.add(rock);
            }
        }
    }
}
