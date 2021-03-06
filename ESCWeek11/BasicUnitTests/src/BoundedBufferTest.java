
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;

import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.*;

public class BoundedBufferTest {	
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;
	
	@Test
	public void testIsEmptyWhenConstructed () {
		BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}
	
	@Test
	public void testIsFullAfterPuts () throws InterruptedException { //Test for concurrency
		final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);
		
		Runnable task = new Runnable () {
			public void run() {
				try {
					bb.put((new Random()).nextInt());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		};

		Thread[] threads = new Thread[10];
		
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread (task);
			threads[i].start();
		}

		for (int i = 0; i < 10; i++) {
			threads[i].join();
		}

		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}
	
	@Test
	public void testTakeBlocksWhenEmpty () {
		final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					assertTrue(false);
				} catch (InterruptedException success) {} //if interrupted, the exception is caught here
			}
		};
		
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(taker.isAlive()); //the taker should not be alive for some time
		} catch (Exception unexpected) {
			assertTrue(false);
		}
	}

    @Test
    public void testEmptyWhenAllTaken() throws InterruptedException {
	    final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<>(10);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    bb.put(1);
                    bb.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread (task);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        assertTrue(bb.isEmpty());
    }




	@Test
    public void testPutBlocksWhenBufferFullMultipleThreads() throws InterruptedException {
	    final BoundedBufferWithSpec<Integer> bb = new BoundedBufferWithSpec<>(10);

	    Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    bb.put(1);
                    fail("Fail");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread (task);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        assertTrue(bb.isFull());

    }
}
