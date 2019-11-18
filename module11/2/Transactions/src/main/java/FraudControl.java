import java.util.Random;

public class FraudControl {

    public FraudControl() {
    }

    private  final Random random = new Random();

    public  synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

}
