package dev.kmunton.days;

import java.util.List;

public class DayException implements Day{

    @Override
    public void processData(List<String> input) {
    }

    public long part1() {
        System.out.println("Incorrect day entered, no result");
        return -1;
    }

    public long part2() {
        System.out.println("Incorrect day entered, no result");
        return -1;
    }
}
