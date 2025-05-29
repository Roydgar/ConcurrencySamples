package org.example.collections.jdk;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Given class demonstra

//     CopyOnWriteArrayList
//    - Follows immutability pattern
//    - Each update operation creates a new copy of the internal array
//    - Past copies of data are free to be used by the client code since they don't modify up-to-date state of CopyOnWriteArrayList
//    - CopyOnWriteArrayList is good for frequent reads and rare writes. When it is updated, CopyOnWriteArrayList acquires a lock
//        and locks the whole internal array. Then it creates a copy of it with updated invariant and replaces the old array with a new one
//        Thus, reads are highly efficient like reading from a normal array. On the other hand, frequent writes lock the collection.
//    - Iterations through the entire CopyOnWriteArrayList are efficient because they work with their own copy of the list
//       that is not modified in concurrent environment. The bad side of this is non-relevant data: if other threads update the list
//       the updates are not visible in the current iteration because it still works with old copy of the array.
//    - Usage example: a pre-configured (or rare-configured) list of configuration that is frequently accessed

public class CopyOnWriteArrayListExample {
    // or CopyOnWriteArraySet. Set does not support duplicates
    private final List<String> subscribers = new CopyOnWriteArrayList<>();
    
    public void addSubscriber(String subscriber) {
        subscribers.add(subscriber);
    }

    public void sendNewsletter(String message) {
        for (String subscriber : subscribers) { // once iterator is acquired,
                // this code iterates over a current array state
                // if CopyOnWriteArrayList is modified in parallel,
                // the changes are not visible in this loop
               // because write operation creates a new copy of the list
            System.out.println("Sending message to " + subscriber);
            // Does not support removal during iteration:
            // This throws UnsupportedOperationException.
            subscribers.remove(subscriber);
        }
    }

    public void removeAll(Collection<String> subsToRemove) {
        // This operation blocks all the list using the mutex, creates a new copy of the
        // array without given values that are to be removed, and updates
        // internal array with a newly created array.
        subscribers.removeAll(subsToRemove);
    }
}