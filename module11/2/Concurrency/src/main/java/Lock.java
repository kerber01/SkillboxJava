public class Lock {

    private Object monitor;
    private boolean isLocked;

    public Lock() {
        monitor = new Object();
        isLocked = false;
    }

    public void lock() {
        if (!isLocked) {
            synchronized (monitor) {
                while (isLocked) {
                    try {
                        monitor.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isLocked = true;
            }
        }
    }

    public void unlock() {
        if (isLocked) {
            synchronized (monitor) {
                monitor.notify();
                isLocked = false;
            }
        }
    }

}
