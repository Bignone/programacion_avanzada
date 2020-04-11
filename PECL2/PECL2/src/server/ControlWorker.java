/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author cesar
 */
public class ControlWorker extends Thread { // Clase worker para ejecutar los comandos de la conexion

    Socket conexion;
    DataOutputStream salida;
    DataInputStream entrada;
    Exposicion expo;
    HashMap<String, Integer> commands = new HashMap<String, Integer>();
    private final LinkedBlockingQueue<Socket> queue;

    public ControlWorker(Exposicion expo, LinkedBlockingQueue<Socket> queue, HashMap<String, Integer> commands) throws IOException {
        this.expo = expo;
        this.queue = queue;
        this.commands = commands;
        System.out.println("Worker Arrancado....");
    }

    private int getCommand(String msg) {
        int result = -1;
        if (commands.containsKey(msg)) {
            result = commands.get(msg);
        }
        return result;
    }

    private String executeOrder66(int command) {
        String result = "Invalid command";
        switch (command) {
            case 0:
                expo.detener();
                result = "Executed command: detener";
                break;
            case 1:
                expo.renuadar();
                result = "Executed command: reanudar";
                break;
        }
        return result;
    }

    @Override
    public void run() {
    	boolean receiveing = false;
    	String mensaje;
    	int codeCommand;
    	String result = "";
    	
    	while (true) {
    		receiveing = false;
    		try {
				conexion = queue.take(); // coger conexion de la cola
				entrada = new DataInputStream(conexion.getInputStream());
				salida = new DataOutputStream(conexion.getOutputStream());
				receiveing = true;
				while (receiveing) { // empezar a recibir comandos
	    			mensaje = entrada.readUTF();
	    			System.out.println("Worker " + getName() + " - mensaje recibido: " + mensaje);
	    			codeCommand = getCommand(mensaje);
	    			if (codeCommand != 2) {
	                	result = executeOrder66(codeCommand);
		                salida.writeUTF(result);
					} else {
	                	salida.writeUTF("exiting");
	                	conexion.close();
	                	result = "connection closed";
	                	receiveing = false;
	                }
	    			System.out.println("Worker " + getName() + " - executed result: " + result);
	    			
				}
				
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
				try {
					conexion.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
    		
    	}
  
    }
    

}
