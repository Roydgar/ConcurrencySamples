package org.example.waitnotify;

public class ParkingLot {
    // TODO: Declare a variable to track available parking spots. This should be an integer initialized in the constructor.
    
    private int availableSlots;
    
    public ParkingLot(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    // TODO: Implement the constructor with an integer parameter to initialize the number of available spots.

    // TODO: Write the synchronized method 'enterParkingLot' which accepts a String parameter for the car's identifier.
    
    public synchronized void enterParkingLot(String carId) {
        while (availableSlots == 0) {
            System.out.println("Car " + carId + " is waiting for a parking slot");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        availableSlots--;
        System.out.println(carId + " has parked. Available slots: " + availableSlots);
    }
    // If there are no available spots, the method should invoke wait() and print "<car> is waiting for a parking spot...".
    // When a spot becomes available, decrement the available spots and print "<car> has parked. Available spots: <number>".

    // TODO: Write the synchronized method 'exitParkingLot' which accepts a String parameter for the car's identifier.
    // This method should increment the available spots, print "<car> has left. Available spots: <number>", and then notify waiting cars.
    
    public synchronized void exitParkingLot(String carId) {
        availableSlots++;
        System.out.println(carId + " has left. Available slots: " + availableSlots);
        notify();
    }
}