package org.example.collections.jdk.map.inventorymanagement;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InventorySystem inventory = new InventorySystem();

        Thread t1 = new Thread(() -> {
            inventory.addItem("Milk", 10);
            inventory.addItem("Eggs", 5);
        });

        Thread t2 = new Thread(() -> {
            inventory.removeItem("Milk", 8);
            inventory.reorderItem("Milk", 10);
            inventory.reorderItem("Eggs", 4);
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        inventory.displayInventory();
    }
}