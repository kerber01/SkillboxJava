import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.ArrayList;

public class DisplayApp {
    private final static String KEY = "USERS_LIST";
    // Объект для работы с Sorted Set'ом
    private static RScoredSortedSet<Integer> usersList;
    // Объект для работы с Redis
    private RedissonClient redisson;
    // Объект для работы с ключами
    private RKeys rKeys;
    private boolean promoted = false;

    public static void main(String[] args) {

        DisplayApp redis = new DisplayApp();
        redis.init();

        while (true) {
            System.out.println(((ArrayList<Integer>) usersList.valueRange(0, 1)).get(0));
        }
    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        usersList = redisson.getScoredSortedSet(KEY);
    }
}
