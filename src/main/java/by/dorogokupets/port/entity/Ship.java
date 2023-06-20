package by.dorogokupets.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Ship extends Thread {
    static final Logger logger = LogManager.getLogger();
    private static final String MSG_SHIP_IN_PIER = ". approached the pier ";

    private static final String MSG_NUMBER_OF_CONTAINERS = ". Current number of containers on the ship: ";
    private static final String MSG_UNLOAD_CONTAINERS = ". Unloaded a container from the ship. Current ship containers: ";
    private static final String MSG_LOAD_CONTAINERS = ". Loaded a container onto the ship. Current ship containers: ";
    private static final String MSG_SHIP_FINISH_UNLOADING = " has finished unloading.";
    private static final String MSG_SHIP_FINISH_LOADING = " has finished loading.";

    private int shipId;
    private int currentAmountShipContainers;
    private static final int maxAmountShipContainers = 40;

    public Ship() {
    }

    public Ship(int shipId, int amountContainers) {
        this.shipId = shipId;
        this.currentAmountShipContainers = amountContainers;
    }

    @Override
    public void run() {
        try {
            Port port = Port.getInstanceUsingDoubleLocking();
            Pier pier = port.getPier();
            if (pier != null) {
                logger.info(shipId + MSG_SHIP_IN_PIER + pier.getId());
                logger.info(shipId + MSG_NUMBER_OF_CONTAINERS + currentAmountShipContainers);

                while (currentAmountShipContainers > 0) {
                    TimeUnit.MILLISECONDS.sleep(330);
                    port.unloadContainers();
                    currentAmountShipContainers--;
                    logger.info(shipId + MSG_UNLOAD_CONTAINERS + currentAmountShipContainers);
                }

                logger.info(shipId + MSG_SHIP_FINISH_UNLOADING);


                while (port.getCurrentCountPortContainers() > 0 && currentAmountShipContainers < maxAmountShipContainers) {
                    TimeUnit.MILLISECONDS.sleep(330);
                    port.loadContainers();
                    currentAmountShipContainers++;
                    logger.info(shipId + MSG_LOAD_CONTAINERS + currentAmountShipContainers);
                }

                logger.info(shipId + MSG_SHIP_FINISH_LOADING);

                port.releasePier(pier);
                logger.info(shipId + ". Has left Pier " + pier.getId());
            }
        } catch (InterruptedException e) {
            logger.error(shipId + ". Was interrupted.");
        }
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public int getAmountContainers() {
        return currentAmountShipContainers;
    }

    public void setAmountContainers(int amountContainers) {
        this.currentAmountShipContainers = amountContainers;
    }
}
