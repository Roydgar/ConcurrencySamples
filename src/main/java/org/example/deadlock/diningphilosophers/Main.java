package org.example.deadlock.diningphilosophers;

public class Main {

    public static final int NUMBER_OF_PHILOSOPHERS = 5;

    // Simulates 5 philosophers problem
    public static void main(String[] args) throws InterruptedException {
        Object[] forks = new Object[NUMBER_OF_PHILOSOPHERS];
        Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
        Thread[] threads = new Thread[NUMBER_OF_PHILOSOPHERS];

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i + 1, forks[i], forks[(i + 1) % NUMBER_OF_PHILOSOPHERS]);
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }

        Thread.sleep(30000);

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            threads[i].interrupt();
        }

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            threads[i].join();
        }
    }
}
