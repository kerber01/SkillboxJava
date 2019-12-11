public class Lock {

    private boolean isLocked;
    private boolean isCaught;
    //private volatile int count;

    public Lock() {
        isLocked = false;
    }

    public void lock() {
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

    public void unlock() {
        synchronized (this) {
            isLocked = false;
            this.notifyAll();
        }
    }

}


