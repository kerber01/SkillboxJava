public class Lock {

    private volatile boolean isLocked;
    private volatile boolean isCaught;
    private volatile int count;

    public Lock() {
        isLocked = false;
        isCaught = false;
        count = 0;
    }

    public void lock() throws InterruptedException {
        synchronized (this) {
            count++;
            if (isLocked && !isCaught) {
                isCaught = true;
                System.out
                    .println(Thread.currentThread().getName() + ": lock " + count
                        + ", isLocked = " + isLocked);

                System.out.println(Thread.currentThread().getName() + " is caught");
                while (isLocked) {
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isCaught = false;
                    System.out.println(Thread.currentThread().getName() + " released");
                }
            } else {
                //Thread.sleep(100);
                isLocked = true;
            }
        }
    }

    public void unlock() {
        synchronized (this) {
            if (isLocked) {
                System.out.println(Thread.currentThread().getName() + " unlocked another thread");
                this.notify();
                isLocked = false;
            }
        }
    }

}


