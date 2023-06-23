package by.dorogokupets.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dorogokupets.port.entity.ShipMessages;

import java.util.concurrent.TimeUnit;

public class Ship extends Thread {
    static final Logger logger = LogManager.getLogger();
    private int shipId;
    private int currentAmountShipContainers;
    private static final int MAX_AMOUNT_SHIP_CONTAINERS = 40;

    public Ship(int shipId, int amountContainers) {
        this.shipId = shipId;
        this.currentAmountShipContainers = amountContainers;
    }

    @Override
    public void run() {
        Port port = Port.getInstanceUsingDoubleLocking();
        Pier pier = null;
        try {
            pier = port.getPier();
        } catch (InterruptedException e) {
            logger.error("Error get pier" + e);
        }
        try {
            if (pier != null) {
                logger.info(shipId + ShipMessages.MSG_SHIP_IN_PIER + pier.getId());
                logger.info(shipId + ShipMessages.MSG_NUMBER_OF_CONTAINERS + currentAmountShipContainers);

                while (currentAmountShipContainers > 0) {
                    TimeUnit.MILLISECONDS.sleep(330);
                    port.unloadContainers();
                    currentAmountShipContainers--;
                    logger.info(shipId + ShipMessages.MSG_UNLOAD_CONTAINERS + currentAmountShipContainers);
                }

                logger.info(shipId + ShipMessages.MSG_SHIP_FINISH_UNLOADING);


                while (port.getCurrentCountPortContainers() > 0 && currentAmountShipContainers < MAX_AMOUNT_SHIP_CONTAINERS) {
                    TimeUnit.MILLISECONDS.sleep(330);
                    port.loadContainers();
                    currentAmountShipContainers++;
                    logger.info(shipId + ShipMessages.MSG_LOAD_CONTAINERS + currentAmountShipContainers);
                }
                logger.info(shipId + ShipMessages.MSG_SHIP_FINISH_LOADING);
            }
        } catch (InterruptedException e) {
            logger.error(shipId + ". Was interrupted.");
        } finally {
            port.releasePier(pier);
            logger.info(shipId + ". Has left Pier " + pier.getId());
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
