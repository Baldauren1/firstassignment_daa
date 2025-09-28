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
            if (l == r) return a[l];

            int pivotIndex = medianOfMediansIndex(a, l, r);
            pivotIndex = partition(a, l, r, pivotIndex);

            if (k == pivotIndex) {
                return a[k];
            } else if (k < pivotIndex) {
                r = pivotIndex - 1;
            } else {
                l = pivotIndex + 1;
            }
        }
    }

    private int partition(int[] a, int l, int r, int pivotIndex) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, r);
        int storeIndex = l;
        for (int i = l; i < r; i++) {
            m.comparisons++;
            if (a[i] < pivot) {
                swap(a, i, storeIndex);
                storeIndex++;
            }
        }
        swap(a, storeIndex, r);
        return storeIndex;
    }

    private int medianOfMediansIndex(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            Arrays.sort(a, l, r + 1);
            return l + n / 2;
        }

        int numMedians = (n + 4) / 5;
        for (int i = 0; i < numMedians; i++) {
            int subLeft = l + i * 5;
            int subRight = Math.min(subLeft + 4, r);
            Arrays.sort(a, subLeft, subRight + 1);
            int medianIndex = subLeft + (subRight - subLeft) / 2;
            swap(a, l + i, medianIndex);
        }

        return medianOfMediansIndex(a, l, l + numMedians - 1);
    }

    private void swap(int[] a, int i, int j) {
        if (i != j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
            m.swaps++;
        }
    }
}
