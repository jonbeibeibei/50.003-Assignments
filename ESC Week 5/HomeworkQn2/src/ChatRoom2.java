
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathanbeiqiyang on 22/2/17.
 */
public class ChatRoom2 {
    private static final int PortNumber = 4321;

    public static void main(String[] args) throws InterruptedException {
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(PortNumber);
            serverSocket.setSoTimeout(10000);

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Socket> clients = new ArrayList<Socket>();
        List<BufferedReader> in = new ArrayList<BufferedReader>();

        while(true){
            try{
                System.out.println("... expecting connections ...");
                Socket client = serverSocket.accept();
                clients.add(client);
                in.add(new BufferedReader(new InputStreamReader(client.getInputStream())));

            }
            catch (SocketTimeoutException timeOut){
                System.out.println("Server Login Timed Out, Starting chat now...");
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

//        Thread[] threads = new Thread[clients.size()];
//        for(int i = 0; i < clients.size(); i++){
//            threads[i] = (new Thread(new ServerRunnable(clients.get(i))));
//        }


//        while(true) {

            for (int i = 0; i < clients.size(); i++) {
                Thread thread = new Thread(new ServerRunnable(clients.get(i)));

                System.out.println("User " + i + "'s turn to speak");

                System.out.println(thread.toString());
                thread.start();

                try {
                    while (true) {
                        String userInput = in.get(i).readLine();
                        if (userInput != null) {
                            System.out.println(userInput);
                        }

                    }

                } catch (SocketException socketEx) {
                    System.out.println("Client " + i + " is idle, next user...");
                    thread.join();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

//        }

    }



    public static class ServerRunnable implements Runnable{

        private Socket client;

        public void run() {
            long start = System.currentTimeMillis();
            long current = start;
            while(current - start < 5000){
                current = System.currentTimeMillis();
            }
//            System.out.println("timeout");

            try {
                System.out.println("closed");
                client.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

        public ServerRunnable(Socket client){
            this.client = client;
        }
    }
}
