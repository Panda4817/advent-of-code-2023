package dev.kmunton.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayExceptionTest {

    private final DayException day = new DayException();

    @Test
    void part1() {
        assertEquals(-1, day.part1());
    }

    @Test
    void part2() {
        assertEquals(-1, day.part2());
    }
}
