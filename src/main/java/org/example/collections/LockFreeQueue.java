package org.example.collections;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<T> {

    private final AtomicReference<Node<T>> head, tail;
    private final AtomicInteger size;

    public LockFreeQueue() {
        // initially, head and tail nodes are dummy, meaning they hold null value
        // it simplifies managing edge cases with empty queue.
        this.head = new AtomicReference<>(null);
        this.tail = new AtomicReference<>(null);
        this.size = new AtomicInteger();
    }

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> currTail;

        // read current tail value and try to make a new node a tail until CAS returns true
        // It means that if any other thread modifies tail in parallel, tail.compareAndSet() returns false
        // and loop continues until CAS succeeds reading new tail each new iteration.
        do {
            currTail = tail.get();
            newNode.setPrev(currTail);
        } while (!tail.compareAndSet(currTail, newNode));

        // Once new node is set up as tail, update next node of previous node to point to the new node
        if (newNode.getPrev() != null) {
            newNode.getPrev().setNext(newNode);
        }

        // If head is null (when queue is empty and head is dummy node), update it to new node
        // so both head and tail are point to the same new node in case of empty queue
        head.compareAndSet(null, newNode);
        size.incrementAndGet();
    }

    public T poll() {
        if (head.get() == null) {
            throw new NoSuchElementException();
        }

        Node<T> currHead;
        Node<T> nextNode;

        // CAS similar to push(). Read current head and try to update it to current head's next node
        // until CAS succeeds
        do {
            currHead = head.get();
            nextNode = currHead.getNext();
        } while (!head.compareAndSet(currHead, nextNode));

        size.decrementAndGet();
        return currHead.getValue();
    }

    public T peek() {
        Node<T> currHead = head.get();
        return currHead == null
                ? null
                : currHead.getValue();
    }

    public int size() {
        return size.get();
    }


    private static class Node<T> {
        // All the variables are volatile to make sure all the threads "see" up-to-date values;
        private final T value;
        private volatile Node<T> next;
        private volatile Node<T> prev;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
}
