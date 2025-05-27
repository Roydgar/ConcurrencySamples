package org.example.collections.jdk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedMap {
        private final Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());
    
        public void increment(String key) {
            synchronized (map) { //synchronized is required because map.getOrDefault() and put() are two separate operations
                                 // that need to be combined into one "transaction" to ensure thread safety
                                 // Here's what reentrancy is: map.getOrDefault() is synchronized under the hood
                                 // it basically uses synchronized (this). The same lock is used here, meaning it is used twice.
                                 // The same lock is used twice and the same thread can enter (acquire) the same lock twice.
                                 // meaning lock is re-entrant (re-enter) 
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }

    public void decrement(String key) {
        synchronized (map) {
            map.put(key, map.getOrDefault(key, 0) - 1);
        }
    }

    public void updateAll(int increment) {
        synchronized (map) {
            for (String key: map.keySet()) {
                map.put(key, map.getOrDefault(key, 0) + increment);
            }
        }
    }
    
        public Map<String, Integer> getMap() {
            return map;
        }
    }