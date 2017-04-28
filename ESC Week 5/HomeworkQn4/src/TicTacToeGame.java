import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by jonathanbeiqiyang on 11/2/17.
 */
public class TicTacToeGame {

    private static final int portNumber = 4321;

    public void GameInstance() throws InputMismatchException {

        TicTacToe game1 = new TicTacToe();
        game1.displayPlayField();
        Scanner gameInput = new Scanner(System.in);
        int row = 0;
        int column = 0;

        while (game1.checkWin() != true) {
            try {
                row = gameInput.nextInt();
                column = gameInput.nextInt();


                if (game1.setIcon(row,column)) {

                    game1.displayPlayField();

                    game1.changePlayerTurn();
                    System.out.println("next player!");
                    System.out.println("input your move in the format: row column, using integers separated by a space only");
                    System.out.println("Use values only from 0 to 2, 0 being the first and 2 the last");
                }
                else{
                    System.out.println("invalid move! try again");
                }

            } catch (InputMismatchException e) {
                System.out.println("wrong input! Try again");
                gameInput.nextLine();
            }
            continue;

        }

        System.out.println("Game Over!");

    }



    public static void main(String[] args) throws IOException {

        System.out.println("Player one goes first! You are o.");
        System.out.println("input your move in the format: row column, using integers separated by a space only");
        System.out.println("Use values only from 0 to 2, 0 being the first and 2 the last");

        ServerSocket serverSocket = null;
        Socket playerOsocket = null;
        Socket playerXsocket = null;

        PrintWriter outO = null;
        PrintWriter outX = null;
        BufferedReader inX = null;
        BufferedReader inO = null;
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Accepting player o");
            playerOsocket = serverSocket.accept();
            System.out.println("Accepting player x");
            playerXsocket = serverSocket.accept();

            inO = new BufferedReader(new InputStreamReader(playerOsocket.getInputStream()));
            inX = new BufferedReader(new InputStreamReader(playerXsocket.getInputStream()));
            outO = new PrintWriter(playerOsocket.getOutputStream(), true);
            outX = new PrintWriter(playerXsocket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader[] ins = {inO,inX};


        TicTacToeGame gameStart = new TicTacToeGame();
        TicTacToe game1 = new TicTacToe();
        game1.displayPlayField();
        Scanner gameInput = new Scanner(System.in);

        int row = 0;
        int column = 0;

        while (game1.checkWin() != true) {
            for(int i = 0; i<2; i++) {
                try {
//                    System.out.println("waiting for input");
                    row = Integer.parseInt(ins[i].readLine());
                    column = Integer.parseInt(ins[i].readLine());
                    System.out.println(row);
//                row = gameInput.nextInt();
//                column = gameInput.nextInt();


                    if (game1.setIcon(row, column)) {

                        game1.displayPlayField();

                        game1.changePlayerTurn();
                        System.out.println("next player!");
//                        System.out.println("input your move in the format: row column, using integers separated by a space only");
//                        System.out.println("Use values only from 0 to 2, 0 being the first and 2 the last");
                    } else {
                        System.out.println("invalid move! try again");
                        i-=1;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("wrong input! Try again");
                    gameInput.nextLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            continue;

        }

        System.out.println("Game Over!");
        serverSocket.close();
        playerOsocket.close();
        playerXsocket.close();

    }

}

