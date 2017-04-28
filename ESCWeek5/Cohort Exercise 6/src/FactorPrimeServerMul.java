
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathanbeiqiyang on 23/2/17.
 */
public class FactorPrimeServerMul {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int portNumber = 4321;

        try{
            serverSocket = new ServerSocket(portNumber);
            serverSocket.setSoTimeout(10000);
        }
        catch (IOException e){
            e.printStackTrace();
        }


        List<Socket> clients = new ArrayList<Socket>();
        List<BufferedReader> ins = new ArrayList<BufferedReader>();




        while(true){
            try{
                System.out.println("... expecting connections ...");
                Socket client = serverSocket.accept();
                clients.add(client);
                ins.add(new BufferedReader(new InputStreamReader(client.getInputStream())));

            }
            catch (SocketTimeoutException timeOut){
                System.out.println("Server Login Timed Out, Starting computation now...");
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

        int numberOfClients = clients.size();  //Given 4 computers, 3 act as clients, 1 as server

        BigInteger number = new BigInteger("4294967297");
        BigInteger step = number.divide(BigInteger.valueOf(numberOfClients));



        for (int i = 0; i < numberOfClients; i++) {

            PrintWriter out = new PrintWriter(clients.get(i).getOutputStream(), true);

            BigInteger begin = step.multiply(BigInteger.valueOf(i));
            BigInteger end = step.multiply(BigInteger.valueOf(i + 1));

            out.println(number);
            if (i == 0) {
                out.println(2);
            }

            else{
                out.println(begin);
            }

            if(i == numberOfClients-1){
                out.println(number);
            }
            else {
                out.println(end);
            }


        }


        System.out.println("Computing Prime factors... factors will be printed as soon as they are ready...");

        String outputs = "";

        for (int i = 0; i < numberOfClients; i++) {

            String line;

            while ((line = ins.get(i).readLine()) != null) {
                System.out.println(line);

            }

        }


    }

}

