package by.dorogokupets.port.main;


import by.dorogokupets.port.entity.Ship;
import by.dorogokupets.port.generator.ShipGenerator;


public class Main {
    public static void main(String[] args) {
        int numShips = 10;
        ShipGenerator shipGenerator = new ShipGenerator();
        for (int i = 1; i <= numShips; i++) {
            Ship ship = shipGenerator.createShip();
            ship.start();
        }
    }
}