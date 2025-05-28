package org.example.collections.jdk.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Represents thread-safe SessionManager that utilizes ConcurrentHashMap.
// Note: the operations is thread-safe unless compound actions are used (like check-then-act)
public class SessionManager {
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public void createSession(String userId, String sessionData) {
        sessions.putIfAbsent(userId, sessionData);
    }

    public void updateSession(String userId, String newSessionData) {
        sessions.put(userId, newSessionData);
    }

    public void removeSession(String userId) {
        sessions.remove(userId);
    }

    public String getSession(String userId) {
        return sessions.get(userId);
    }

    public int getActiveSessionCount() {
        return sessions.size();
    }

    public static void main(String[] args) throws InterruptedException {
        SessionManager sessionManager = new SessionManager();

        // Thread to create and update sessions
        Thread t1 = new Thread(() -> {
            // Create a session for user1 and update the session data
            sessionManager.createSession("user1", "data1");
            sessionManager.updateSession("user1", "updateddata1");
        });

        // Thread to create another session and remove one
        Thread t2 = new Thread(() -> {
            // Create a session for user2 and remove the session for user1
            sessionManager.createSession("user2", "data2");
            sessionManager.removeSession("user1");
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        //  Print the session details for user2 and the total count of active sessions
        System.out.println("Session details for user1: " + sessionManager.getSession("user1"));
        System.out.println("Session details for user2: " + sessionManager.getSession("user2"));
        System.out.println("Total count of active sessions: " + sessionManager.getActiveSessionCount());
    }
}