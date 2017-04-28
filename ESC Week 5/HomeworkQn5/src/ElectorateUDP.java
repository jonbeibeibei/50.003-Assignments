import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * Created by jonathanbeiqiyang on 25/2/17.
 */
public class ElectorateUDP {

    public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException {
        DatagramSocket votingSocket = new DatagramSocket();

        long currentTime = System.currentTimeMillis();
        Thread voterRunnableThread = null;
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            int port = votingSocket.getLocalPort();
            byte id_size = (byte) (1 + Long.SIZE / Byte.SIZE + ipAddress.getAddress().length + Integer.SIZE / Byte.SIZE);
//            if (id_size != 17) {
//                System.out.println("IP error");
//            }

            ByteBuffer buffer = ByteBuffer.allocate(id_size);
            buffer.put(id_size).putLong(currentTime).put(ipAddress.getAddress()).putInt(port);
            buffer.flip();

            InetAddress inetAddress = InetAddress.getByName("224.1.1.1");
            int multicastPort = 4321;
            MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.joinGroup(inetAddress);

            voterRunnableThread = new Thread(new VoterRunnable(buffer, multicastSocket));
            voterRunnableThread.start();

            byte[] sendData = new byte[id_size + 1];
            buffer.get(sendData, 0, id_size);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, multicastPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Wait for all 5 Electorates to come online...");
            System.out.println("Please key in your vote using A / B, uppercase only.");
            while (true) {
                String voteInput = in.readLine().trim();
                if (voteInput.equals("A")) {
                    sendData[id_size] = 0;
                    break;
                } else if (voteInput.equals("B")) {
                    sendData[id_size] = 1;
                    break;
                } else {
                    System.out.println("Invalid input! Try again!");
                }
            }
            votingSocket.send(sendPacket);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        voterRunnableThread.join();


    }


}

 class VoterRunnable implements Runnable{

    ByteBuffer buffer;
    MulticastSocket multicastSocket;

    public VoterRunnable(ByteBuffer byteBuffer, MulticastSocket multicastSocket){
        this.buffer = byteBuffer;
        this.multicastSocket = multicastSocket;
    }

     @Override
         public void run() {
            byte[] receiveData = new byte[buffer.capacity()+1];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            HashMap<ByteBuffer, Integer> voteTally = new HashMap<ByteBuffer, Integer>();


            while(voteTally.size() < 5){
                try{
                    this.multicastSocket.receive(receivePacket);

                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                byte[] received = receivePacket.getData();
                ByteBuffer voter = ByteBuffer.allocate(buffer.capacity());
                voter.put(received,0,buffer.capacity());
                voter.flip();
                if(voteTally.containsKey(voter)){ // 1 Vote per Electorate
                    continue;
                }
                voteTally.put(voter,Integer.valueOf(received[buffer.capacity()]));
            }

            int votesForA = 0;
            int votesForB = 0;

            for(Integer i: voteTally.values()){
                if(i==0){
                    votesForA++;
                }else{
                    votesForB++;
                }
            }

            if (votesForB < votesForA){
                System.out.println("A wins!");
            }
            else if(votesForA < votesForB){
                System.out.println("B wins!");
            }
            else{
                System.out.println("error");
            }
         }
 }
