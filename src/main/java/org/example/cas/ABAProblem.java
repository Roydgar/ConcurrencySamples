package org.example.cas;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABAProblem {
    public void aba() {
        AtomicReference<String> ref = new AtomicReference<String>();

        // Thread 1 - wants to update A to C
        String value = ref.get(); // gets "A"

        // Meanwhile, Thread 2 does this:
        ref.compareAndSet("A", "B"); // changes to "B"
        ref.compareAndSet("B", "A"); // changes back to "A"

        // Back to Thread 1:
        ref.compareAndSet("A", "C"); // Succeeds, but it shouldn't!


        // To solve this problem, AtomicStampedReference<> is used. It uses an addition int stamp field to compare not only values, but also stamps (like versions)
        AtomicStampedReference<String> stampedReference = new AtomicStampedReference<>("A", 1);
    }
}
