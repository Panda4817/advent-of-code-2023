package dev.kmunton.days.day16;

import dev.kmunton.utils.Direction;
import dev.kmunton.utils.Point2D;
import java.util.Objects;

public class Beam extends Point2D {

    private Direction direction;
    public Beam(int row, int col, Direction direction) {
        super(row, col);
        this.direction = direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
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
