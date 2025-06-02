package org.example.collections.jdk.queue.logprocessor;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingLogProcessor processor = new BlockingLogProcessor();

        Thread logProcessingThread = new Thread(processor::processLogs);

        Runnable addLogTask = () -> {
            for (int i = 0; i < 20; i++) {
                String logValue = UUID.randomUUID().toString();
                System.out.println("Thread " + Thread.currentThread().getName() + " is adding log " + logValue);
                processor.addLog(logValue);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread addThread1 = new Thread(addLogTask);
        Thread addThread2 = new Thread(addLogTask);

        logProcessingThread.start();
        addThread1.start();
        addThread2.start();

        addThread1.join();
        addThread2.join();

        Thread.sleep(5000);
//        processor.stop();
        System.out.println("Finished processing logs");
        logProcessingThread.interrupt();
        logProcessingThread.join();
    }
}
