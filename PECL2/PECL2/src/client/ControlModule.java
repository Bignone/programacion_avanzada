/*
 *  Esta es la clase del Cliente.
 */
package client;

import java.io.*;
import java.net.*;


//@author Eduardo Bustos Miranda & César Munuera Pérez 
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

    /**
     * Crea la conexion con el servidor y los canales de datos
     *
     * @throws IOException
     */
    public void connect() throws IOException { // crear la conexion con el servidor y los canales de datos
        socket = new Socket(this.address, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Función que envía y recibe mensajes del servidor.
     *
     * @param msg
     * @return
     */
    public String sendMessage(String msg) {
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

    /**
     * Función que cierra la conexión, mandando el comando close
     *
     * @throws IOException
     */
    public void close() throws IOException { 
    	sendMessage(COMMAND_CLOSE); 
        socket.close();
    }

    /**
     * Función que da la orden de reanudar, de los botones del Cliente
     *
     * @return
     */
    public String reanudar() { 
        return sendMessage(COMMAND_REANUDAR);
    }

    /**
     * Función que da la orden de detener, de los botones del Cliente
     *
     * @return
     */
    public String detener() {
        return sendMessage(COMMAND_DETENER);
    }

    
    /**
     * Ejemplo de uso del cliente, conectar y mandar comandos
     *
     * @param args
     * @throws UnknownHostException
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public static void main(String args[]) throws UnknownHostException, IOException, CloneNotSupportedException {

        ControlModule controlModule = new ControlModule(InetAddress.getLocalHost(), 5000); // crear el controlador
        controlModule.connect(); // se crea y abre una conexion y se queda abierta

        controlModule.reanudar();
        controlModule.detener();

    }

}
