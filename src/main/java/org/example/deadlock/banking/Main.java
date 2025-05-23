package org.example.deadlock.banking;

public class Main {

    // Simulates simultaneous banking transfers between two bank accounts
    // Demonstrates how lock ordering prevents deadlocks.
    // Without lock ordering, the given example produces "left-right" deadlock.
    public static void main(String[] args) throws InterruptedException {
        BankAccount firstAccount = new BankAccount("1", 100);
        BankAccount secondAccount = new BankAccount("2", 100);

        Thread t1 = new Thread(() -> {
            while (true) {
                BankAccount.transfer(firstAccount, secondAccount, 100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                BankAccount.transfer(secondAccount, firstAccount, 100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        t1.start();
        t2.start();

        Thread.sleep(30000);

        t1.interrupt();
        t2.interrupt();
        t1.join();
        t2.join();
    }
}
