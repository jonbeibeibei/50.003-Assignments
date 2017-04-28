import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jonathanbeiqiyang on 23/3/17.
 */
public class CountDownLatchExercise {
    private static int gradesTotal = 15;
    private static String[] grades;

    private static int failures = 7;
    private static CountDownLatch latch = new CountDownLatch(failures);

    private static int numberOfThreads = 10;
    private static Thread[] threads = new Thread[numberOfThreads];

    public static void main(String[] args) {
        grades = new String[]{"A","F","F","C","D","F","F","F","F","F","C","B","F","F","F"};

        int size = gradesTotal/numberOfThreads;
        for (int i = 0;  i < numberOfThreads; i++){
            threads[i] = new Grades(grades, i*size, (i+1)*size, "Thread "+ i, latch);
            threads[i].start();
        }

        try{
            latch.await();
            System.out.println(failures + " Fs found");
            for(int i = 0; i < numberOfThreads; i++){
                threads[i].interrupt();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Grades extends Thread{
    private String[] grades;
    private int start;
    private int end;
    private String name;
    private CountDownLatch latch;

    public Grades(String[] grades, int start, int end, String name, CountDownLatch latch){
        this.name = name;
        this.grades = grades;
        this.start = start;
        this.end = end;
        this.latch = latch;
    }

    public void run() {
       for (int i= start; i<end; i++){
           if(isInterrupted()){
               return;
           }
           if(grades[i].equals("F")){
               System.out.println("F found");
               latch.countDown();
           }
       }
    }
}
