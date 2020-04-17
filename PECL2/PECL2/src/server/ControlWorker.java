package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Esta clase se encargará de generar hiloss capaces de establecer conexiones
 * con los clientes, y gestionar los mensajes que mandan.
 *
 * @author Eduardo Bustos Miranda & César Munuera Pérez
 */
public class ControlWorker extends Thread { // Clase worker para ejecutar los comandos de la conexion

    Socket conexion;
    DataOutputStream salida;
    DataInputStream entrada;
    Exposicion expo;
    HashMap<String, Integer> commands = new HashMap<String, Integer>();
    private final LinkedBlockingQueue<Socket> queue;

    /**
     * A cada worker hay que pasarle la exposición sobre la que trabajará, la
     * cola donde debe recoger su trabajo (las conexiones), y un HashMap con los
     * comandos que pueden ejecutar.
     *
     * @param expo
     * @param queue
     * @param commands
     * @throws IOException
     */
    public ControlWorker(Exposicion expo, LinkedBlockingQueue<Socket> queue, HashMap<String, Integer> commands) throws IOException {
        this.expo = expo;
        this.queue = queue;
        this.commands = commands;
        System.out.println("Worker Arrancado....");
    }

    /**
     * Función que sacaca del HM el valor en función de la key que le hemos
     * pasado.
     *
     * @param msg
     * @return
     */
    private int getCommand(String msg) {
        int result = -1;
        if (commands.containsKey(msg)) {
            result = commands.get(msg);
        }
        return result;
    }

    /**
     * Función que, dependiendo el comando que se le pase, llama a la función
     * detener o reanudar.
     *
     * @param command
     * @return
     */
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

    /**
     * Mantendremos a los workers realizando las siguientes tareas;
     *
     * Coge una conexión, establece los canales de entrada y de salida, y se
     * pone en modo receiving. Si la E/S falla, no se pone en este modo y cierra
     * la conexión.
     *
     * Después lee lo que llega del cliente, y busca el comando en el mensaje.
     * Si no hay nada que ejecutar (HABIENDO RECIBIDO EL MENSAJE), se sale y
     * cierra la conexión. Así deja de estar en modo receiving.
     *
     * Después sale, y vuelve a coger una conexión, ...
     */
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
