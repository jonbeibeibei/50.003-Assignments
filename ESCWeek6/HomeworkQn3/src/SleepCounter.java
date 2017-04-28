import java.util.Scanner;

/**
 * Created by jonathanbeiqiyang on 5/3/17.
 */
public class SleepCounter {

    public static void main(String[] args) {
        CounterThread counterThread = new CounterThread();
        counterThread.start();

        Scanner input = new Scanner(System.in);

        if(input.nextInt() == 0){
            counterThread.interrupt();
        }
    }
}

class CounterThread extends Thread{

    private int count = 0;

    public void run(){
        while (true){
            count++;
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
                return;
            }
            System.out.println("current count is: " + count);
        }
    }
}
