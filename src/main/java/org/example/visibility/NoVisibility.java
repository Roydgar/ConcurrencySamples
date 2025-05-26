package org.example.visibility;


//Visibility:
// When one thread updates shared data (primitive, reference), it may not be immediately visible to other threads.
//    One updated variable can become visible soon, another - never. One variable updated later can become visible earlier
//    that the one updated before it.
//- This issues arise because of CPU caching and operation re-ordering. JVM has different optimization that cache
//    the data for CPU processing and reorder operations
//- In other words, this problem can be called "STALE DATA".
//- with 64-bit primitives like long and double, the problem is even worse: long and double values are updated
//    in two steps: first 32 bits and second 32 bits are updated separately. It means that other threads can read not only
//    stale long's and double's, but it may read value that has only 32-bits updated. It results with completely broken variable

// This issues can be solved by adding "synchronized" keyword to the codebase where data is written/read.
//    Without it, reading data that is updated by other threads is similar to "READ_UNCOMMITED" isolation level, but
//    even worse because it is more critical
//
//    Volatile:
//    - Another way to solve this issue is to use "volatile" keyword. This keyword is a hint to JVM to not cache the variable
//        in the registers and do not perform any re-ordering. It is a light-weight way to solve stale data issue without mutual execution
//    - Volatile helps only with updating variable to get up-to-date value of the variable. It doesn't help
//        with "check-then-act" and non-atomic operations.
//    - When a variable is declared as volatile, it ensures that:
//        No Caching: Every time a thread reads a volatile variable, it reads directly from the main memory (RAM), ensuring that it sees the latest value.
//        Visibility Guarantee: Writes to a volatile variable are immediately written to the main memory, making them visible to all other threads.
public class NoVisibility {
    private static boolean ready;
    private static int number;


    // For example, the following code may print 0 or hang forever if volatile or synchronized are not used:
    private static class ReaderThread extends Thread {
        public void run() {
        while (!ready)
            Thread.yield();
        System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}