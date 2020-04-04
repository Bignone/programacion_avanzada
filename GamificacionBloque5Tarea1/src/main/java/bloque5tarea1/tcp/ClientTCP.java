package bloque5tarea1.tcp;

import java.io.*;
import java.net.*;


public class ClientTCP {
	
	private static int SERVER_PORT = 5000;
	
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public ClientTCP() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getLocalHost(), SERVER_PORT); 
		dataInputStream = new DataInputStream(socket.getInputStream()); 
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	public String send(String msg) throws IOException {
		System.out.println("Sending to server: " + msg);
		msg = "2,3";
		dataOutputStream.writeUTF(msg); 
		String received = dataInputStream.readUTF(); 
		
		System.out.println("Response received from server: " + received);		
		return received;
	}
	
	public void close() throws IOException {
        socket.close();
    }
	
	public static void main(String args[]) {
    	try {
    		ClientTCP client = new ClientTCP();
        	client.send("2,3");
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
