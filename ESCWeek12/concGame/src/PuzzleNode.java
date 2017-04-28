import java.util.LinkedList;
import java.util.List;

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