package bloque5tarea2.udp;

import java.io.*;
import java.net.*;

public class ClientUDP {
	
	private static int SERVER_PORT = 5001;
	
    private DatagramSocket socket;
    private InetAddress address;
 
    private byte[] buf;
 
    public ClientUDP() throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }
 
    public String send(String msg) throws IOException {
    	System.out.println("Sending to server: " + msg);
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, SERVER_PORT);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Response received from server: " + received);
        return received;
    }
 
    public void close() {
        socket.close();
    }
    
    public static void main(String args[]) throws IOException {
    	ClientUDP client = new ClientUDP();
    	client.send("1992/12/22");
    	client.close();
    }
}
