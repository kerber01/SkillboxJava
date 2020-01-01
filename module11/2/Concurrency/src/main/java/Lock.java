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
        if (!Thread.currentThread().equals(threadLock)) {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " acquired the lock \n"
                    + "acquisition count = " + acquisitionCount);
                while (isLocked) {
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isLocked = true;
                threadLock = Thread.currentThread();
            }
        }
        acquisitionCount++;
    }

    public void unlock() {
        synchronized (this) {
            isLocked = false;
            acquisitionCount--;
            System.out.println("acquisition count = " + acquisitionCount);
            if (acquisitionCount == 0) {
                this.notifyAll();
                System.out.println(threadLock.getName() + " got out of critical section");
                threadLock = null;
                //acquisitionCount = 0;
            }
        }
    }

}


