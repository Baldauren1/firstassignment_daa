package org.example.test;

import org.example.select.DeterministicSelect;
import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SelectTests {

    @Test
    void testSelectMatchesSortedArray() {
        Random rnd = new Random(999);

        for (int trial = 0; trial < 100; trial++) {
            int n = 100 + rnd.nextInt(200);
            int[] arr = rnd.ints(n, -10000, 10000).toArray();
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int k = rnd.nextInt(n);

            Metrics m = new Metrics();
            int sel = new DeterministicSelect(m).select(arr.clone(), k);

            assertEquals(sorted[k], sel, "Mismatch on trial " + trial + " at k=" + k);
        }
    }
}
