package org.example.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class SharedBuffer<T> {
    private final Queue<T> queue;
    private final int limit;

    public SharedBuffer(int limit) {
        this.queue = new LinkedList<>();
        this.limit = limit;
    }

    public boolean isFull() {
        return queue.size() >= limit;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void offer(T val) {
        queue.offer(val);
    }

    public T poll() {
        return queue.poll();
    }
}
