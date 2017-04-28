import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by jonathanbeiqiyang on 25/2/17.
 */
public class TicTacToePlayer {

    private static final int portNumber = 4321;
    private static final String hostname = "localhost";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Socket client = null;
        BufferedReader in = null;
        BufferedReader stdIn = null;
        PrintWriter out = null;

        System.out.println("Input your row choice then press enter... repeat for column choice. \n Use only values from 0-2");
        System.out.println("Game board moves will be reflected on the server...");
        try{
            client = new Socket(hostname,portNumber);

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(client.getOutputStream(),true);



            while (true) {
                String line = input.nextLine();
//                System.out.println(line);
                out.println(line);

            }




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static class InputRunnable implements Runnable{

        private BufferedReader stdIn;
        private PrintWriter out;

        public InputRunnable(BufferedReader stdIn, PrintWriter out){
            this.stdIn = stdIn;
            this.out = out;
        }

        @Override
        public void run() {
            try{
                out.println(stdIn.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
