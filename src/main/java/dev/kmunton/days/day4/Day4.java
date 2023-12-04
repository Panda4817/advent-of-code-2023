package dev.kmunton.days.day4;

import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Day4 implements Day {

    Map<Integer, List<Integer>> winningNumbers = new HashMap<>();
    Map<Integer,List<Integer>> scratchCardNumbers = new HashMap<>();

    public Day4(List<String> input) {
        processData(input);
    }

    public void processData(List<String> input) {
        var cardNumber = 1;
        for (var line : input) {
            var lineSplit = line.split(":");
            var winningScratchCard = lineSplit[1].split(" \\| ");
            var winningNum = Arrays.stream(winningScratchCard[0].split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt).toList();
            var scratchCardNum = Arrays.stream(winningScratchCard[1].split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt).toList();
            winningNumbers.put(cardNumber, winningNum);
            scratchCardNumbers.put(cardNumber, scratchCardNum);
            cardNumber++;
        }
    }

    public long part1() {
        var sumOfPoints = 0;

        for (var entry : scratchCardNumbers.entrySet()) {
            var winningNum = winningNumbers.get(entry.getKey());
            var points = 0;
            for (int number : entry.getValue()) {
                if (winningNum.contains(number)) {
                    if (points == 0) {
                        points += 1;
                    } else {
                        points *= 2;
                    }
                }
            }
            sumOfPoints += points;
        }

        return sumOfPoints;

    }

    public long part2() {
        PriorityQueue<Integer> cardQueue = new PriorityQueue<>();
        scratchCardNumbers.forEach((key, value) -> cardQueue.add(key));
        var cardCounts = new HashMap<Integer, Integer>();
        scratchCardNumbers.forEach((key, value) -> cardCounts.put(key, 1));

        while (!cardQueue.isEmpty()) {
            var card = cardQueue.poll();
            var winningNum = winningNumbers.get(card);
            var scratchCardNum = scratchCardNumbers.get(card);
            var cardsToCopy = new ArrayList<Integer>();
            var nextCard = card + 1;
            for (int number : scratchCardNum) {
                if (winningNum.contains(number)) {
                    cardsToCopy.add(nextCard);
                    cardCounts.put(nextCard, cardCounts.get(nextCard) + 1);
                    nextCard += 1;
                }
            }
            cardQueue.addAll(cardsToCopy);
        }

        return cardCounts.values().stream().mapToInt(Integer::intValue).sum();

    }
}
