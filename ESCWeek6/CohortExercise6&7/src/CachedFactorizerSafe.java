import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FactorizerUser {
	public static void main (String[] args) {
		CachedFactorizerSafe factorizer = new CachedFactorizerSafe();
		Thread tr1 = new Thread (new MyRunnable(factorizer,0));
		Thread tr2 = new Thread (new MyRunnable(factorizer,1));
		tr1.start();
		tr2.start();
		
		try {
			tr1.join();
			tr2.join();
		}
		catch (Exception e) {
            System.out.println("Cannot join threads");
        }
	}
}

class MyRunnable implements Runnable {
	private CachedFactorizerSafe factorizer;
	private int numberCount = 100;
	List<Integer> factorList;
	private int threadNum;
	
	public MyRunnable (CachedFactorizerSafe factorizer, int threadNum) {
		this.factorizer = factorizer;
		this.threadNum = threadNum;
	}
	
	public void run () {
		Random random = new Random ();
		for(int i = 0; i < numberCount; i++) {
            int randomInt = random.nextInt(100);
//            System.out.println(randomInt);
//            System.out.println("Thread number: " + threadNum + " factorising number: " + randomInt);
            factorList = factorizer.service(randomInt);
//            System.out.println("Thread "+ threadNum + " factors are: ");
//            System.out.println(factorList.toString());
//        factorizer.factor(randomInt);
//            factorizer.service(randomInt);
        }
        System.out.println("For Thread " + threadNum + " cache hits are : "+ factorizer.getCacheHitRatio());
    }
}

public class CachedFactorizerSafe {
	private int lastNumber;
	private List<Integer> lastFactors;
	private long hits;
	private long cacheHits;
	private Object serviceLock = new Object();
	
	public long getHits () {
		return hits;
	}
	
	public double getCacheHitRatio () {
		return (double) cacheHits/ (double) hits;
	}

	// making service synchronized
	public List<Integer> service (int input) {
		List<Integer> factors = null;
		synchronized (serviceLock) {
            ++hits;

            if (input == lastNumber) {
                ++cacheHits;
                factors = new ArrayList<Integer>(lastFactors);
            }
        }
		
		if (factors == null) {
			factors = factor(input);
			lastNumber = input;
			lastFactors = new ArrayList<Integer>(factors);
		}

//        System.out.println("hits are " + hits);
//        System.out.println("cache hits are " + cacheHits);

        return factors;
	}
	
	public List<Integer> factor(int n) {		
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
		        factors.add(i);
		        n /= i;
		    }
		}
//		for(int i = 0; i < factors.size(); i++){
//			System.out.println(factors.get(i));
//		}
		return factors;
	}
}