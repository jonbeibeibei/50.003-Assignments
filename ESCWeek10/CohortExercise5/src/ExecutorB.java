/**
 * Created by jonathanbeiqiyang on 1/4/17.
 */
/**
 * Created by jonathanbeiqiyang on 1/4/17.
 */
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.*;

public class ExecutorB {

    private static final BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(10);
    private static final ExecutorService exec = new ThreadPoolExecutor(100,100,5,TimeUnit.SECONDS, taskQueue);

    public static void main(String[] args) throws Exception {

        ServerSocket socket = new ServerSocket(4321);
        int connectionCounter = 0;

        while (true) {
            try {

                final Socket connection = socket.accept();
                connectionCounter++;
                Runnable task = new Runnable() {
                    public void run() {
//                    System.out.println("handling request");
                        handleRequest(connection);
                    }
                };

                exec.execute(task);
                System.out.println("Count = " + connectionCounter);

            }catch (RejectedExecutionException e){
                System.out.println("LOG: task submission is rejected.");
                break;
            }
        }
    }


    protected static void handleRequest(Socket connection) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);

            String input = in.readLine();


            BigInteger number = new BigInteger(in.readLine());
            BigInteger result = factor(number);
            //System.out.println("sending results: " + String.valueOf(result));

            out.println(result);
            out.flush();
            in.close();
            out.close();
            connection.close();

        }
        catch (Exception e) {
            System.out.println("Something went wrong with the connection");
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