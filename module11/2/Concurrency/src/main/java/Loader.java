import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {

    static int i;

    static Lock lock = new Lock();

    public static void main(String[] args) throws InterruptedException {
        i = 0;

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(() -> {
            for (int j = 0; j < 100000; j++) {
                lock.lock();
                lock.lock();
                lock.lock();
                try {
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    lock.unlock();
                    lock.unlock();
                }
            }
        });

        service.submit(() -> {
            for (int j = 0; j < 100000; j++) {
                lock.lock();
                lock.lock();
                lock.lock();
                try {
                    i--;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    lock.unlock();
                    lock.unlock();
                }
            }
        });
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        System.out.println(i);
    }
}



