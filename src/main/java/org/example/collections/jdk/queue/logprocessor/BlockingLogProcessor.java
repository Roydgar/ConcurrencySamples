package org.example.collections.jdk.queue.logprocessor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingLogProcessor {
    private final BlockingQueue<String> logsQueue;

    public BlockingLogProcessor() {
        this.logsQueue = new LinkedBlockingQueue<>();
    }

    public void addLog(String log) {
        // Adds value to the queue. LinkedBlockingQueue uses two separate locks: one for reads and a separate one for updates
        // Thus, the queue can be read even when another thread writes to the queue.
        logsQueue.add(log);
    }

    public void processLogs() {
        while (true) {
            try {
                // take() is a blocking operation, meaning it blocks until new value is added to the queue
                // it helps to avoid busy-waits in an efficient manner
                String log = logsQueue.take();
                System.out.println("Processing log " + log);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}