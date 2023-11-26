package dev.kmunton.days.day1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    private static Day1 day1;
    @BeforeAll
    static void beforeAll() {
        day1 =  new Day1("1.txt");
    }



    @Test
    void part1() {
        assertEquals(0, day1.part1());
    }

    @Test
    void part2() {
        assertEquals(0, day1.part2());
    }
}
