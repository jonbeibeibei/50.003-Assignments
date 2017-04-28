import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jonathanbeiqiyang on 20/3/17.
 */
public class HashMapExp {
    public static int numberOfThreads = 3;
    public static int numberOfTests = 10;
    public static long concurrentHashmapTime = 0;
    public static long synchronizedMapTime =0;

    public static void main(String[] args) {
        for (int i = 0 ; i < numberOfTests; i++){
            Map<String, Integer> map = new ConcurrentHashMap<String, Integer>(2);

            long initTime = System.currentTimeMillis();

            Thread[] threads = new Thread[numberOfThreads];
            for (int j = 0; j < numberOfThreads; j++){
//                System.out.println("creating thread " + j);
                threads[j] = new WorkerThread(map);
                threads[j].start();
            }

            try{
                for (int j  = 0; j < numberOfThreads; j++){
                    threads[j].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - initTime;
            concurrentHashmapTime += totalTime;

        }
        System.out.println("Concurrent Hashmap Time: " +  concurrentHashmapTime/numberOfTests);

        for (int i = 0 ; i < numberOfTests; i++){
//            Map<String, Integer> map = new ConcurrentHashMap<String, Integer>(2);
            HashMap threadMap = new HashMap<String, Integer>(2);
            Map<String, Integer> collectionsMap = Collections.synchronizedMap(threadMap);

            long initTime = System.currentTimeMillis();

            Thread[] threads = new Thread[numberOfThreads];
            for (int j = 0; j < numberOfThreads; j++){
//                System.out.println("creating thread " + j);
                threads[j] = new WorkerThread(collectionsMap);
                threads[j].start();
            }

            try{
                for (int j  = 0; j < numberOfThreads; j++){
                    threads[j].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - initTime;
            synchronizedMapTime += totalTime;

        }
        System.out.println("Synchronized Map time: " + synchronizedMapTime/numberOfTests);
    }


}


class WorkerThread extends Thread {
    private Map<String, Integer> map = null;

    public WorkerThread(Map<String, Integer> map) {
        this.map = map;
    }

    public void run() {
        for (int i = 0; i < 500000; i++) {
            // Return 2 integers between 1-1000000 inclusive
            Integer newInteger1 = (int) Math.ceil(Math.random() * 1000000);
            Integer newInteger2 = (int) Math.ceil(Math.random() * 1000000);
            // 1. Attempt to retrieve a random Integer element
            map.get(String.valueOf(newInteger1));
            // 2. Attempt to insert a random Integer element
            map.put(String.valueOf(newInteger2), newInteger2);
        }
    }
}