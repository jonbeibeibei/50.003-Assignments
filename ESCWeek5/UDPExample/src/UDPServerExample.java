import java.net.*;

public class UDPServerExample {
	public static void main(String args[]) throws Exception       {          
		DatagramSocket serverSocket = new DatagramSocket(9876);             
		byte[] receiveData = new byte[1024];             
		byte[] sendData = new byte[1024];             
		while(true)                {                   
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("Expecting Message Any Minute Now");                   
			serverSocket.receive(receivePacket);
			byte[] data = new byte[receivePacket.getLength()];
			System.arraycopy(receivePacket.getData(),receivePacket.getOffset(),data,0,receivePacket.getLength());
			String sentence = new String(data);
			System.out.println("RECEIVED: " + sentence);                   
			InetAddress IPAddress = receivePacket.getAddress();                   
			int port = receivePacket.getPort();                   
			String capitalizedSentence = sentence.toUpperCase();                   
			sendData = capitalizedSentence.getBytes();                   
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);                   
			serverSocket.send(sendPacket);             
		}       
	}
}
