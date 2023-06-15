package by.dorogokupets.port.entity;

public class Port {
    private static Port instance;

    private Port() {
    }

//    public synchronized static Port getInstance() {
//        if (instance == null) {
//            instance = new Port();
//        }
//
//        return instance;
//    }

    public static Port getInstanceUsingDoubleLocking() {
        if (instance == null) {
            synchronized (Port.class) {
                if (instance == null) {
                    instance = new Port();
                }
            }
        }
        return instance;
    }
}
