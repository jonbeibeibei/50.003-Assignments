import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by jonathanbeiqiyang on 24/2/17.
 */
public class FileTransferClient {

    public static final String host = "localhost";
    public static final int portNumber = 4321;

    public static void main(String[] args) {

        Socket client = null;
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader fileInput = null;

        Scanner scanner = new Scanner(System.in);

        String fileName = scanner.nextLine();

        try{
            client = new Socket(host,portNumber);
            client.setSoTimeout(1000);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(),true);
            fileInput = new BufferedReader(new FileReader(fileName));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            String input = null;
            while((input = fileInput.readLine()) != null){
                System.out.println(input);
                out.println(input);

                try{
                    in.readLine();
                } catch (SocketTimeoutException e) {
                    System.out.println("sending");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            out.close();
            in.close();
            fileInput.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
