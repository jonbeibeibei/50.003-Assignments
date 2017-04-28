import java.util.Vector;

public class FirstExampleSafe {


    // If two threads call the static methods and pass the same list object to them,
    // one thread might read the wrong list size and access a wrong index/invalid index.
    // Therefore it is necessary to synchronize them on the list object
    // This works as the Vector Object's synchronization policy is to synchronize
    // on the 'this' object, supporting client side locking


	public static Object getLast(Vector list) {
	    synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
	}

	public static synchronized void  deleteLast(Vector list) {
	    synchronized (list) {
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
        }
	}	
	
	public static boolean contains(Vector list, Object obj) {
        synchronized (list) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(obj)) {
                    return true;
                }
            }

            return false;
        }
    }
}
