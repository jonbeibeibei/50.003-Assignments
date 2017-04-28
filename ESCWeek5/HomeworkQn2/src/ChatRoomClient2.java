import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jonathanbeiqiyang on 22/2/17.
 */
public class ChatRoomClient2 {
    private static final String HostName = "localhost";
    private static final int PortNumber = 4321;

    public static void main(String[] args) {
        Socket client = null;
        PrintWriter out = null;
        BufferedReader stdIn = null;

        try{
            client = new Socket(HostName, PortNumber);
            out = new PrintWriter(client.getOutputStream(),true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Write your message, press enter on an empty line to exit");


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userInput;
        try{
            while(!(userInput = stdIn.readLine()).equals("")){
                out.println(userInput);
                out.flush();

            }
            System.out.println("You have successfully left the session");
            stdIn.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
