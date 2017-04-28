import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentSlidingGame {
	//The following models the class sliding game.
	//The following is the board setting.
	// 0 	1 	2 	
	// 3    4 	5 	
	// 6 	7   8

	static class ThreadSafety<PuzzleNode>{
	    private PuzzleNode puzzleNode = null;
	    private final CountDownLatch countDownLatch = new CountDownLatch(1);

        public boolean isSet(){
            return (countDownLatch.getCount() == 0);
        }

        public synchronized void setValue(PuzzleNode newPuzzleNode){
            if(!isSet()){
                puzzleNode = newPuzzleNode;
                countDownLatch.countDown();
            }
        }

        public PuzzleNode getPuzzleNode() throws InterruptedException{
            countDownLatch.await();
            synchronized (this){
                return puzzleNode;
            }
        }


    }
    public static void main (String[] args) throws Exception {
    	//int[] initialBoardConfig = new int[] {3,5,6,0,2,7,8,4,1}; 
    	//int[] initialBoardConfig = new int[] {5,0,6,4,7,3,2,8,1}; 
    	//int[] initialBoardConfig = new int[] {2,3,8,4,6,0,1,5,7}; 
       	int[] initialBoardConfig = new int[] {2,1,5,3,6,0,7,8,4};
        ExecutorService exec = new ScheduledThreadPoolExecutor(100);
        ThreadSafety<PuzzleNode> answer = new ThreadSafety<PuzzleNode>();
        AtomicInteger taskCount = new AtomicInteger(0);
        ConcurrentMap<String, Boolean> seen = new ConcurrentHashMap<String, Boolean>();

        System.out.println("Executing");

       	BFSSearch(exec, answer, taskCount, seen, new PuzzleNode(initialBoardConfig, null));

        System.out.println("Executed ");
        if (answer.getPuzzleNode() == null) {
    		System.out.println ("No solution");
    	}
    	else {
    		System.out.println ("Solution Found");
    		for (int[] i: answer.getPuzzleNode().getTrace()) {
        		System.out.println (toString(i));    			
    		}
    	}
    }
	
    public static void BFSSearch(final ExecutorService exec, final ThreadSafety<PuzzleNode> answer, final AtomicInteger taskCount,
                                 final ConcurrentMap<String, Boolean> seen, final PuzzleNode init) {
	    taskCount.incrementAndGet();
	    exec.execute(new Runnable() {
            @Override
            public void run() {

                if(answer.isSet()|| seen.putIfAbsent(ConcurrentSlidingGame.toString(init.config), true) != null){
                    if(taskCount.decrementAndGet() == 0){
                        answer.setValue(null);
                    }
                    return;
                }

                if(isGoal(init.config)){

                    taskCount.decrementAndGet();
                    answer.setValue(init);
                    exec.shutdown();
                }

                else{

                    for(int[] next:nextPositions(init.config)){
                        BFSSearch(exec, answer, taskCount, seen, new PuzzleNode(next, init));
                    }
                }
            }
        });


    }
	
    private static boolean isGoal (int[] boardConfig) {
    	return boardConfig[0] == 1 && boardConfig[1] == 2 && boardConfig[2] == 3 && boardConfig[3] == 4 &&
    			boardConfig[4] == 5 && boardConfig[5] == 6 && boardConfig[6] == 7 && boardConfig[7] == 8 && boardConfig[8] == 0;
    }

    private static List<int[]> nextPositions (int[] boardConfig) {
    	int emptySlot = -1;
    	
    	for (int i = 0; i < boardConfig.length; i++) {
    		if (boardConfig[i] == 0) {
    			emptySlot = i;
    			break;
    		}
    	}
    	
    	List<int[]> toReturn = new ArrayList<int[]>();
    	
    	//the empty slot goes right
    	if (emptySlot != 2 && emptySlot != 5 && emptySlot != 8) {
    		int[] newConfig = boardConfig.clone(); 
    		newConfig[emptySlot]= newConfig[emptySlot+1];
    		newConfig[emptySlot+1]=0;
    		toReturn.add(newConfig);
    	}
    	//the empty slot goes left    	
    	if (emptySlot != 0 && emptySlot !=3 && emptySlot != 6) {
    		int[] newConfig = boardConfig.clone();     		
    		newConfig[emptySlot]=newConfig[emptySlot-1];
    		newConfig[emptySlot-1]=0;
    		toReturn.add(newConfig);
    	}
    	//the empty slot goes down   
    	if (emptySlot != 6 && emptySlot != 7 && emptySlot != 8) {
    		int[] newConfig = boardConfig.clone();     		    		
    		newConfig[emptySlot]=newConfig[emptySlot+3];
    		newConfig[emptySlot+3]=0;
    		toReturn.add(newConfig);
    	}
    	//the empty slot goes up 
    	if (emptySlot != 0 && emptySlot != 1 && emptySlot != 2) {
    		int[] newConfig = boardConfig.clone();     		    		
    		newConfig[emptySlot] = newConfig[emptySlot-3];
    		newConfig[emptySlot-3] = 0;
    		toReturn.add(newConfig);
    	}
    	
    	return toReturn;
    }
    
    private static String toString(int[] config) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < config.length; i++) {
    		sb.append(config[i]);
    	}
    	
    	return sb.toString();
    }
}

class PuzzleNode {
	final int[] config;
	final PuzzleNode prev;
	
	PuzzleNode(int[] config, PuzzleNode prev) {
		this.config = config;
		this.prev = prev;
	}
	
	List<int[]> getTrace() {
		List<int[]> solution = new LinkedList<int[]> ();
		for (PuzzleNode n = this; n.prev != null; n = n.prev) {
			solution.add(0, n.config);
		}
		
		return solution;
	}
}