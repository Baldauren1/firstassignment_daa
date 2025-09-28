package org.example.test;

import org.example.sort.MergeSort;
import org.example.sort.QuickSort;
import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SortingTests {

    @Test
    void testMergeSortCorrectnessRandom() {
        Random rnd = new Random(42);
        for (int n = 10; n <= 1000; n *= 10) {
            int[] arr = rnd.ints(n).toArray();
            int[] expected = arr.clone();
            Arrays.sort(expected);

            Metrics m = new Metrics();
            new MergeSort(m).sort(arr);

            assertArrayEquals(expected, arr, "MergeSort failed on random array of size " + n);
        }
    }

    @Test
    void testQuickSortCorrectnessRandom() {
        Random rnd = new Random(123);
        for (int n = 10; n <= 1000; n *= 10) {
            int[] arr = rnd.ints(n).toArray();
            int[] expected = arr.clone();
            Arrays.sort(expected);

            Metrics m = new Metrics();
            new QuickSort(m).sort(arr);

            assertArrayEquals(expected, arr, "QuickSort failed on random array of size " + n);
        }
    }

    @Test
    void testAdversarialArrays() {
        int[] sorted = new int[1000];
        int[] reversed = new int[1000];
        for (int i = 0; i < 1000; i++) {
            sorted[i] = i;
            reversed[i] = 1000 - i;
        }

        int[] expSorted = sorted.clone();
        int[] expReversed = reversed.clone();
        Arrays.sort(expSorted);
        Arrays.sort(expReversed);

        Metrics m1 = new Metrics();
        new MergeSort(m1).sort(sorted);
        assertArrayEquals(expSorted, sorted);

        Metrics m2 = new Metrics();
        new QuickSort(m2).sort(reversed);
        assertArrayEquals(expReversed, reversed);
    }

    @Test
    void testQuickSortRecursionDepthBound() {
        Random rnd = new Random(777);
        int n = 5000;
        int[] arr = rnd.ints(n).toArray();

        Metrics m = new Metrics();
        new QuickSort(m).sort(arr);

        int depth = m.getMaxRecursionDepth();
        int bound = 2 * (int) (Math.log(n) / Math.log(2)) + 5;
        assertTrue(depth <= bound, "QuickSort recursion depth too large: " + depth);
    }
}
