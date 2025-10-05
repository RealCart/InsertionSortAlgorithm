package com.example.sort.metrics;

public final class Metrics {
    private long comparisons;
    private long swaps;
    private long shifts;
    private long reads;
    private long writes;
    private long recursiveCalls;
    private long startTimeNanos;
    private long endTimeNanos;
    private long memoryBytesBefore;
    private long memoryBytesAfter;

    public void start() {
        System.gc();
        Runtime rt = Runtime.getRuntime();
        this.memoryBytesBefore = rt.totalMemory() - rt.freeMemory();
        this.startTimeNanos = System.nanoTime();
    }

    public void stop() {
        this.endTimeNanos = System.nanoTime();
        Runtime rt = Runtime.getRuntime();
        this.memoryBytesAfter = rt.totalMemory() - rt.freeMemory();
    }

    public void incComparisons() { comparisons++; }
    public void incSwaps() { swaps++; }
    public void incShifts() { shifts++; }
    public void incReads() { reads++; }
    public void incWrites() { writes++; }
    public void incRecursiveCalls() { recursiveCalls++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getShifts() { return shifts; }
    public long getReads() { return reads; }
    public long getWrites() { return writes; }
    public long getArrayAccesses() { return reads + writes; }
    public long getRecursiveCalls() { return recursiveCalls; }
    public long getTimeNanos() { return endTimeNanos - startTimeNanos; }
    public long getMemoryDeltaBytes() { return memoryBytesAfter - memoryBytesBefore; }

    @Override
    public String toString() {
        return "Metrics{" +
                "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", shifts=" + shifts +
                ", reads=" + reads +
                ", writes=" + writes +
                ", arrayAccesses=" + getArrayAccesses() +
                ", recursiveCalls=" + recursiveCalls +
                ", timeNanos=" + getTimeNanos() +
                ", memoryDeltaBytes=" + getMemoryDeltaBytes() +
                '}';
    }
}
