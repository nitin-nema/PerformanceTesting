package com.example.PerformanceTesting;

import org.openjdk.jmh.annotations.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)   // Measure average execution time
@OutputTimeUnit(TimeUnit.MILLISECONDS)  // Output results in milliseconds
@State(Scope.Thread)
public class SortingBenchmark {

    private int[] array;

    @Setup(Level.Invocation) // Called before each benchmark invocation
    public void setUp() {
        array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
    }

    @Benchmark
    public int[] bubbleSort() {
        int[] data = Arrays.copyOf(array, array.length);
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
        return data;
    }

    @Benchmark
    public int[] builtInSort() {
        int[] data = Arrays.copyOf(array, array.length);
        Arrays.sort(data);
        return data;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args); // Runs the benchmark
    }
}
