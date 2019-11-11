import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

public class BankTest extends TestCase {

    private ConcurrentHashMap<String, Account> accounts;
    private ArrayList<String> keySet;
    private Bank bank;

    public BankTest() {
    }

    @Before
    public void setUp() throws InterruptedException {
        accounts = new ConcurrentHashMap<>();
        bank = new Bank();
        keySet = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Account account = new Account();
            accounts.put(account.getAccNumber(), account);
        }
        for (String s : accounts.keySet()) {
            keySet.add(s);
        }

    }

    @After
    public void tearDown() {
        accounts.clear();
    }


    public void test_transfer() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(() -> {
            try {
                bank.transfer(accounts.get(keySet.get(0)).getAccNumber(),
                    accounts.get(keySet.get(1)).getAccNumber(), 51000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                bank.transfer(accounts.get(keySet.get(1)).getAccNumber(),
                    accounts.get(keySet.get(2)).getAccNumber(), 51000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        if (accounts.get(keySet.get(0)).isBlocked() || accounts.get(keySet.get(1)).isBlocked()) {
            assertEquals(100000, accounts.get(keySet.get(0)).getMoney());
            assertEquals(100000, accounts.get(keySet.get(1)).getMoney());
        } else {
            assertEquals(49000, accounts.get(keySet.get(0)).getMoney());
            assertEquals(100000, accounts.get(keySet.get(1)).getMoney());
            assertEquals(151000, accounts.get(keySet.get(2)).getMoney());
        }


    }
}

