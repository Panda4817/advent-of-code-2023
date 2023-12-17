package dev.kmunton.days.day16;

import dev.kmunton.utils.Point;
import java.util.Objects;

public class Beam extends Point {

    private String direction;
    public Beam(int row, int col, String direction) {
        super(row, col);
        this.direction = direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Beam beam = (Beam) o;
        return Objects.equals(direction, beam.direction) && super.equals(beam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), direction);
    }

    public String getKey() {
        return this.getRow() + "_" + this.getCol() + "_" + direction;
    }
}
