import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GDesktopWithThreadPool {
	private final static int BOUND = 20;
	private final static int N_CONSUMERS = 4;
	private final static ExecutorService exec = new ScheduledThreadPoolExecutor(100);
	
	public static void startIndexing (File[] roots) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {return true;}
		};
		
		for (File root : roots) {
//			(new FileCrawler(queue, filter, root)).start();;
            exec.execute(new FileCrawler(queue, filter, root, exec));
		}
		
		for (int i = 0; i < N_CONSUMERS; i++) {
			(new Indexer(queue)).start();

		}
	}
}

class FileCrawler extends Thread {
	private final BlockingQueue<File> fileQueue; 
	private final FileFilter fileFilter; 	
	private final File root;
	private final ExecutorService executorService;
	
	FileCrawler (BlockingQueue<File> queue, FileFilter filter, File root, ExecutorService executorService) {
		this.fileQueue = queue;
		this.fileFilter = filter;
		this.root = root;
		this.executorService = executorService;
	}
	
	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	private void crawl(File root) throws InterruptedException {
		File[] entries = root.listFiles(fileFilter);
		
		if (entries != null) {
			for (File entry : entries) {
			    executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(entry.isDirectory())

                        {
                            try {
                                crawl(entry);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else

                        {
                            try {
                                fileQueue.put(entry);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
			}
		}
	}
}

class Indexer extends Thread {
	private final BlockingQueue<File> queue;
	
	public Indexer (BlockingQueue<File> queue) {
		this.queue = queue;
	}
	
	public void run() {
		try {
			while (true) {
				indexFile(queue.take());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void indexFile(File file) {
		// TODO Auto-generated method stub	
	}
}