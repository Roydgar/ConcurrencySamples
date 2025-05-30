package org.example.collections.jdk.queue;

// Demonstrates the difference between ConcurrentLinkedQueue and LinkedBlockingQueue
// ConcurrentLinkedQueue is non-blocking queue (based on CAS and atomic variables)
// LinkedBlockingQueue utilizes Lock and supports functionality to  block
// if queue is bounded and full, or empty.
//
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Example with LinkedBlockingQueue
        System.out.println("Using LinkedBlockingQueue:");
        TaskQueue blockingQueue = new TaskQueue();
        blockingQueue.addTask("Task 1 (LinkedBlockingQueue)");
        blockingQueue.addTask("Task 2 (LinkedBlockingQueue)");

        System.out.println("Queue is full. The next task will block...");

        new Thread(() -> {
            try {
                System.out.println("Attempting to add Task 3 (LinkedBlockingQueue)...");
                blockingQueue.addTask("Task 3 (LinkedBlockingQueue)"); // This will block until space is available
            } catch (InterruptedException e) {
                System.out.println("Interrupted while waiting to add Task 3 (LinkedBlockingQueue).");
            }
        }).start();

        Thread.sleep(2000); // Simulating delay before consuming tasks

        System.out.println("Removing a task...");
        blockingQueue.executeTasks(); // This will unblock the producer thread

        // Example with ConcurrentLinkedQueue
        System.out.println("Using ConcurrentLinkedQueue:");
        ConcurrentTaskQueue concurrentQueue = new ConcurrentTaskQueue();
        concurrentQueue.addTask("Task 1 (ConcurrentLinkedQueue)");
        concurrentQueue.addTask("Task 2 (ConcurrentLinkedQueue)");
        concurrentQueue.executeTasks();
    }
}