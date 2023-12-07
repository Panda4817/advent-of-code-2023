package dev.kmunton.days.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day12Test {

    private static Day12 day;
    private static final List<String> INPUT = """
        """.lines().toList();
    @BeforeAll
    static void beforeAll() {
        day =  new Day12(INPUT);
    }

    @Test
    void part1() {
        assertEquals( 0, day.part1());

    }

    @Test
    void part2() {
        assertEquals( 0, day.part2());
    }
}
