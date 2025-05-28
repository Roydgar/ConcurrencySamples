package org.example.collections.jdk.map;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMap {
    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public void incrementCount(String key) {
        // Despite using ConcurrentHashMap, this operation is not atomic and not thread-safe
        // This is a compound operation made of two atomic ones: getOrDefault() and put()
        // This causes lost updates (when two threads increment in parallel at the same time)
        // Also, this code can throw ConcurrentModificationException()
//        map.put(key, map.getOrDefault(key, 0) + 1);

        // To make it thread safe, the following single atomic method can be used:
        // merge() puts 1 if value by given key is absent or gets the current value of key if it is present, increments
        // it by 1 and puts a new value to map. Since this is a part of a single atomic method of ConcurrentHashMap,
        // this code is thread-safe
        map.merge(key, 1, Integer::sum);
    }

    public void remove(String key) {
        map.remove(key);
    }


    public void displayMap() {
        map.forEach((key, count) -> System.out.println(key + ": " + count));
    }
}