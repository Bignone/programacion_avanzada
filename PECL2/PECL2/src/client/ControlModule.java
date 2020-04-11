/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.*;


public class ControlModule { // Clase cliente

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    InetAddress address;
    int port;

    private static String COMMAND_REANUDAR = "reanudar";
    private static String COMMAND_DETENER = "detener";
    private static String COMMAND_CLOSE = "close";

    public ControlModule(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() throws IOException { // crear la conexion con el servidor y los canales de datos
        socket = new Socket(this.address, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public String sendMessage(String msg) { // envia un mesaje y devuelve la respuesta del server
        String response = null;
        try {
            dataOutputStream.writeUTF(msg);
            response = dataInputStream.readUTF();
            System.out.println("Sended message: " + msg + " - server response: " + response);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return response;
    }

    public void close() throws IOException { // cierra la conexion
    	sendMessage(COMMAND_CLOSE); // manda el comando de cierre al servidor
        socket.close();
    }

    public String reanudar() { // metodo que manda el comando reanudar definido sin que el usuario sepa el string
        return sendMessage(COMMAND_REANUDAR);
    }

    public String detener() { // metodo que manda el comando detener definido sin que el usuario sepa el string
        return sendMessage(COMMAND_DETENER);
    }

    public static void main(String args[]) throws UnknownHostException, IOException, CloneNotSupportedException {

        ControlModule controlModule = new ControlModule(InetAddress.getLocalHost(), 5000); // crear el controlador
        controlModule.connect(); // se crea y abre una conexion y se queda abierta

        controlModule.reanudar();
        controlModule.detener();
//
//        controlModule.clone();

    }

}
