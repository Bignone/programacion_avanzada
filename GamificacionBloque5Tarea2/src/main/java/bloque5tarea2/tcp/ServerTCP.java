package bloque5tarea2.tcp;

import bloque5tarea2.operation.Operator;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerTCP extends Thread {

	private static int PORT = 5000;

	private ServerSocket socket;
	private boolean running;
	
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	private Socket conection;
	

	public ServerTCP() throws IOException {
	        socket = new ServerSocket(PORT);
	    }
	
	public void run() {
		running = true;
		
		int num = 0;
		try {
			System.out.println("Starting server...");
			while (running) {
				conection = socket.accept(); // Esperamos una conexión
				num++;
				System.out.println("Connection " + num + " from: " + conection.getInetAddress().getHostName());

				dataInputStream = new DataInputStream(conection.getInputStream()); // Abrimos los canales de E/S
				dataOutputStream = new DataOutputStream(conection.getOutputStream());
				String received = dataInputStream.readUTF(); // Leemos el mensaje del cliente

				String result = Operator.operationString(received);

				System.out.println("Connection " + num + ". Received: " + received);
				dataOutputStream.writeUTF(result); // Le respondemos
				conection.close(); // Y cerramos la conexión
				
				if (received.equals("end")) {
	                running = false;
	                continue;
	            }
			}
			socket.close();
			
		} catch (IOException e) {
		}
	}
	
	public static void main(String args[]) throws IOException {
		
        try {
            ServerTCP server = new ServerTCP();
            server.start();
            server.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
