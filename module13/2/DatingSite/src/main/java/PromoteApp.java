import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PromoteApp {
    private static final String KEY = "USERS_LIST8";
    private static RDeque<String> usersList;
    private static RedissonClient redisson;

    public static void main(String[] args) {
        redisson = RedissonConnection.init();
        usersList = redisson.getDeque(KEY);
        PromoteApp.createUserList();
        while (true) {
            PromoteApp.promoteNextUser();
            if (new Random().nextInt(10) == 0) {
                PromoteApp.promoteRandomUser();
            }
        }
    }

    static void createUserList() {
        List<String> users = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            users.add(String.valueOf(i));
        }
        usersList.addAll(users);
    }

    static void promoteNextUser() {
        usersList.addLast(usersList.poll());
    }

    static void promoteRandomUser() {
        int randomUser = new Random().nextInt(usersList.size() - 1);
        if (randomUser == 0) {
            randomUser += 1;
        }
        usersList.remove(String.valueOf(randomUser));
        usersList.addFirst(String.valueOf(randomUser));
    }

}