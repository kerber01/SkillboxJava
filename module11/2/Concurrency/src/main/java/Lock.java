public class Lock {

    private boolean isLocked;
    private Thread threadLock;
    private volatile int acquisitionCount;

    public Lock() {
        isLocked = false;
    }

    public void lock() {
        if (!threadLock.equals(Thread.currentThread())) {
            synchronized (this) {
                while (isLocked) {
                    try {
                        acquisitionCount++;
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isLocked = true;
                threadLock = Thread.currentThread();
            }
        }
    }

    public void unlock() {
        synchronized (this) {
            isLocked = false;
            acquisitionCount--;
            if (acquisitionCount == 0){
                this.notifyAll();
            }
        }
    }

}


