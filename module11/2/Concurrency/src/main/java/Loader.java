import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {

    static int i;
    static int k;
    static Lock lock = new Lock();

    public static void main(String[] args) throws InterruptedException {
        i = 0;
        k = 0;

        ExecutorService service = Executors.newFixedThreadPool(2);

        for (int j = 0; j < 100000; j++) {

            service.submit(() -> {
                changeI();
            });
        }
        for (int j = 0; j < 100000; j++) {

            service.submit(() -> {
                changeK();

            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        System.out.println(i);
        System.out.println(k);
    }

    private static void changeI() {

        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        i += 1;


    }

    private static void changeK() {

        try {
            k += i;
        } finally {
            lock.unlock();

        }
    }

}



