package org.example.threadpools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {

    public static void main(String[] args) {
        int[] numbers = new int[100];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1; // fill with 1 to 100
        }

        try (ForkJoinPool pool = new ForkJoinPool()) { // Default number of threads is Runtime.getRuntime().availableProcessors()
                                                        // ForkJoinPool is created in try-catch with resources in order to close() method of Closable to be called
                                                        // close() methods shuts down ForkJoinPool on completion and releases the threads.
            SumTask task = new SumTask(numbers, 0, numbers.length);
            int result = pool.invoke(task);
            System.out.println("Sum: " + result);
        }
    }

    static class SumTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 10; // max chunk size before splitting
        private final int[] numbers;
        private final int start;
        private final int end;

        public SumTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += numbers[i];
                }
                return sum;
            } else {
                int mid = start + length / 2;
                SumTask left = new SumTask(numbers, start, mid);
                SumTask right = new SumTask(numbers, mid, end);

                left.fork(); // compute left in another thread
                int rightResult = right.compute(); // compute right in current thread
                int leftResult = left.join(); // wait for left to finish

                return leftResult + rightResult;
            }
        }
    }
}