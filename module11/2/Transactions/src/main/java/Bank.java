import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {

    private HashMap<String, Account> accounts;
    private static final Object monitor = new Object();
    private static boolean transferIsComplete = true;
    //private final Random random = new Random();
    FraudControl fraudControl;

    public Bank(HashMap<String, Account> accounts, FraudControl fraudControl) {
        this.accounts = accounts;
        this.fraudControl = fraudControl;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    /*
        public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }
    */

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        transferIsComplete = false;
        synchronized (accounts.get(fromAccountNum)) {
            if (!accounts.get(fromAccountNum).isBlocked() && !accounts.get(toAccountNum)
                .isBlocked()) {
                if (amount > 50000 && fraudControl.isFraud(fromAccountNum, toAccountNum, amount)) {
                    accounts.get(fromAccountNum).setBlocked(true);
                    accounts.get(toAccountNum).setBlocked(true);
                    System.out.println("Transfer from or to a blocked account is denied");
                } else {

                    accounts.get(toAccountNum)
                        .receiveTransfer(accounts.get(fromAccountNum).giveTransfer(amount));

                }
            } else {
                System.out.println("Transfer from or to a blocked account is denied");
            }
//            transferIsComplete = true;
//            monitor.notifyAll();
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) throws InterruptedException {
        synchronized (accounts.get(accountNum)) {
//            while (!transferIsComplete) {
//                try {
//                    monitor.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            return accounts.get(accountNum).getMoney();
        }

    }

}
