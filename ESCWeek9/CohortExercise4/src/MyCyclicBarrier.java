//this class must be thread-safe. why?
public class MyCyclicBarrier {
	private int count = 0;
    private int parties = 0;
	private Runnable torun;
	
	public MyCyclicBarrier (int count, Runnable torun) {
		this.count = count;
		this.parties = count;
		this.torun = torun;
	}

	public MyCyclicBarrier (int count) {
		this.count = count;
	}
	
	//complete the implementation below.
	//hint: use wait(), notifyAll()
	public synchronized void await() throws InterruptedException{
	    parties--;

	    if(parties>0){
	        this.wait();
        }
        else{
	        parties = count;

	        notifyAll();
            torun.run();
        }
    }
}
