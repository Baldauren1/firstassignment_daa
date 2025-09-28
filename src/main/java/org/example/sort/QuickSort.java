package org.example;

import org.example.metrics.Metrics;

import java.util.Random;

public class QuickSort {
    private final Metrics m;
    private final Random rnd = new Random();

    public QuickSort(Metrics m) {
        this.m = m;
    }

    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int l, int r) {
        while (l < r) {
            m.enter();

            if (r - l + 1 <= 16) {
                for (int i = l; i <= r; i++) {
                    for (int j = i; j > l; j--) {
                        m.comparisons++;
                        if (a[j] < a[j - 1]) {
                            int t = a[j];
                            a[j] = a[j - 1];
                            a[j - 1] = t;
                            m.swaps++;
                        } else break;
                    }
                }
                m.exit();
                return;
            }

            // partition
            int p = partition(a, l, r);

            if (p - l < r - p) {
                sort(a, l, p);
                l = p + 1;
            } else {
                sort(a, p + 1, r);
                r = p;
            }
            m.exit();
        }
    }

    private int partition(int[] a, int l, int r) {
        int pivot = a[l + rnd.nextInt(r - l + 1)];
        int i = l - 1;
        int j = r + 1;
        while (true) {
            do {
                i++;
                m.comparisons++;
            } while (a[i] < pivot);

            do {
                j--;
                m.comparisons++;
            } while (a[j] > pivot);

            if (i >= j) {
                return j;
            }

            int t = a[i];
            a[i] = a[j];
            a[j] = t;
            m.swaps++;
        }
    }
}
