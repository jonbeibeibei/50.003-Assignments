import java.util.concurrent.*;
import junit.framework.TestCase;

public class TestThreadPoolSample extends TestCase {
	
    public void testPoolExpansion() throws InterruptedException {
        int max_pool_size = 10;
        ExecutorService exec = Executors.newFixedThreadPool(max_pool_size);

        for(int i = 0; i < (max_pool_size + 10); i++){
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        Thread.sleep(1000);
       int numberOfThreads = 0;
       if (exec instanceof ThreadPoolExecutor){
           numberOfThreads = ((ThreadPoolExecutor) exec).getActiveCount();
       }

        System.out.println(numberOfThreads);
       assert(numberOfThreads <= max_pool_size);

       exec.shutdownNow();
    }
}
