package org.example.deadlock.diningphilosophers;

public class Philosopher implements Runnable {

    private final int id;
    private final Object leftFork;
    private final Object rightFork;

    public Philosopher(int id, Object leftFork, Object rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep(100);
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep(100);
    }


    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Philosopher thinks for a while (does nothing)
                think();
                Object firstLock = leftFork;
                Object secondLock = rightFork;

                // without ordering, a possible case when all the philosophers take left forks at the same time -> deadlock
                // commenting out these lines simulates deadlock.
                if (id % 2 == 0) {
                    firstLock = rightFork;
                    secondLock = leftFork;
                }

                synchronized (firstLock) {
                    System.out.println("Philosopher " + id + " took first fork");
                    synchronized (secondLock) {
                        System.out.println("Philosopher " + id + " took second fork");
                        //Philosopher eats for a while
                        eat();
                    }

                    System.out.println("Philosopher " + id + " put second fork");
                }
                System.out.println("Philosopher " + id + " put first fork");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
