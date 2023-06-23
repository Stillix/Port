package by.dorogokupets.port.util;

import java.util.Random;

import by.dorogokupets.port.entity.Ship;

public class ShipFactory {
    private static final int MAX_AMOUNT_CONTAINERS = 40;
    private int shipIdCounter = 0;
    private static Random random = new Random();

    public Ship createShip() {
        int currentAmountContainers = random.nextInt(MAX_AMOUNT_CONTAINERS + 1);
        int shipId = ++shipIdCounter;
        return new Ship(shipId, currentAmountContainers);
    }
}
