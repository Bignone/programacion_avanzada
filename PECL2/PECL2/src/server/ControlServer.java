/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author cesar
 */
public class ControlServer extends Thread { // Clase servidor

    ServerSocket servidor;
    Socket conexion;
    DataOutputStream salida;
    DataInputStream entrada;
    Exposicion expo;
    private static int CONNECTION_LIMIT = 10;
    private static int CONTROL_WORKERS = 10;
    HashMap<String, Integer> commands = new HashMap<String, Integer>();
    private static int PORT = 5000;
    private final LinkedBlockingQueue<Socket> queue = new LinkedBlockingQueue<>(CONNECTION_LIMIT);

    public ControlServer(Exposicion expo) throws IOException {
        this.expo = expo;
        initCommands();
        servidor = new ServerSocket(PORT); // Creamos un ServerSocket
        startWorkers();
        System.out.println("Servidor Arrancado....");
    }
    
    public void initCommands() { // iniciar los comandos del servidor
    	commands.put("detener", 0);
        commands.put("reanudar", 1);
        commands.put("close", 2);
    }
    
    public void startWorkers() throws IOException { // iniciar los workers para atender las conexiones
    	ControlWorker worker;
    	for (int i = 0; i < CONTROL_WORKERS; i++)
        {
        	worker = new ControlWorker(expo, queue, commands);
        	worker.start();
        }
    }

    public boolean spaceInQueue() { // comprobar si ha espacio en la cola para nuevas conexiones
    	return queue.size() < CONNECTION_LIMIT;
    }

    @Override
    public void run() {
        try {
            while (true) {
            	if (spaceInQueue()) {
	            	conexion = servidor.accept(); 
	            	if (!queue.offer(conexion)) {
	            		conexion.close();
	            	}
	            }
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    public static void main(String args[]) throws UnknownHostException, IOException, CloneNotSupportedException {

        ControlServer server = new ControlServer(null);
        server.start();

    }
}
