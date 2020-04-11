/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import client.ControlModule;

/**
 *
 * @author cesar
 */
public class ControlServerRun extends Thread { // Clase servidor

    ServerSocket servidor;
    Socket conexion;
    DataOutputStream salida;
    DataInputStream entrada;
    Exposicion expo;
    private static int CONNECTION_LIMIT = 10;
    HashMap<String, Integer> commands = new HashMap<String, Integer>();
    private int activeConections = 0;
    private static int PORT = 5000;
    

    public ControlServerRun(Exposicion expo) throws IOException {
        this.expo = expo;
        commands.put("detener", 0);
        commands.put("reanudar", 1);
        commands.put("close", 2);
        servidor = new ServerSocket(PORT); // Creamos un ServerSocket
        System.out.println("Servidor Arrancado....");
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
    	
        String result = "";

        try {
            while (true) {
            	conexion = servidor.accept();     // Esperamos una conexión
//                if (activeConections < CONNECTION_LIMIT) {
                    activeConections++;
                    System.out.println("Conexión nº " + activeConections + " desde: " + conexion.getInetAddress().getHostName());

                    entrada = new DataInputStream(conexion.getInputStream());  // Abrimos los canales de E/S
                    salida = new DataOutputStream(conexion.getOutputStream());
                    while (true) {
	                    String mensaje = entrada.readUTF();    //Leemos el mensaje del cliente
	                    System.out.println("Conexión nº " + activeConections + ". Mensaje recibido: " + mensaje);
	                    int codeCommand = getCommand(mensaje);
	                    
	                    if (codeCommand != 2) {
		                	result = executeOrder66(codeCommand);
			                salida.writeUTF(result);  // Le respondemos
		                } else {
		                	salida.writeUTF("exiting");  // Le respondemos
		                	conexion.close();
		                	break;
		                }                       // Y cerramos la conexión
                    }
//                }
//                else {
//                	conexion.close(); 
//                }
            }
        } catch (IOException e) {
        }
    }
    
    public static void main(String args[]) throws UnknownHostException, IOException, CloneNotSupportedException {

        ControlServerRun server = new ControlServerRun(null);
        server.start();

    }
}
