package dev.kmunton.utils;

import java.util.List;

public class CustomMaths {

    // Greatest Common Factor
    public static long gcf(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return (gcf(b, a % b));
        }
    }

    // Lowest Common Multiple
    public static long lcm(long a, long b) {
        return (a * b) / gcf(a, b);
    }

    // Function to find area of polygon using shoelace formula which takes a list of (x, y) vertices and total number of vertices (n)
    public static double polygonArea(List<Double> X, List<Double> Y,
                                     int n)
    {
        // Initialize area
        double area = 0.0;

        // Calculate value of shoelace formula
        int j = n - 1;
        for (int i = 0; i < n; i++)
        {
            area += (X.get(j) + X.get(i)) * (Y.get(j) - Y.get(i));

            // j is previous vertex to i
            j = i;
        }

        // Return absolute value
        return Math.abs(area / 2.0);
    }
}
