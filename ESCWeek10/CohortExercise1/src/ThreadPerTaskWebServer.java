/**
 * Created by jonathanbeiqiyang on 27/3/17.
 */
import com.sun.javafx.tk.Toolkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {
    public static void main (String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(4321, 1000);

        long startTime = 0;
        while (true) {
            Socket connection = socket.accept();
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            new TaskThread(connection).start();
        }
    }


}

class TaskThread extends Thread{
    private final Socket connection;

    public TaskThread(Socket connection){
        this.connection = connection;
    }

    public void run(){
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
        }
        catch (Exception e){
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

