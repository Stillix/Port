package by.dorogokupets.port.main;


import by.dorogokupets.port.entity.Ship;
import by.dorogokupets.port.util.ShipFactory;


public class Main {
    public static void main(String[] args) {
        int numShips = 10;
        ShipFactory shipFactory = new ShipFactory();
        for (int i = 1; i <= numShips; i++) {
            Ship ship = shipFactory.createShip();
            ship.start();
        }
    }
}