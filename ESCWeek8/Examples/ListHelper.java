
import java.util.ArrayList;
import java.util.Collections;

public class ListHelper<E> {
	public java.util.List<E> list = Collections.synchronizedList(new ArrayList<E>());
		
	//thread-safe - if you put synchronized in the method, then not it is not, but in this case it is synchronized.
	public boolean putIfAbsent(E x) { // if put synchronized here, you are synchronizing the listhelper object, not the list.
		synchronized (list) {
			boolean absent = !list.contains(x);
			if (absent) {
				list.add(x);
			}
			
			return absent;			
		}
	}
}