package bloque5tarea1.udp;

import java.net.*;

import bloque5tarea1.operation.Operator;


public class ServerUDP extends Thread {
 
    private static int PORT = 5001;
	
	private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
 
    public ServerUDP() throws SocketException {
        socket = new DatagramSocket(PORT);
    }
    
    public void run() {
        running = true;
        System.out.println("Starting server...");
 
        while (running) {
        	try {
	            DatagramPacket packet = new DatagramPacket(buf, buf.length);
	            socket.receive(packet);
	             
	            InetAddress address = packet.getAddress();
	            int port = packet.getPort();
	            String received = new String(packet.getData(), 0, packet.getLength());
	            System.out.println("Received from client: " + received);
	            String result = Operator.operationString(received);
	            System.out.println("Responding: " + result);
	            byte[] bufResponse = result.getBytes();
	            packet = new DatagramPacket(bufResponse, bufResponse.length, address, port);
	            
	             
	            if (received.equals("end")) {
	                running = false;
	                continue;
	            }
	            socket.send(packet);
	            
        	} catch (Exception e) {
        		
        	}
        }
        socket.close();
    }
    
    public static void main(String args[]) throws SocketException {
    	ServerUDP server = new ServerUDP();
    	server.start();
    }
}
