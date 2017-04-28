import java.util.HashSet;
import java.util.Set;

public class DLExample {


	
}

/**
 * Deadlock explanation:
 * If a thread is executing taxi.setLocation() on one taxi object,
 * and another thread is executing dispatcher.getImage() on the dispatcher
 * object that is a member of the taxi object, the first taxi object will
 * get the lock. The second thread may also run and get the lock on the dispatcher
 * object and the two threads will be in deadlock.
 * This is because they will be waiting on each other's locks and neither
 * will give up lock without the other giving it up first, resulting in deadlock
 *
 *
 *
 */

class Taxi {
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

	public synchronized Point getLocation() {
        return location;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination))
            dispatcher.notifyAvailable(this);
    }

    public synchronized Point getDestination() {
        return destination;
    }
}

class Dispatcher {
    private final Set<Taxi> taxis;
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<Taxi>();
        availableTaxis = new HashSet<Taxi>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image();
        for (Taxi t : taxis)
            image.drawMarker(t.getLocation());
        return image;
    }
}

class Image {
    public void drawMarker(Point p) {
    }
}

class Point {
	
}

