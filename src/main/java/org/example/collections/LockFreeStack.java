package org.example.collections;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {

    private final AtomicReference<Node<T>> head;

    public LockFreeStack() {
        this.head = new AtomicReference<>(null);
    }

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> currHead;

        do {
            currHead = head.get();
            newNode.setPrev(currHead);
        } while (!head.compareAndSet(currHead, newNode));

        // Once new node is set up as tail, update next node of previous node to point to the new node
        if (newNode.getPrev() != null) {
            newNode.getPrev().setNext(newNode);
        }
    }

    public T pop() {
        if (head.get() == null) {
            throw new NoSuchElementException();
        }

        Node<T> currHead;
        Node<T> prevNode;

        do {
            currHead = head.get();
            prevNode = currHead.getPrev();
        } while (!head.compareAndSet(currHead, prevNode));

        if (prevNode != null) {
            prevNode.setNext(null);
        }

        return currHead.getValue();
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
