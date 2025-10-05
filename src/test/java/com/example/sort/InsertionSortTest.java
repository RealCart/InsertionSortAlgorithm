package com.example.sort;

import com.example.sort.algorithm.InsertionSort;
import com.example.sort.metrics.Metrics;
import com.example.sort.utils.ArrayUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertionSortTest {

    @Test
    void emptyArray() {
        int[] a = new int[0];
        Metrics m = new Metrics();
        InsertionSort.sort(a, true, m);
        assertTrue(ArrayUtils.isSorted(a, true));
        assertEquals(0, a.length);
    }

    @Test
    void singleElement() {
        int[] a = new int[]{42};
        Metrics m = new Metrics();
        InsertionSort.sort(a, true, m);
        assertTrue(ArrayUtils.isSorted(a, true));
        assertEquals(0, m.getShifts());
    }

    @Test
    void duplicatesHandled() {
        int[] a = new int[]{5, 1, 3, 3, 2, 5, 1};
        Metrics m = new Metrics();
        InsertionSort.sort(a, true, m);
        assertTrue(ArrayUtils.isSorted(a, true));
    }

    @Test
    void alreadySorted() {
        int[] a = new int[]{1,2,3,4,5,6,7};
        Metrics m = new Metrics();
        InsertionSort.sort(a, true, m);
        assertTrue(ArrayUtils.isSorted(a, true));
        // Expect minimal shifts (0)
        assertEquals(0, m.getShifts());
    }

    @Test
    void reverseSorted() {
        int[] a = new int[]{7,6,5,4,3,2,1};
        Metrics m = new Metrics();
        InsertionSort.sort(a, true, m);
        assertTrue(ArrayUtils.isSorted(a, true));
    }

    @Test
    void descendingOrder() {
        int[] a = new int[]{3,1,4,1,5,9,2,6};
        Metrics m = new Metrics();
        InsertionSort.sort(a, false, m);
        assertTrue(ArrayUtils.isSorted(a, false));
        assertFalse(ArrayUtils.isSorted(a, true));
    }

    @Test
    void nullArrayThrows() {
        Metrics m = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> {
            InsertionSort.sort(null, true, m);
        });
    }

    @Test
    void metricsNonNegative() {
        int[] a = ArrayUtils.randomIntArray(100, 100, 123);
        Metrics m = new Metrics();
        InsertionSort.sort(a, true, m);
        assertTrue(m.getComparisons() >= 0);
        assertTrue(m.getReads() >= 0);
        assertTrue(m.getWrites() >= 0);
        assertTrue(m.getArrayAccesses() == m.getReads() + m.getWrites());
    }
}
