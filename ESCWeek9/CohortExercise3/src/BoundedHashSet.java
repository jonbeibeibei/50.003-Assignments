import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
	private final Set<T> set;
	private final Semaphore sem;
	
	public BoundedHashSet(int bound) {
        sem = new Semaphore(bound);
        this.set = Collections.synchronizedSet(new HashSet<T>());
	}
	
	public boolean add(T o) throws InterruptedException {
	    sem.acquire();
	    boolean added = false;

	    try{
	        added = set.add(o);
	        return added;
        }
        finally {
	        if(added == false){
	            sem.release();
            }
        }

	}
	
	public boolean remove (Object o) {
	    boolean removed = set.remove(o);
	    if(removed == true){
	        sem.release();
        }
		return removed;
	}
}