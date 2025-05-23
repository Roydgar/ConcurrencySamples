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
        if (value == oldValue) {
            value = newValue;
            return true;
        }
        return false;
    }
}
