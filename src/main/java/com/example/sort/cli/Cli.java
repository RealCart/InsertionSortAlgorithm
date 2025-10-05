package com.example.sort.cli;

import com.example.sort.algorithm.InsertionSort;
import com.example.sort.metrics.Metrics;
import com.example.sort.utils.ArrayUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

public final class Cli {
    public static void main(String[] args) {
        try {
            new Cli().run(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private void run(String[] args) throws Exception {
        if (args.length == 0 || hasFlag(args, "--help", "-h")) {
            printHelp();
            return;
        }

        int size = getInt(args, "--size", 20);
        int distinct = getInt(args, "--distinct", size);
        long seed = getLong(args, "--seed", 42L);
        String orderStr = getString(args, "--order", "asc").toLowerCase(Locale.ROOT);
        boolean ascending = orderStr.startsWith("a");
        boolean verify = hasFlag(args, "--verify", null);
        boolean print = hasFlag(args, "--print", null);
        int trials = getInt(args, "--trials", 1);
        String file = getString(args, "--file", null);

        int[] base;
        if (file != null) {
            base = ArrayUtils.loadFrom(Path.of(file));
        } else {
            base = ArrayUtils.randomIntArray(size, distinct, seed);
        }

        Metrics agg = new Metrics();
        long timeSum = 0, compSum = 0, swapSum = 0, shiftSum = 0, readSum = 0, writeSum = 0, accessSum = 0, memSum = 0;

        for (int t = 0; t < trials; t++) {
            int[] a = ArrayUtils.copy(base);
            if (print && a.length <= 100) {
                System.out.println("Before: " + Arrays.toString(a));
            }

            Metrics m = new Metrics();
            InsertionSort.sort(a, ascending, m);

            if (print && a.length <= 100) {
                System.out.println("After : " + Arrays.toString(a));
            }

            if (verify && !ArrayUtils.isSorted(a, ascending)) {
                throw new IllegalStateException("Array is not sorted correctly");
            }

            System.out.println("Trial " + (t + 1) + " -> " + m);

            timeSum += m.getTimeNanos();
            compSum += m.getComparisons();
            swapSum += m.getSwaps();
            shiftSum += m.getShifts();
            readSum += m.getReads();
            writeSum += m.getWrites();
            accessSum += m.getArrayAccesses();
            memSum += m.getMemoryDeltaBytes();
        }

        if (trials > 1) {
            System.out.println("Averages over " + trials + " trials:");
            System.out.printf(Locale.ROOT,
                    "timeNanos=%.1f, comparisons=%.1f, swaps=%.1f, shifts=%.1f, reads=%.1f, writes=%.1f, accesses=%.1f, memoryDeltaBytes=%.1f%n",
                    timeSum / (double) trials, compSum / (double) trials, swapSum / (double) trials,
                    shiftSum / (double) trials, readSum / (double) trials, writeSum / (double) trials,
                    accessSum / (double) trials, memSum / (double) trials);
        }
    }

    private static boolean hasFlag(String[] args, String a, String b) {
        for (String s : args) {
            if (s.equals(a) || (b != null && s.equals(b))) return true;
        }
        return false;
    }

    private static int getInt(String[] args, String key, int def) {
        String v = getString(args, key, null);
        return v == null ? def : Integer.parseInt(v);
    }

    private static long getLong(String[] args, String key, long def) {
        String v = getString(args, key, null);
        return v == null ? def : Long.parseLong(v);
    }

    private static String getString(String[] args, String key, String def) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(key)) return args[i + 1];
        }
        return def;
    }

    private static void printHelp() {
        System.out.println("""
            Insertion Sort CLI
            Usage:
              --help | -h            Show this help
              --size N               Random array size (default: 20). Ignored if --file is set.
              --distinct D           Number of distinct values to generate (<= size yields duplicates; default: size)
              --seed S               RNG seed (default: 42)
              --file PATH            Load integers from file (commas/whitespace separated)
              --order O              asc | desc (default: asc)
              --verify               Check that the output is sorted
              --print                Print arrays if size <= 100
              --trials T             Repeat run T times, reporting averages
            """.strip());
    }
}
