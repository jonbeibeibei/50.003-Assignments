public class MyStackThreadSafe {
	private final int maxSize;
	private long[] stackArray; //guarded by "this"
	private int top; //invariant: top < stackArray.length && top >= -1; guarded by "this"	
	
	//pre-condition: s > 0
	//post-condition: maxSize == s && top == -1 && stackArray != null
	public MyStackThreadSafe(int s) { //Do we need "synchronized" for the constructor?
		maxSize = s;
	    stackArray = new long[maxSize];
	    top = -1;
	}

	//pre-condition: top < maxSize - 1
    //post-condition: j added to the end of the stack
    public synchronized void push(long j){
	    while (isFull()){
	        try{
	            wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stackArray[top+1] = j;
	    notifyAll();
    }
	
	//pre-condition: top >= 0
	//post-condition: the top element is removed
	public synchronized long pop() {
		long toReturn; 
		
		while (isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		toReturn = stackArray[top--];
		notifyAll();			
	    return toReturn;
	}	

	//pre-condition: true
	//post-condition: the elements are un-changed. the return value is true iff the stack is empty.
	public synchronized boolean isEmpty() {

        return (top == -1);
	}

	//pre-condition: true
    //post-condition: the elements are un-changed. the return value is true iff the stack is full.
    public synchronized boolean isFull(){
        return (top == maxSize - 1);
    }

    //pre-condition: top >= 0
    //post-condition: the elements are un-changed, return top value of stack
    public synchronized long peek(){
        return stackArray[top];
    }

}