package org.example.producerconsumer;

import java.util.Random;

public class Consumer implements Runnable {
    private final String consumerName;

    private final SharedBuffer<String> buffer;
    private final Object lock;
    private final Random random;

    public Consumer(SharedBuffer<String> buffer, Object lock, String consumerName) {
        this.consumerName = consumerName;

        this.buffer = buffer;
        this.lock = lock;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Acquire the common lock. Since both consumer and producer access the common data concurrently,
                // Race condition may occur. Moreover, lock is required for "wait()" operation
                // in order to make it "transactional" because it requires condition while (buffer.isEmpty())
                synchronized (lock) {
                    consume();
                }

                // Higher waiting times make consumer consume less frequently that data is produced, thus
                // simulating full queue.
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("Queue is empty, consumer " + consumerName + " is waiting...");
            // wait() releases the lock so the producer can acquire it and produce a value.
            // once produces calls notify(), consumer acquires the lock again and continues thread execution
            // on the other hand, lock can be acquired by the producer again. So the consumer "competes" for the lock with producer
            lock.wait();
        }

        String value = buffer.poll();
        System.out.println("Consumer " + consumerName + " consumed value " + value);
        lock.notifyAll();
    }
}
