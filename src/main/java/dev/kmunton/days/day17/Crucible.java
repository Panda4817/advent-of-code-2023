package dev.kmunton.days.day17;

import dev.kmunton.utils.Point;

public class Crucible extends Point {

    private String direction;
    private long totalHeatLoss;
    private int straightSteps;
    public Crucible(int row, int col, String direction, long totalHeatLoss, int straightSteps) {
        super(row, col);
        this.direction = direction;
        this.totalHeatLoss = totalHeatLoss;
        this.straightSteps = straightSteps;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getTotalHeatLoss() {
        return totalHeatLoss;
    }

    public void setTotalHeatLoss(long totalHeatLoss) {
        this.totalHeatLoss = totalHeatLoss;
    }

    public int getStraightSteps() {
        return straightSteps;
    }

    public void setStraightSteps(int straightSteps) {
        this.straightSteps = straightSteps;
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
