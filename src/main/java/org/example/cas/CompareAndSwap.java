package org.example.cas;

public class CompareAndSwap {

    private int value;

    private void clientCode() {
        boolean isUpdated = false;

        while (!isUpdated) {
            int oldValue = value;
            // ... do any logic;
            isUpdated = cas(5, oldValue);
        }
    }

    private boolean cas(int newValue, int oldValue) {
        if (value == oldValue) { // THIS LINE IS NOT THREAD-SAFE. Some other thread can change the value after
                                // this if check is passed. Classes like AtomicInteger rely on
                                // Java's CAS operations (e.g., in AtomicInteger, AtomicReference, Unsafe, AQS) rely on CPU-level instructions
                                // to make them atomic
            value = newValue;
            return true;
        }
        return false;
    }
}
