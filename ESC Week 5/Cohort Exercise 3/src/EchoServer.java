import java.net.*;
import java.io.*;
 
public class EchoServer {
    public static void main(String[] args) throws Exception {
    	ServerSocket serverSocket = new ServerSocket(4321);

    	int numOfClients = 2;
    	Socket[] clients = new Socket[numOfClients];

    	for (int i=0; i<numOfClients; i++) {
            System.out.println("(... expecting connection ... )" + i );
            clients[i] = serverSocket.accept();
//            Socket clientSocket = serverSocket.accept();

        }
        System.out.println("(... connection established ...)");

    	while(true){
        for (int i=0; i<numOfClients; i++) {
            System.out.println("client " + i);
            PrintWriter out =
                    new PrintWriter(clients[i].getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clients[i].getInputStream()));
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));
            String inputLine;

            while (!((inputLine = in.readLine()).equals("Bye"))) {
                System.out.println("Wife says:" + inputLine);
                out.println(stdIn.readLine());
                out.flush();
                System.out.println("while ran");
                break;

            }


            if (inputLine.equals("Bye")){
                out.println(inputLine);
                serverSocket.close();
                for (int j=0; i<numOfClients; i++) {
                    clients[j].close();

                }
                out.close();
                in.close();
                break;
            }

            continue;
        }
//            out.print(inputLine);

//            out.println(inputLine);
//            serverSocket.close();
//            clients[i].close();
//            out.close();
//            in.close();
        }
    }
}