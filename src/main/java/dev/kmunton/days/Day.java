package dev.kmunton.days;

import java.util.List;

public interface Day {

    void processData(List<String> data);
    long part1();
    long part2();
}
