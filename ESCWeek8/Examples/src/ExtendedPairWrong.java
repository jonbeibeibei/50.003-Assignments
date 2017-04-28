public class ExtendedPairWrong extends NewPair {
	public ExtendedPairWrong (int x, int y) {
		super(x, y);
	}
	
	public synchronized void increment () {
		incrementX();
		incrementY();
	}
}

class NewPair {
	private int x;
	private int y;
	// benefit is to allow 2 threads that can access incrementX and incrementY at the same time because they have different locks
	// more effeciency possible?
    // But the problem is that... if we have a method that modifies X and Y, there is a relationship between X and Y
    // So we do not want any modification to be done to X while Y is being modified. In this case that is possible, which might be an
    // issue.
	private Object lockx = new Object ();
	private Object locky = new Object ();
	
	
	public NewPair(int x, int y) { 
		this.x = x;
		this.y = y;
	}
	
	public void incrementX() {
		synchronized (lockx) {
			x++;			
		}
	}
	
	public void incrementY() {
		synchronized (locky) {
			y++;			
		}
	}
}