public class Account implements Comparable<Account> {

    private long money;
    private String accNumber;
    private volatile boolean isBlocked;

    public Account() {
        money = 100000;
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(
                alphaNumericString.charAt((int) (Math.random() * alphaNumericString.length())));
        }
        accNumber = sb.toString();
        isBlocked = false;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public long getMoney() {
        return money;
    }

    public void giveTransfer(long transferAmount) throws InterruptedException {
        if (!this.isBlocked) {
            if (money <= 0) {
                System.out.println(this.getAccNumber() + " Account balance is to low");
                wait();
            }

            this.money -= transferAmount;


        } else {
            System.out.println("Transfer from " + this.accNumber + " is forbidden.");
        }
    }

    public void receiveTransfer(long transferAmount) {
        if (!this.isBlocked) {
            this.money += transferAmount;
        } else {
            System.out.println("Transfer to " + this.accNumber + " is forbidden.");
        }
        if (money > 0) {
            notifyAll();
        }
    }

    public String getAccNumber() {
        return accNumber;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}
