import java.util.HashMap;

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
        Account from = accounts.get(fromAccountNum);
        Account to = accounts.get(toAccountNum);
        synchronized (fromAccountNum.compareTo(toAccountNum) > 0 ? from : to) {
            if (!from.isBlocked() && !to.isBlocked()) {
                if (amount > 50000 && fraudControl.isFraud(fromAccountNum, toAccountNum, amount)) {
                    from.setBlocked(true);
                    to.setBlocked(true);
                    System.out.println("Transfer from or to a blocked account is denied");
                } else {

                    accounts.get(toAccountNum)
                        .receiveTransfer(from.giveTransfer(amount));

                }
            } else {
                System.out.println("Transfer from or to a blocked account is denied");
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) throws InterruptedException {
        synchronized (accounts.get(accountNum)) {
            return accounts.get(accountNum).getMoney();
        }

    }

}
