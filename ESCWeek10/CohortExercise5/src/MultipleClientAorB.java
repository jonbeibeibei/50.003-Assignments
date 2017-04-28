/**
 * Created by jonathanbeiqiyang on 1/4/17.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

public class MultipleClientAorB {
    public static void main(String[] args) throws Exception {
        int numberOfClients = 1000; //vary this number here
        long startTime = System.currentTimeMillis();
//    	BigInteger n = new BigInteger("4294967297");
        BigInteger n = new BigInteger("239839672845043");
        Thread[] clients = new Thread[numberOfClients];

        for (int i = 0; i < numberOfClients; i++) {
            clients[i] = new Thread(new Client(n));
            clients[i].start();
        }

        for (int i = 0; i < numberOfClients; i++) {
            clients[i].join();
        }
        System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
    }
}

class Client implements Runnable {
    private final BigInteger n;

    public Client (BigInteger n) {
        this.n = n;
    }

    public void run() {
        String hostName = "localhost";
        int portNumber = 4321;

        try {
            long startTime = System.currentTimeMillis();
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while((userInput = stdIn.readLine()) != null){
                out.println(userInput);
            }
            out.println(n.toString());
            out.flush();
            in.readLine();
            System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e) {
            System.out.println("Server got problem");
        }
    }
}