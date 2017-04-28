import javax.sound.sampled.Port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jonathanbeiqiyang on 22/2/17.
 */
public class ChatRoom {

    private static final int PortNumber = 4321;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket client = null;

        try{
            serverSocket = new ServerSocket(PortNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try{
//                System.out.println("... expecting connections ...");
                client = serverSocket.accept();

            } catch (IOException e) {
                e.printStackTrace();
            }
            (new Thread(new ServerRunnable(client))).start();
        }
    }
    public static class ServerRunnable implements Runnable{

        private Socket client = null;

        public void run() {

            BufferedReader in = null;

            try{
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("New user has entered the session");
            } catch (IOException e) {

                e.printStackTrace();
                return;
            }

            String inputLine;

            try{
                while(true){
                    inputLine = in.readLine();
                    if(inputLine == null){
                        System.out.println("user has left");
                        break;
                    }
                    else{
                        System.out.println(inputLine);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public ServerRunnable(Socket client){
            this.client = client;
        }
    }
}
