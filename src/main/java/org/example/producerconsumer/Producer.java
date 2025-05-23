package org.example.producerconsumer;

import java.util.Random;
import java.util.UUID;

public class Producer implements Runnable {
    private final String producerName;

    private final Object lock;
    private final SharedBuffer<String> buffer;
    private final Random random;


    public Producer(SharedBuffer<String> buffer, Object lock, String producerName) {
        this.producerName = producerName;
        this.lock = lock;
        this.buffer = buffer;
        this.random = new Random();
    }

    @Override
    public void run() {
        // Run until thread is interrupted
        while (true) {
            try {
                // Acquire the common lock. Since both consumer and producer access the common data concurrently,
                // Race condition may occur. Moreover, lock is required for "wait()" operation
                // in order to make it "transactional" because it requires condition while (buffer.isFull())
                synchronized (lock) {
                    produce();
                }

                // Wait between produced values
                Thread.sleep(random.nextInt(0, 1500));
            } catch (InterruptedException e) {
                // In case of interrupted Thread.sleep, wait(), interrupt the thread and exit the endless loop.
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

    private void produce() throws InterruptedException {
        // Use while loop instead if () because wait() can be woken up by thread scheduler accidentally
        while (buffer.isFull()) {
            System.out.println("Queue is full, producer " + producerName + " is waiting...");
            // wait() releases the lock so the consumer can acquire it and consume data in parallel.
            // once consumer calls notify(), producer acquires the lock again and continues thread execution
            // on the other hand, lock can be acquired by the consumer again. So the producer "competes" for the lock with consumer
            lock.wait();
        }
        String data = UUID.randomUUID().toString();
        buffer.offer(data);
        System.out.println("Producer " + producerName + " produced " + data);

        // Notify the waiting consumer. notifyAll() is used if there are many consumers. This method notifies them all.
        lock.notifyAll();
    }
}
