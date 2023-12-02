package dev.kmunton.days.day4;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    private static Day4 day;
    private static final List<String> INPUT = """
        """.lines().toList();

    @BeforeAll
    static void beforeAll() {
        day =  new Day4(INPUT);
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
