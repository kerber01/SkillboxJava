public class Lock {

    private volatile boolean isLocked;

    public Lock() {
        isLocked = false;
    }

    public void lock() {
        if (!isLocked) {
            synchronized (this) {
                while (isLocked) {
                    try {
                        this.wait();
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
            synchronized (this) {
                this.notify();
                isLocked = false;
            }
        }
    }

}
