package org.example.collections;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        LockFreeQueue<Integer> queue = new LockFreeQueue<>();

        // Producer thread
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.push(i);
                System.out.println("Enqueued: " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {

                Integer value = queue.peek();
                System.out.println("Peeked: " + value);
                value = queue.poll(); // Now dequeue the value and print it
                System.out.println("Dequeued: " + value);

                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Queue size: " + queue.size());
    }
}
