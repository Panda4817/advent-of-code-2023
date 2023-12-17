package dev.kmunton.days.day17;

import dev.kmunton.utils.Point;

public class Block extends Point {

    private final long heatLoss;
    public Block(int row, int col, long heatLoss) {
        super(row, col);
        this.heatLoss = heatLoss;
    }

    public long getHeatLoss() {
        return heatLoss;
    }
}
