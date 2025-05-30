package org.example.collections.jdk.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue {
    private final BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>(2); // Queue with a fixed capacity of 2

    public void addTask(String task) throws InterruptedException {
        taskQueue.put(task); // Blocks if the queue is full
        System.out.println("Task added: " + task);
    }


    // Locks the whole queue like
    //        putLock.lock();
    //        takeLock.lock();
    // Meaning that it will wait to these locks to be released,
    // acquires them and prevents any parallel modification and takes.
    public void cancelTask(String task) {
        boolean isRemoved = taskQueue.remove(task);
        if (isRemoved) {
            System.out.println("Task removed: " + task);
        }
    }

    public void executeTasks() throws InterruptedException {
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.take(); // Blocks if the queue is empty
            System.out.println("Executing: " + task);
        }
    }
}