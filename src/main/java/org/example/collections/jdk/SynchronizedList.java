package org.example.collections.jdk;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class SynchronizedList {
    private final List<String> syncList = Collections.synchronizedList(new ArrayList<>());

    public void addElement(String element) {
        syncList.add(element);
        System.out.println("Added: " + element);
    }

    public void removeElement(String element) {
        syncList.remove(element);
        System.out.println("Removed: " + element);
    }

    public List<String> getList() {
        return syncList;
    }

    public boolean contains(String element) {
        synchronized (syncList) { // Here, synchronization is required because contains() method
                                    // is not implemented in Collections.synchronizedList().
                                    // NOTE: IT WORKS on  Collections.synchronizedList() because it uses the same
                                    // mutex (this) under the hood. The same technique would not work with ConcurrentHashMap
                                    // because it uses different locking mechanism
            return syncList.contains(element);
        }
    }
}