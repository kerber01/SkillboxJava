import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.*;

public class RedisStorage {

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<Integer> usersList;

    private boolean promoted = false;

    private final static String KEY = "USERS_LIST";


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

    void createUserList(){
        usersList.addAll(new HashMap<Integer, Double>() {
            {
                for (int i = 1; i < 21; i++) {
                    put(i, (double) i);
                }
            }

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt(); // Here!
            throw new RuntimeException(ex);
        }
        System.out.println();
    }
    void printUsername(){
        if (usersList.isEmpty()) {
            createUserList();
        }
        int firstUsername = usersList.pollFirst();
        if (promoted){
            System.out.println("> Пользователь " + firstUsername + " оплатил платную услугу");
            promoted = false;
        }
        System.out.println("— На главной странице показываем пользователя " + firstUsername);
    }

    void printRandomUsername(){
        if (!usersList.isEmpty()) {
            ArrayList<Integer> currentUsers = (ArrayList<Integer>) usersList.valueRange(0, -1);
            usersList.addScore(currentUsers.get(new Random().nextInt(currentUsers.size())), -20);
            promoted = true;
        }
        printUsername();
    }
}