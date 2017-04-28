
/**
 * Created by jonathanbeiqiyang on 5/3/17.
 */
public class SynchronizedAccount {

    private int balance;
    private Object withdrawLock = new Object();
    private Object depositLock = new Object();

    public SynchronizedAccount(int balance){
        this.balance = balance;
    }

    public int checkBalance(){
        return balance;
    }

    public void withdraw(int withdrawal){
        synchronized (withdrawLock){
            if(balance >= withdrawal) {
                balance -= withdrawal;
            }
            else{
                System.out.println("Not enough money in specified account to be withdrawn.");
            }
        }
    }

    public void deposit(int depositAmt){
        synchronized (depositLock){
            balance += depositAmt;
        }
    }

}
