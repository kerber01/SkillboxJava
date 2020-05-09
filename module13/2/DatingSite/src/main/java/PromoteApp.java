import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PromoteApp {
    private final static String KEY = "USERS_LIST";
    // Объект для работы с Sorted Set'ом
    private static RScoredSortedSet<Integer> usersList;
    // Объект для работы с Redis
    private RedissonClient redisson;
    // Объект для работы с ключами
    private RKeys rKeys;
    private boolean promoted = false;

    public static void main(String[] args) {

        PromoteApp redis = new PromoteApp();
        redis.init();
        while (true) {
            int step = 30;
            redis.createUserList();
//            redis.printUserList();
            for (int i = 0; i < 20; i++) {
                redis.promoteNextUser(step);
//                redis.printUserList();
                if (new Random().nextInt(10) == 0) {
                    System.out.println("random promotion");
                    redis.promoteRandomUser(step, i);
//                    redis.printUserList();
                    redis.promoteNextUser(step * 2);
                    i += 1;
                }
            }
            System.out.println("cycle ended \r\n");
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

    void createUserList() {
        usersList.addAll(new HashMap<>() {
            {
                for (int i = 1; i < 22; i++) {
                    put(i, (double) i);
                }
            }
        });
    }

    void promoteNextUser(int step) {
        int topUser = ((ArrayList<Integer>) usersList.valueRange(0, 0)).get(0);
        usersList.addScore(topUser, step);
    }

    void promoteRandomUser(int step, int bound) {
        List<Integer> leftToPromoteUsers = ((ArrayList<Integer>) usersList.valueRange(0, 21 - bound));
        int randomUser = new Random().nextInt(leftToPromoteUsers.size() - 2);
        while (randomUser == 0)
            randomUser += 1;
        int user = leftToPromoteUsers.get(randomUser);

        usersList.addScore(user, -step);
    }

    void printUserList() {
        System.out.println(usersList.valueRange(0, -1));
    }


}