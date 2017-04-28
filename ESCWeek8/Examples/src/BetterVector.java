import java.util.Vector; // Extending the class for Thread Safety

public class BetterVector<E> extends Vector<E> {
	public synchronized boolean putIfAbsent(E x) {
		boolean absent = !contains(x);
		
		if (absent) {
			add(x);
		}
		
		return absent;
	}
}