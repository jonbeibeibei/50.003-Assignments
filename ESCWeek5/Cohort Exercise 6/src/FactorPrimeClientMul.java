import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jonathanbeiqiyang on 23/2/17.
 */
public class FactorPrimeClientMul {

    public static void main(String[] args) throws IOException {

        String host = "localhost";
        int portNumber = 4321;
        Socket socket = new Socket(host,portNumber);

        System.out.println("Client Connected!");

        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        BigInteger number = new BigInteger(in.readLine());
//        System.out.println(number);
        BigInteger begin = new BigInteger(in.readLine());
//        System.out.println(begin);
        BigInteger end = new BigInteger(in.readLine());
//        System.out.println(end);

        ArrayList<BigInteger> primeFactors = new ArrayList<>();
        System.out.println("starting number is: " + begin);
        System.out.println("ending number is: "+ end);

        for(BigInteger i = begin; i.compareTo(end) == -1; i = i.add(BigInteger.ONE)){
            if(number.mod(i) == BigInteger.ZERO){
                if(!(primeFactors.contains(i))){
                    primeFactors.add(i);
                    primeFactors.add(number.divide(i));
                }
            }
        }
        for(BigInteger i: primeFactors){
//            System.out.println(i);
            out.println(i);
        }


        out.flush();



    }
}
