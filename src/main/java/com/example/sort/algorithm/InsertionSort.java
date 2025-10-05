package com.example.sort.algorithm;

import com.example.sort.metrics.Metrics;

public final class InsertionSort {
    private InsertionSort() {}


    public static void sort(int[] a, boolean ascending, Metrics m) {
        if (a == null) throw new IllegalArgumentException("Array must not be null");
        if (m == null) throw new IllegalArgumentException("Metrics must not be null");

        m.start();
        standard(a, ascending, m);
        m.stop();
    }

    private static int read(int[] a, int i, Metrics m) { m.incReads(); return a[i]; }
    private static void write(int[] a, int i, int v, Metrics m) { m.incWrites(); a[i] = v; }

    private static int cmp(int x, int y, boolean ascending, Metrics m) {
        m.incComparisons();
        if (ascending) {
            return Integer.compare(x, y);
        } else {
            return Integer.compare(y, x);
        }
    }

    private static void standard(int[] a, boolean ascending, Metrics m) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            int key = read(a, i, m);
            int j = i - 1;
            while (j >= 0 && cmp(read(a, j, m), key, ascending, m) > 0) {
                write(a, j + 1, read(a, j, m), m);
                m.incShifts();
                j--;
            }
            write(a, j + 1, key, m);
        }
    }
}
