
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jonathanbeiqiyang on 23/2/17.
 */
public class FactorPrimeServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4321);

        int numberOfClients = 3;  //Given 4 computers, 3 act as clients, 1 as server
        Socket[] sockets = new Socket[numberOfClients];

        BigInteger number = new BigInteger("1127451830576035879");
        BigInteger step = number.divide(BigInteger.valueOf(numberOfClients));
        BufferedReader[] ins = new BufferedReader[numberOfClients];

        for (int i = 0; i < numberOfClients; i++) {
            System.out.println("...expecting connection...");
            sockets[i] = serverSocket.accept();
            PrintWriter out = new PrintWriter(sockets[i].getOutputStream(), true);
            ins[i] = new BufferedReader(new InputStreamReader(sockets[i].getInputStream()));

            BigInteger begin = step.multiply(BigInteger.valueOf(i));
            BigInteger end = step.multiply(BigInteger.valueOf(i + 1));

            out.println(number);
            if (i == 0) {
                out.println(2);
            }

            if (i == 1) {
                out.println(begin);
            }
            if (i == numberOfClients - 1) {
                out.println(begin);
            }
            out.println(end);

//

        }

        outerloop:
//        while (true){
        System.out.println("Computing Prime factors... factors will be printed as soon as they are ready...");

        String outputs = "";

        for (int i = 0; i < numberOfClients; i++) {

            String line;

            while ((line = ins[i].readLine()) != null) {
                System.out.println(line);

            }

        }


    }

}

