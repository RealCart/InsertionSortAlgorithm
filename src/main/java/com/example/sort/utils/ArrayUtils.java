package com.example.sort.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ArrayUtils {
    private ArrayUtils() {}

    public static int[] copy(int[] a) {
        if (a == null) throw new IllegalArgumentException("Array must not be null");
        int[] b = new int[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        return b;
    }

    public static boolean isSorted(int[] a, boolean ascending) {
        if (a == null) throw new IllegalArgumentException("Array must not be null");
        for (int i = 1; i < a.length; i++) {
            if (ascending) {
                if (a[i-1] > a[i]) return false;
            } else {
                if (a[i-1] < a[i]) return false;
            }
        }
        return true;
    }

    public static int[] randomIntArray(int size, int distinct, long seed) {
        if (size < 0) throw new IllegalArgumentException("size must be >= 0");
        if (distinct <= 0) distinct = size;
        Random rnd = new Random(seed);
        int[] a = new int[size];
        if (distinct >= size) {
            for (int i = 0; i < size; i++) a[i] = i;
            for (int i = size - 1; i > 0; i--) {
                int j = rnd.nextInt(i + 1);
                int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
            }
        } else {
            for (int i = 0; i < size; i++) a[i] = rnd.nextInt(distinct);
        }
        return a;
    }

    public static int[] loadFrom(Path path) throws IOException {
        String content = Files.readString(path);
        String[] tokens = content.split("[,\s]+");
        List<Integer> list = new ArrayList<>();
        for (String t : tokens) {
            if (t.isBlank()) continue;
            list.add(Integer.parseInt(t.trim()));
        }
        int[] a = new int[list.size()];
        for (int i = 0; i < a.length; i++) a[i] = list.get(i);
        return a;
    }

    public static String toStringLimited(int[] a, int limit) {
        if (a == null) return "null";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < a.length && i < limit; i++) {
            if (i > 0) sb.append(", ");
            sb.append(a[i]);
        }
        if (a.length > limit) sb.append(", ... (").append(a.length - limit).append(" more)]");
        else sb.append("]");
        return sb.toString();
    }
}
