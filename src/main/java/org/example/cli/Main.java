package org.example.cli;

import org.example.metrics.Metrics;
import org.example.sort.MergeSort;
import org.example.sort.QuickSort;
import org.example.select.DeterministicSelect;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        final int[] ns = new int[]{1000, 2000, 5000, 10000, 20000}; // extend if needed
        final int warmups = 3;   // number of warmup runs (not recorded)
        final int trials = 10;   // measured trials per n
        final long baseSeed = 123456L;

        try (FileWriter fw = new FileWriter("metrics_ns.csv")) {
            fw.write("algo,n,trial,time_ns\n");
            for (int n : ns) {
                System.out.println("Running n = " + n);
                for (int t = 0; t < warmups + trials; t++) {
                    long seed = baseSeed + t + (n * 131542391L);
                    Random rnd = new Random(seed);

                    int[] base = new int[n];
                    for (int i = 0; i < n; i++) base[i] = rnd.nextInt();

                    boolean isWarm = t < warmups;
                    int trialIndex = isWarm ? -1 : (t - warmups);

                    // MergeSort
                    {
                        int[] arr = base.clone();
                        Metrics m = new Metrics();
                        long t0 = System.nanoTime();
                        new MergeSort(m).sort(arr);
                        long t1 = System.nanoTime();
                        long dt = t1 - t0;
                        if (!isWarm) fw.write(String.format("mergesort,%d,%d,%d\n", n, trialIndex, dt));
                    }

                    // QuickSort
                    {
                        int[] arr = base.clone();
                        Metrics m = new Metrics();
                        long t0 = System.nanoTime();
                        new QuickSort(m).sort(arr);
                        long t1 = System.nanoTime();
                        long dt = t1 - t0;
                        if (!isWarm) fw.write(String.format("quicksort,%d,%d,%d\n", n, trialIndex, dt));
                    }

                    // Deterministic Select (k = n/2)
                    {
                        int[] arr = base.clone();
                        Metrics m = new Metrics();
                        int k = Math.max(0, n/2 - 1);
                        long t0 = System.nanoTime();
                        int sel = new DeterministicSelect(m).select(arr, k);
                        long t1 = System.nanoTime();
                        long dt = t1 - t0;
                        if (!isWarm) fw.write(String.format("select,%d,%d,%d\n", n, trialIndex, dt));
                    }

                    // Closest Pair (random points)
                    {
                        Random rndPts = new Random(seed ^ 0xCAFEBABEL);
                        org.example.closest.ClosestPair.Point[] pts = new org.example.closest.ClosestPair.Point[n];
                        for (int i = 0; i < n; i++) {
                            pts[i] = new org.example.closest.ClosestPair.Point(rndPts.nextDouble() * 1000.0, rndPts.nextDouble() * 1000.0);
                        }
                        Metrics m = new Metrics();
                        long t0 = System.nanoTime();
                        double d = new org.example.closest.ClosestPair(m).closest(pts);
                        long t1 = System.nanoTime();
                        long dt = t1 - t0;
                        if (!isWarm) fw.write(String.format("closest,%d,%d,%d\n", n, trialIndex, dt));
                    }

                    fw.flush();
                }
            }
        }
        System.out.println("Done. metrics_ns.csv generated."); 
    }
}
