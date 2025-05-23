package org.example.producerconsumer;

public class Main {

    // This class runs "producer-consumer" simulation for 30 secs
    // Producers and consumers share common data buffer
    public static void main(String[] args) throws InterruptedException {
        SharedBuffer<String> buffer = new SharedBuffer<>(10);
        Object lock = new Object();

        Consumer consumer = new Consumer(buffer, lock, "consumer1");
        Consumer consumer2 = new Consumer(buffer, lock, "consumer2");
        Producer producer = new Producer(buffer, lock, "producer1");
        Producer producer2 = new Producer(buffer, lock, "producer2");

        Thread consumerThread = new Thread(consumer);
        Thread consumerThread2 = new Thread(consumer2);
        Thread producerThread = new Thread(producer);
        Thread producerThread2 = new Thread(producer2);

        consumerThread.start();
        consumerThread2.start();
        producerThread.start();
        producerThread2.start();

        Thread.sleep(30000);

        consumerThread.interrupt();
        consumerThread2.interrupt();
        producerThread.interrupt();
        producerThread2.interrupt();

        consumerThread.join();
        consumerThread2.join();
        producerThread.join();
        producerThread2.join();

        System.out.println("Finished simulation");
    }
}
