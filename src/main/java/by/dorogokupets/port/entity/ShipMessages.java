package by.dorogokupets.port.entity;

public class ShipMessages {
    private ShipMessages() {
    }

    static final String MSG_SHIP_IN_PIER = ". approached the pier ";
    static final String MSG_NUMBER_OF_CONTAINERS = ". Current number of containers on the ship: ";
    static final String MSG_UNLOAD_CONTAINERS = ". Unloaded a container from the ship. Current ship containers: ";
    static final String MSG_LOAD_CONTAINERS = ". Loaded a container onto the ship. Current ship containers: ";
    static final String MSG_SHIP_FINISH_UNLOADING = " has finished unloading.";
    static final String MSG_SHIP_FINISH_LOADING = " has finished loading.";
}
