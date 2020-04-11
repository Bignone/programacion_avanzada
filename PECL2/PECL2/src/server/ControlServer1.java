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

import client.ControlModule;

/**
 *
 * @author cesar
 */
public class ControlServer1 extends Thread { // Clase servidor

    ServerSocket servidor;
    Socket conexion;
    DataOutputStream salida;
    DataInputStream entrada;
    Exposicion expo;
    private static int CONNECTION_LIMIT = 10;
    HashMap<String, Integer> commands = new HashMap<String, Integer>();
    private int activeConections = 0;
    private static int PORT = 5000;

    public ControlServer1(Exposicion expo) throws IOException {
        this.expo = expo;
        commands.put("detener", 0);
        commands.put("reanudar", 1);
        commands.put("close", 2);
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
        DataOutputStream salida;
        DataInputStream entrada;
        int num = 0;
        try
        {
            servidor = new ServerSocket(5000); // Creamos un ServerSocket en el puerto 5000
            System.out.println("Servidor Arrancado....");
            while (true)
            {
                conexion = servidor.accept();     // Esperamos una conexión
                num++;
                System.out.println("Conexión nº "+num+" desde: "+conexion.getInetAddress().getHostName());
                entrada = new DataInputStream(conexion.getInputStream());  // Abrimos los canales de E/S
                salida  = new DataOutputStream(conexion.getOutputStream());
                while (true) {
	                String mensaje = entrada.readUTF();    //Leemos el mensaje del cliente
	                int codeCommand = getCommand(mensaje);
	                if (codeCommand != 2) {
	                	result = executeOrder66(codeCommand);
	                	System.out.println("Conexión nº "+num+". Mensaje recibido: "+mensaje);
		                salida.writeUTF(result);  // Le respondemos
	                } else {
	                	salida.writeUTF("exiting");  // Le respondemos
	                	conexion.close();
	                	break;
	                }
	                
                }
                                        // Y cerramos la conexión
            }
        } catch (IOException e) {}
    }
    
    public static void main(String args[]) throws UnknownHostException, IOException, CloneNotSupportedException {

        ControlServer server = new ControlServer(null);
        server.start();

    }
}
