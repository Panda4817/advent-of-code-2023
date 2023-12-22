package dev.kmunton.days.day17;

import dev.kmunton.utils.Direction;
import dev.kmunton.utils.Point2D;

public class Crucible extends Point2D {

    private final Direction direction;
    private final long totalHeatLoss;
    private final int straightSteps;
    public Crucible(int row, int col, Direction direction, long totalHeatLoss, int straightSteps) {
        super(row, col);
        this.direction = direction;
        this.totalHeatLoss = totalHeatLoss;
        this.straightSteps = straightSteps;
    }

    public Direction getDirection() {
        return direction;
    }

    public long getTotalHeatLoss() {
        return totalHeatLoss;
    }

    public int getStraightSteps() {
        return straightSteps;
    }

    @Override
    public String toString() {
        return "Crucible{" +
            "direction='" + direction + '\'' +
            ", totalHeatLoss=" + totalHeatLoss +
            ", straightSteps=" + straightSteps +
            '}';
    }

    public String getKey() {
        return this.getRow() + "_" + this.getCol() + "_" + direction + "_" + straightSteps;
    }
}
