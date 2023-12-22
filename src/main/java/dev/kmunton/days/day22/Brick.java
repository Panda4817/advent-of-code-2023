package dev.kmunton.days.day22;

import dev.kmunton.utils.Point3D;
import java.util.HashSet;
import java.util.Set;

public class Brick {

    private final Point3D end1;
    private final Point3D end2;
    private final Set<Point3D> allCubes = new HashSet<>();

    public Brick(Point3D end1, Point3D end2) {
        this.end1 = end1;
        this.end2 = end2;
        for (int x = end1.getX(); x <= end2.getX(); x++) {
            for (int y = end1.getY(); y <= end2.getY(); y++) {
                for (int z = end1.getZ(); z <= end2.getZ(); z++) {
                    allCubes.add(new Point3D(x, y, z));
                }
            }
        }
    }

    public Point3D getEnd1() {
        return end1;
    }

    public Point3D getEnd2() {
        return end2;
    }

    public Set<Point3D> getAllCubes() {
        return allCubes;
    }

    @Override
    public String toString() {
        return "Brick{" +
            "end1=" + end1 +
            ", end2=" + end2 +
            "\n, allCubes=" + allCubes +
            '}';
    }
}
