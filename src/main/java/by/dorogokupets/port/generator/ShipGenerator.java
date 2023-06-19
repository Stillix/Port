package by.dorogokupets.port.generator;

import java.util.Random;

import by.dorogokupets.port.entity.Ship;

public class ShipGenerator {
    private int shipIdCounter = 0;
    private Random random = new Random();

    public Ship createShip() {
        int maxAmountContainers = 50;
        int currentAmountContainers = random.nextInt(maxAmountContainers + 1);
        int shipId = ++shipIdCounter;
        return new Ship(shipId, currentAmountContainers);
    }
}
