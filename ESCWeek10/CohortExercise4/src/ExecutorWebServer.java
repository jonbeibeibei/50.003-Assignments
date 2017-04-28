import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
public class ExecutorWebServer {
    private static final int numOfCPUs = Runtime.getRuntime().availableProcessors();
    private static final double targetUtil = 0.8;
    private static final double waitToCompRatio = 0.05;
	private static final int NTHREADS = (int) Math.round(numOfCPUs*targetUtil*(1+waitToCompRatio));
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
    public static void main(String[] args) throws Exception {

        System.out.println("number of CPUs is: " + numOfCPUs);
        System.out.println("number of threads is: " + NTHREADS);

		ServerSocket socket = new ServerSocket(4321, 1000);

		while (true) {

            final Socket connection = socket.accept();
			Runnable task = new Runnable () {
				public void run() {
//                    System.out.println("handling request");
                    handleRequest(connection);
				}
			};
			
			exec.execute(task);
		}
    }

	protected static void handleRequest(Socket connection) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
			BigInteger input = new BigInteger(in.readLine());
			BigInteger ans = factor(input);

			out.println(ans);
			out.flush();
			in.close();
			out.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static BigInteger factor(BigInteger n) {
		BigInteger i = new BigInteger("2");
		BigInteger zero = new BigInteger("0");

		while (i.compareTo(n) < 0) {
			if (n.remainder(i).compareTo(zero) == 0) {
				return i;
			}

			i = i.add(new BigInteger("1"));
		}

		assert(false);
		return null;
	}

}