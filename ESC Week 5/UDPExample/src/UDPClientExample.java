import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

class UDPClientExample {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		System.out.print(">: ");                   
		String sentence = inFromUser.readLine();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
        byte[] data = new byte[receivePacket.getLength()];
        System.arraycopy(receivePacket.getData(),receivePacket.getOffset(),data,0,receivePacket.getLength());
        String modifiedsentence = new String(data);
//		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedsentence);
		clientSocket.close();
	}
}