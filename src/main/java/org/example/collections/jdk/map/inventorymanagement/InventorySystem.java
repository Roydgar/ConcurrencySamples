package org.example.collections.jdk.map.inventorymanagement;

import java.util.concurrent.ConcurrentHashMap;

public class InventorySystem {
    private static final int RE_ORDER_QUANTITY = 10;
    
    private final ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();

    public void addItem(String item, int quantity) {
        inventory.merge(item, quantity, Integer::sum);
    }

    public void removeItem(String item, int quantity) {
        inventory.computeIfPresent(item, (key, val) -> val - quantity > 0 ? val - quantity : null);
    }

    public int getQuantity(String item) {
        return inventory.getOrDefault(item, 0);
    }

    public void removeByPercentage(String item, int percentage) {
        inventory.computeIfPresent(item, (key, val) -> val - (val * percentage / 100));
    }

    public void displayInventory() {
        inventory.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    // TODO: Implement a method called reorderItem that triggers an automatic re-order when stock falls below a threshold
    // TODO: Use the compute() method to update the quantity of the item in a thread-safe manner
    // TODO: Print a message when the reorder happens and update the item's stock
    public void reorderItem(String item, int threshold) {
        inventory.compute(item, (_, quantity) -> {
           if (quantity == null || quantity <= threshold) {
                int newQuantity = quantity == null 
                    ? RE_ORDER_QUANTITY
                    : quantity + RE_ORDER_QUANTITY;
                    
                System.out.println("Reordered item " + item + ". Old quantity: " + quantity + ". New quantity: " + newQuantity);
                
                return newQuantity;
           } else {
                return quantity;
           }    
        });
    }

}