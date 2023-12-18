package dev.kmunton.days.day18;

import dev.kmunton.utils.Direction;

public record DigStep(Direction direction, int steps, String hexColour) {

    @Override
    public String toString() {
        return "DigStep{" +
            "direction=" + direction +
            ", steps=" + steps +
            ", hexColour='" + hexColour + '\'' +
            '}';
    }
}
