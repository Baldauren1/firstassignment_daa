package org.example.test;

import org.example.closest.ClosestPair;
import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTests {
    private double slowClosest(ClosestPair.Point[] pts) {  // Naive O(n^2) implementation for checking
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double d = Math.sqrt(dx * dx + dy * dy);
                if (d < best) best = d;
            }
        }
        return best;
    }

    @Test
    void testClosestPairAgainstNaiveForSmallN() {
        Random rnd = new Random(2025);
        for (int n = 50; n <= 2000; n *= 10) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }

            Metrics m = new Metrics();
            double fast = new ClosestPair(m).closest(pts);
            double slow = slowClosest(pts);

            assertEquals(slow, fast, 1e-9, "Mismatch at n=" + n);
        }
    }
}
