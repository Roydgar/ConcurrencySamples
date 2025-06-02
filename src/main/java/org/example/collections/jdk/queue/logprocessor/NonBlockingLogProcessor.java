package org.example.collections.jdk.queue.logprocessor;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NonBlockingLogProcessor implements LogProcessor{
    private final ConcurrentLinkedQueue<String> logsQueue;
    private volatile boolean running;

    public NonBlockingLogProcessor() {
        this.logsQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void addLog(String log) {
        logsQueue.add(log);
    }

    @Override
    public void processLogs() {
        this.running = true;
        // Here, busy wait is possible if logs adding rate is less than processing rate
        // In case of empty queue, Thread.wait() is a possible solution
        // But using BlockingQueue is a better way to solve this
        while (running) {
            if (!logsQueue.isEmpty()) {
                String log = logsQueue.poll();
                processLog(log);
            }
        }
    }

    private void processLog(String log) {
        try {
            System.out.println("Processing log " + log);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.running = false;
    }
}