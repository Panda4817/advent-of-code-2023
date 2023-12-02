package dev.kmunton;

import dev.kmunton.days.Day;
import dev.kmunton.days.DayException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Runner {

    public static Day mapDayToClass(String day){
        try {
            Class<Day> dayClass = (Class<Day>) Class.forName("dev.kmunton.days.day" + day + ".Day" + day);
            String resource = day + ".txt";
            var data = loadData(resource, dayClass);
            Class[] cArg = {List.class};
            return dayClass.getDeclaredConstructor(cArg).newInstance(data);
        } catch (Exception ex ) {
            return new DayException();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Provide day number");
            return;
        } else if (args.length > 1) {
            System.out.println("Too many days provided. Provide one day.");
            return;
        }

        Day ans = mapDayToClass(args[0]);

        System.out.println("Part 1: ");
        final long startTimePart1 = System.currentTimeMillis();
        long part1 = ans.part1();
        final long endTimePart1 = System.currentTimeMillis();

        if (part1 > 0) {
            System.out.println(part1);
        }

        if (part1 != -1) {
            System.out.println("Total execution time: " + (endTimePart1 - startTimePart1) + " ms");
        }

        System.out.println();
        System.out.println("Part 2: ");
        final long startTimePart2 = System.currentTimeMillis();
        long part2 = ans.part2();
        final long endTimePart2 = System.currentTimeMillis();

        if (part2 > 0) {
            System.out.println(part2);
        }
        if (part2 != -1) {
            System.out.println("Total execution time: " + (endTimePart2 - startTimePart2) + " ms");
        }

    }

    public static List<String> loadData(String filename, Class clazz) throws FileNotFoundException {
        List<String> data = new ArrayList<>();
        var resource = clazz.getClassLoader().getResource(filename);
        if (Objects.isNull(resource)) {
            throw new FileNotFoundException("File with name [%s] not found".formatted(filename));
        }
        File file = new File(resource.getFile());
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String s = myReader.nextLine();
            data.add(s);
        }
        myReader.close();

        return data;
    }
}
