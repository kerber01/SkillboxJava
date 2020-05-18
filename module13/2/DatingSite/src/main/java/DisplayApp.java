import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;

public class DisplayApp {
    private static final String KEY = "USERS_LIST8";
    private static RDeque<String> usersList;
    private static RedissonClient redisson;

    public static void main(String[] args) {

        redisson = RedissonConnection.init();
        usersList = redisson.getDeque(KEY);

        while (true) {
            System.out.println(usersList.getFirst());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
        }
    }
}
