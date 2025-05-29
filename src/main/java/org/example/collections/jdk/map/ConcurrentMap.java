package org.example.collections.jdk.map;

import java.util.concurrent.ConcurrentHashMap;


// ConcurrentHashMap
//    - Comparing to Synchronized collections, use different algorithms to achieve thread safety, thus more effective
//    - ConcurrentHashMap is based on  segment locking. It means that one segment (bucket) of a map is locked, but other are free to use. Only write operations are locked. Also, it uses CAS for operations like putIfAbsent()
//    - Does not support null keys
//    - Need to be careful with aggregation operations since reads do not lock data. Some other threads can modify map in parallel.
//    - Iteration through all the ConcurrentHashMap (like for loop) is Weakly Consistent. It means that the changes made by other threads to the ConcurrentHashMap are not immediately visible.
//      A good tone is to create a short-lived "snapshot" of ConcurrentHashMap when iterating through it (like new HashMap(myConcurrentHashMap)) and use compute, forEach, reduce methods.
//      For cases when real-time consistensy and visibility is required, ConcurrentHashMap is not the best solution.
//    - Even ConcurrentHashMap is thread-safe, its values should also be immutable and thread-safe
//    - ConcurrentNavigableMap is the same, but is based on TreeMap thus maintains ordering.
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