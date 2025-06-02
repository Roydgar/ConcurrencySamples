package org.example.collections.jdk.queue.logprocessor;

public interface LogProcessor {
    void addLog(String log);
    void processLogs();
    void stop();
}
