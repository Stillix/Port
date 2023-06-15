package by.dorogokupets.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship extends Thread {
    static final Logger logger = LogManager.getLogger();
    private int shipId;
    private int amountContainers;

    public Ship() {
    }

    public Ship(int shipId, int amountContainers) {
        this.shipId = shipId;
        this.amountContainers = amountContainers;
    }

    @Override
    public void run() {

    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public int getAmountContainers() {
        return amountContainers;
    }

    public void setAmountContainers(int amountContainers) {
        this.amountContainers = amountContainers;
    }
}
