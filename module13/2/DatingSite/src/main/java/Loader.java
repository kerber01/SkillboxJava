import java.util.Random;

public class Loader {
    public static void main(String[] args) {

        RedisStorage redis = new RedisStorage();
        redis.init();
        redis.createUserList();
        Random rand = new Random();



        while (true){
            if (rand.nextInt(10) == 0) {
                redis.printRandomUsername();
                continue;
            }
            redis.printUsername();
        }

    }
}
