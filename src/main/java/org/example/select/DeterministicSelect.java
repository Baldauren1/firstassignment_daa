package org.example.select;

import org.example.metrics.Metrics;

import java.util.Arrays;

public class DeterministicSelect {
    private final Metrics m;

    public DeterministicSelect(Metrics m) {
        this.m = m;
    }

    public int select(int[] a, int k) {
        return select(a, 0, a.length - 1, k);
    }

    private int select(int[] a, int l, int r, int k) {
        while (true) {
            if (l == r) {
                return a[l];
            }

            int pivot = medianOfMedians(a, l, r);
            int pivotIndex = partition(a, l, r, pivot);

            if (k == pivotIndex) {
                return a[k];
            } else if (k < pivotIndex) {
                r = pivotIndex - 1;
            } else {
                l = pivotIndex + 1;
            }
        }
    }

    private int partition(int[] a, int l, int r, int pivot) {
        int i = l, j = r;
        while (i <= j) {
            while (i <= j && a[i] < pivot) {
                m.comparisons++;
                i++;
            }
            while (i <= j && a[j] > pivot) {
                m.comparisons++;
                j--;
            }
            if (i <= j) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                m.swaps++;
                i++;
                j--;
            }
        }
        return i - 1;
    }

    private int medianOfMedians(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            Arrays.sort(a, l, r + 1);
            return a[l + n / 2];
        }

        int numMedians = (n + 4) / 5;
        int[] medians = new int[numMedians];

        for (int i = 0; i < numMedians; i++) {
            int start = l + i * 5;
            int end = Math.min(start + 4, r);
            Arrays.sort(a, start, end + 1);
            medians[i] = a[start + (end - start) / 2];
        }

        return medianOfMedians(medians, 0, numMedians - 1);
    }
}
