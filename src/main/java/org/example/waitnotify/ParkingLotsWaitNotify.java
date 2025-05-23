package org.example.waitnotify;

import java.util.Random;

public class ParkingLotsWaitNotify {
    public static void main(String[] args) throws InterruptedException {
        ParkingLot parkingLot = new ParkingLot(3);
        
        Thread t1 = new Thread(new CarParking("car1", parkingLot)); 
        Thread t2 = new Thread(new CarParking("car2", parkingLot)); 
        Thread t3 = new Thread(new CarParking("car3", parkingLot)); 
        Thread t4 = new Thread(new CarParking("car4", parkingLot)); 
        Thread t5 = new Thread(new CarParking("car5", parkingLot)); 
        // TODO: Create a runnable for cars trying to enter the parking lot.
        // The runnable should attempt to park a car by invoking the 'enterParkingLot' method and then simulate the car staying parked by pausing the thread.
        // Following this, the runnable should invoke 'exitParkingLot' to make the car leave.

    
        // TODO: Start threads for multiple cars entering and exiting the parking lot.
        // Each thread should be given a unique name representing the car it simulates (e.g., "Car 1").

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        // TODO: Use join() to ensure all car threads complete before the program ends.
        // This guarantees the main program waits for all car activities to finish before exiting.
        
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }
    
    private static class CarParking implements Runnable {
        private final String carId;
        private final ParkingLot parkingLot;
        private final Random random;
        
        public CarParking(String carId, ParkingLot parkingLot) {
            this.carId = carId;
            this.parkingLot = parkingLot;
            this.random = new Random();
        }
        
        @Override
        public void run() {
            try {
                parkingLot.enterParkingLot(carId);
                Thread.sleep(random.nextInt(0, 1000));
                parkingLot.exitParkingLot(carId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}