public class Lock {

    private boolean isLocked;
    private Thread threadLock;
    private int acquisitionCount;

    public Lock() {
        isLocked = false;
        acquisitionCount = 0;
    }

    public int getAcquisitionCount() {
        return acquisitionCount;
    }

    public void lock() {
        synchronized (this) {
            if (!Thread.currentThread().equals(threadLock)) {
                while (isLocked) {
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isLocked = true;
                threadLock = Thread.currentThread();
                System.out.println(Thread.currentThread().getName() + " acquired the lock \n"
                    + "acquisition count = " + acquisitionCount);
            } else {
                acquisitionCount++;
                System.out.println(Thread.currentThread().getName() + " holdCount++ (" + acquisitionCount+")");
            }
        }
    }

    public void unlock() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + "unlock attempt, acquisition count = " + acquisitionCount);
            if (acquisitionCount > 0) {
                acquisitionCount--;
            }
            if (acquisitionCount == 0) {
                isLocked = false;
                this.notifyAll();
                System.out
                    .println(Thread.currentThread().getName() + " got out of critical section\n");
            }
        }
    }

}


