package dev.kmunton.days.day5;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {

    private static Day5 day;
    private static final List<String> INPUT = """
        """.lines().toList();
    @BeforeAll
    static void beforeAll() {
        day =  new Day5(INPUT);
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
