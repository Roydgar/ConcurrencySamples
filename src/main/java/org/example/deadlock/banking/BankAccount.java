package org.example.deadlock.banking;

public class BankAccount {
    private final String id;
    private int amount;

    public BankAccount(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return this.id;
    }

    // This method has no monitor intentionally. The operations are synchronized in "transfer()" method
    public void deposit(int amount) {
        this.amount += amount;
        System.out.println("Deposited " + amount + " to account " + id);
    }

    // This method has no monitor intentionally. The operations are synchronized in "transfer()" method
    public boolean withdraw(int toWithdraw) {
        if (toWithdraw > amount) {
            System.out.println("Cannot withdraw more money than balance for account " + id);
            return false;
        }

        this.amount -= toWithdraw;
        System.out.println("Withdraw " + toWithdraw + " from account " + id);
        return true;
    }

    public static void transfer(BankAccount from, BankAccount to, int amount) {
        // Edge case. If ids are equal, it is impossible to design ordering of locks
        // using this criteria. Thus, the transfers are done sequentially and locked globally.
        if (from.getId().equals(to.getId())) {
            synchronized (BankAccount.class) {
                doTransfer(from, to, amount);
            }
        } else {
            doTransfer(from, to, amount);
        }
    }

    public static void doTransfer(BankAccount from, BankAccount to, int amount) {
        // This logic prevents deadlock in case when "from" account transfers to "to" account
        // and "to" account transfers to "from" account at the same time.
        // If such case occurs, both threads are waiting for each other's locks -> deadlock
        // Such a basic logic of comparing ids changes locking ordering.
        BankAccount firstLock = from.getId().compareTo(to.getId()) < 0 ? from : to;
        BankAccount secondLock = from.getId().compareTo(to.getId()) < 0 ? to : from;

        synchronized (firstLock) {
            System.out.println("Acquired lock on bank account " + firstLock.getId());
            synchronized (secondLock) {
                System.out.println("Acquired lock on bank account " + secondLock.getId());

                // given deposit locks two accounts and is thread-safe. Thus, "Amount" can be of any type, not only "int"
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                    System.out.println("Transferred " + amount + " from account " + from.getId() + " to account " + to.getId());
                } else {
                    System.out.println("Could not transfer " + amount + " from account " + from.getId() +
                            " to account " + to.getId() + ". Balance is insufficient");
                }
            }
        }
    }
}
