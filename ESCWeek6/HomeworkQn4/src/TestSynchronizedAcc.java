/**
 * Created by jonathanbeiqiyang on 5/3/17.
 */
public class TestSynchronizedAcc {

    public static int numberOfThreads = 5;

    public static void main(String[] args) {
        SynchronizedAccount synchronizedAccount = new SynchronizedAccount(0);
        SyncThreads[] threads = new SyncThreads[numberOfThreads];

        for(int i = 0; i < numberOfThreads; i++){
            threads[i] = new SyncThreads(synchronizedAccount,i);
            threads[i].start();
        }

        for(int i = 0; i < numberOfThreads; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final balance is " + synchronizedAccount.checkBalance());
    }
}

class SyncThreads extends Thread{
    private SynchronizedAccount synchronizedAccount;
    private int id;

    public SyncThreads(SynchronizedAccount synchronizedAccount, int id){
        this.synchronizedAccount = synchronizedAccount;
        this.id = id;
    }

    public void run(){
        synchronizedAccount.checkBalance();
        synchronizedAccount.deposit(100);

    }
}
