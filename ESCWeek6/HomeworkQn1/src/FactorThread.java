import javax.imageio.stream.FileCacheImageInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jonathanbeiqiyang on 20/2/17.
 */
public class FactorThread {

    public static BigInteger[] answers = new BigInteger[2];
    public static Object lock = new Object();
    public static boolean complete = false;
    public static int numberOfThreads = 4;
    public static BigInteger semiPrimeVal = new BigInteger("299");


    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        BigInteger one = input.nextBigInteger();
        FactorThread main = new FactorThread();
        FactorPrimeThread[] threads = new FactorPrimeThread[numberOfThreads];

        BigInteger numberOfThreadsBig = BigInteger.valueOf(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            BigInteger step = BigInteger.valueOf(i);
            BigInteger lower;
            BigInteger upper;
            if (i == 0) {
                lower = BigInteger.valueOf(2);

            } else {
                lower = step.multiply(semiPrimeVal.divide(numberOfThreadsBig));
            }
            upper = (step.add(BigInteger.ONE)).multiply(semiPrimeVal.divide(numberOfThreadsBig));
            threads[i] = main.new FactorPrimeThread(lower, upper, semiPrimeVal);
            System.out.println("starting Thread " + i);
            threads[i].start();


        }

        main.checkFlag(threads);

    }




    public void checkFlag(FactorPrimeThread[] threads){
//        System.out.println("checking flag");
            synchronized (lock){
                while (!complete){

                    try{

                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Factors are "+ answers[0] + " and " + answers[1]);
                for(int i = 0; i < numberOfThreads; i++){
                    threads[i].interrupt();
                    System.out.println("Thread " + i + " interrupted? " + threads[i].isInterrupted());
                }
            }

    }


    public void setFlag(){
//        System.out.println("setting flag");
        synchronized (lock){
            complete = true;
            lock.notify();
            }
        }


    class FactorPrimeThread extends Thread{
        private BigInteger lower;
        private BigInteger upper;
        private BigInteger semiPrime;

        public FactorPrimeThread(BigInteger lower, BigInteger upper, BigInteger semiPrime){
            this.lower = lower;
            this.upper = upper;
            this.semiPrime = semiPrime;
        }


        public void run(){
            for(BigInteger i = lower; i.compareTo(upper) == -1; i = i.add(BigInteger.ONE)){
                if(isInterrupted()){
                    System.out.println("Process Interrupted, closing Thread...");
                    return;
                }
                if(semiPrime.mod(i) == BigInteger.ZERO){
//                    System.out.println("Setting");
                    answers[0] = i;
                    answers[1] = semiPrime.divide(i);

                    setFlag();

                }

            }
//            System.out.println("comp");
        }


    }
}



