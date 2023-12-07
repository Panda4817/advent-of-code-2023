package dev.kmunton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.kmunton.days.Day;
import dev.kmunton.days.DayException;
import dev.kmunton.days.day1.Day1;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RunnerTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @ParameterizedTest
    @ValueSource(strings = {"hello", "", "-1", "26"})
    void mapDayToClass_incorrectInput_throwDayException(String input) {
        Day obj = Runner.mapDayToClass(input);

        assertEquals(DayException.class, obj.getClass());
    }

    @Test
    void mapDayToClass_correctInput_returnDay() {
        Day obj = Runner.mapDayToClass("1");

        assertEquals(Day1.class, obj.getClass());
    }

    @Test
    void main_emptyArgs_returnMsg() {
        String[] list = {};
        Runner.main(list);

        assertEquals("Provide day number", outputStreamCaptor.toString().trim());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "26", "hello"})
    void main_incorrectDayNumber_printOutErrorMsg(String input) {
        String[] list = {input};
        Runner.main(list);

        assertEquals("Part 1: \nIncorrect day entered, no result\n\nPart 2: \nIncorrect day entered, no result",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void main_tooManyArgs_returnErrorMsg() {
        String[] list = {"1", "2"};
        Runner.main(list);

        assertEquals("Too many days provided. Provide one day.", outputStreamCaptor.toString().trim());
    }

    @Test
    void main_correctDayButNoInput_returnErrorMsg() {
        String[] list = {"2"};
        Runner.main(list);

        assertNotEquals("Part 1: \nIncorrect day entered, no result\n\nPart 2: \nIncorrect day entered, no result",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void loadData_correctFilename_returnList() throws FileNotFoundException {
        String resource = "1.txt";
        var data = Runner.loadData(resource, getClass());

        assertEquals(List.of("a", "b", "c", "d", "e"), data);
    }

    @Test
    void loadData_incorrectFilename_throwException() {
        String resource = "test.txt";

        assertThrows(FileNotFoundException.class, () -> Runner.loadData(resource, getClass()));
    }
}
