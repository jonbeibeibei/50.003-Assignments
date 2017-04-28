import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class test1 extends Thread {
	TrackerFixed tracker;
	
	public test1 (TrackerFixed tra) {
		this.tracker = tra;
	}
	
	public void run () {
		TrackerFixed.MutablePoint loc = tracker.getLocation("somestring");
		loc.x = -1212000;
	}
}

//is this class thread-safe?
public class TrackerFixed {
	//@guarded by ???
	private final Map<String, MutablePoint> locations;
	
	//the reference locations, is it going to be an escape?
	public TrackerFixed(Map<String, MutablePoint> locations) {
		this.locations = copyData(locations);
	}
	
	//is this an escape?
	public synchronized Map<String, MutablePoint> getLocations () {
		return copyData(locations);
	}
	
	//is this an escape?
	public synchronized MutablePoint getLocation (String id) {
		MutablePoint loc = locations.get(id);
		return new MutablePoint(loc.x, loc.y);
	}
	
	public void setLocation (String id, int x, int y) {
		MutablePoint loc = locations.get(id);
		
		if (loc == null) {
			throw new IllegalArgumentException ("No such ID: " + id);			
		}
		
		loc.x = x;
		loc.y = y;
	}

	private Map<String, MutablePoint> copyData (Map<String, MutablePoint> map){
	    Map<String, MutablePoint> data = new ConcurrentHashMap<>();
	    for(String key: map.keySet()){
	        MutablePoint mutablePoint = new MutablePoint(map.get(key).x,map.get(key).y);
	        data.put(key, mutablePoint);
        }

        return data;
    }
	
	//this class is not thread-safe (why?) and keep it unmodified.
    class MutablePoint {
		public int x, y;

		public MutablePoint (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public MutablePoint (MutablePoint p) {
			this.x = p.x;
			this.y = p.y;
		}
	}
}
