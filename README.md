# Insertion Sort (Instrumented, Java 17)

A clean, documented, and tested insertion sort implementation with metrics and a CLI.

## Features

- **Code quality:** JavaDoc, input validation, clear structure
- **Metrics:** comparisons, swaps, shifts, reads, writes, total array accesses, time (ns), memory delta
- **Optimizations:** choose `standard`, `binary` (binary search for insertion point), or `guarded` (sentinel)
- **CLI:** generate random data or load from a file, run multiple trials, verify, print arrays
- **Tests:** JUnit 5 covering edge cases

## Build

```bash
mvn -q -f pom.xml -DskipTests=false test
mvn -q -f pom.xml -DskipTests=true package
```

## Run

```bash
java -cp target/insertion-sort-1.0-SNAPSHOT.jar com.example.sort.cli.Cli --help
```

### Examples

Random 50 numbers (distinct), standard ascending:
```bash
java -cp target/insertion-sort-1.0-SNAPSHOT.jar com.example.sort.cli.Cli --size 50 --algo standard --order asc --verify
```

Random with duplicates, binary insertion, descending, 3 trials:
```bash
java -cp target/insertion-sort-1.0-SNAPSHOT.jar com.example.sort.cli.Cli --size 1000 --distinct 50 --algo binary --order desc --trials 3
```

Load from file (whitespace or commas):
```bash
java -cp target/insertion-sort-1.0-SNAPSHOT.jar com.example.sort.cli.Cli --file data.txt --algo guarded --order asc --print --verify
```

## Data file format
A plain text file containing integers separated by whitespace and/or commas:
```
10, 5, 5, 3
2 1 4 9
```

## Notes on Metrics

- **Array accesses** = reads + writes (counted at each element access).
- **Swaps** are counted only when two elements are directly exchanged (rare in insertion sort; the guarded variant does exactly one swap to place the sentinel).
- **Shifts** count the number of single-position moves during insertion.
- **Recursive calls** remain 0 (non-recursive algorithm).
- **Memory delta** is measured as a rough `usedMemoryAfter - usedMemoryBefore` around the sort call and is **approximate** due to the JVM/GC.
