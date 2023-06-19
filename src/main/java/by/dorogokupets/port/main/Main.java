package by.dorogokupets.port.main;

import by.dorogokupets.port.entity.Ship;
import by.dorogokupets.port.generator.ShipGenerator;

public class Main {
    public static void main(String[] args) {
        ShipGenerator shipGenerator = new ShipGenerator();

        for (int i = 0; i < 10; i++) {
            Ship ship = shipGenerator.createShip();
            ship.start();
        }
    }
}