package org.example.cas;

import java.util.concurrent.atomic.AtomicReference;

// This class is thread-safe because it uses a single AtomicReference for both 'lower' and 'upper'
// variables. CAS on a single AtomicReference ensures thread-safety and avoids lost updates and race conditions
// On the other hand, if two separate AtomicInteger were used for  'lower' and 'upper', the class would be not thread-safe
public class CasNumberRange {
    private final AtomicReference<IntPair> values =
            new AtomicReference<IntPair>(new IntPair(0, 0));

    public int getLower() {
        return values.get().lower;
    }

    public void setLower(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i > oldv.upper)
                throw new IllegalArgumentException("Can’t set lower to " + i + " > upper");
            IntPair newv = new IntPair(i, oldv.upper);
            if (values.compareAndSet(oldv, newv))
                return;
        }
    }

    public int getUpper() {
        return values.get().upper;
    }

    /**
     * Immutable class
     * @param lower Invariant: lower <= upper
     */
    private record IntPair(int lower, int upper) {
    }
}