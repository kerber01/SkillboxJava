public class Lock {

    private boolean isLocked;
    private String threadLockName;
    //private volatile int count;

    public Lock() {
        isLocked = false;
    }

    public void lock() {
        if (!threadLockName.equals(Thread.currentThread().getName())) {
            synchronized (this) {
                while (isLocked) {
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isLocked = true;
                threadLockName = Thread.currentThread().getName();
            }
        }
    }

    public void unlock() {
        synchronized (this) {
            isLocked = false;
            this.notifyAll();
        }
    }

}


