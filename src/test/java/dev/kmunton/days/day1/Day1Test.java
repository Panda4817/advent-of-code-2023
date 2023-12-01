package dev.kmunton.days.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    private static Day1 day1;



    @Test
    void part1() {
        day1 =  new Day1("1.txt");
        assertEquals(142, day1.part1());
    }

    @Test
    void part2() {
        day1 =  new Day1("1b.txt");
        assertEquals(364, day1.part2());
    }
}
