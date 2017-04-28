import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingCounterFixed {
    private AtomicStampedReference<AtomicInteger> value = new AtomicStampedReference<>(new AtomicInteger(),0);

    public int getValue() {
        return value.getReference().get();
    }

    public int increment() {
        AtomicInteger oldRef;
        int oldStamp;
        AtomicInteger newRef;
        do{
          oldRef = value.getReference();
          oldStamp = value.getStamp();
          newRef = new AtomicInteger(oldRef.get());
        } while (!value.compareAndSet(oldRef, newRef, oldStamp, oldStamp + 1));
        return oldRef.get() + 1;
    }
}
