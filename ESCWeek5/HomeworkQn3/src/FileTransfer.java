import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by jonathanbeiqiyang on 24/2/17.
 */
public class FileTransfer {

    public static final int portNumber = 4321;
    static String fileName = "/Users/jonathanbeiqiyang/IdeaProjects/ESC Week 5/HomeworkQn3/src/output";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(portNumber);
            serverSocket.setSoTimeout(20000);
            System.out.println("20 seconds to login clients and input input filepaths to shell ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Socket> clients = new ArrayList<>();
        BufferedReader in = null;
        PrintWriter out = null;
        PrintWriter file = null;
        int counter = 0;

        while(true) {

            try {
                System.out.println("... expecting connections ...");
                Socket client = serverSocket.accept();
                clients.add(client);

            } catch (SocketTimeoutException e) {
                System.out.println("file client login timed out, proceeding...");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




            for(int i = 0; i< clients.size(); i++) {

                in = new BufferedReader(new InputStreamReader(clients.get(i).getInputStream()));
                out = new PrintWriter(clients.get(i).getOutputStream(), true);
                file = new PrintWriter(new FileWriter(new File(fileName + counter + ".txt")), true);

                counter++;

                String output = null;
                while ((output = in.readLine()) != null) {
                    out.print("Message Received");
                    System.out.println("Received " + output);
                    file.println(output);
                }

                System.out.println("done");


                file.close();
                clients.get(i).close();
                in.close();
                out.close();

            }



        }




}

