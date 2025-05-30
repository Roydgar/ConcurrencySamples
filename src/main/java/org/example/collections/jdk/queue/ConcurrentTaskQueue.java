package org.example.collections.jdk.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentTaskQueue {
    private final Queue<String> taskQueue = new ConcurrentLinkedQueue<>();

    public void addTask(String task) {
        taskQueue.add(task); // Adds task to the non-blocking concurrent queue
        System.out.println("Task added: " + task);
    }

    public void executeTasks() {
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.poll(); // Retrieves and removes the head of the queue
            System.out.println("Executing: " + task);
        }
    }
}