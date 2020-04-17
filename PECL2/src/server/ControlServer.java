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
 * Esta es la claser del Server
 *
 * @author César Munuera Pérez & Eduardo Bustos Miranda
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

    /**
     * Creo una exposición, inicializo los comandos, meto el port al server e
     * inicializo los workers
     *
     * @param expo
     * @throws IOException
     */
    public ControlServer(Exposicion expo) throws IOException {
        this.expo = expo;
        initCommands();
        servidor = new ServerSocket(PORT); // Creamos un ServerSocket
        startWorkers();
        System.out.println("Servidor Arrancado....");
    }
    
    /**
     * Función que inicializa los comandos
     */
    public void initCommands() { // iniciar los comandos del servidor
    	commands.put("detener", 0);
        commands.put("reanudar", 1);
        commands.put("close", 2);
    }
    
    /**
     * Función que crea y arranca los workers del server
     *
     * @throws IOException
     */
    public void startWorkers() throws IOException { // iniciar los workers para atender las conexiones
    	ControlWorker worker;
    	for (int i = 0; i < CONTROL_WORKERS; i++)
        {
        	worker = new ControlWorker(expo, queue, commands);
        	worker.start();
        }
    }

    /**
     * Función que calcula si la cola est� llena
     *
     * @return
     */
    public boolean spaceInQueue() { // comprobar si ha espacio en la cola para nuevas conexiones
    	return queue.size() < CONNECTION_LIMIT;
    }

    /**
     * Si queda espacio en la cola, acepto una conexi�n y se la paso a la cola.
     * Si algo de esto �ltimo da fallo, salta error y se cierra la conexión. No
     * se llega a añadir.
     */
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
    
    /**
     * Ejemplo de uso del server, crear el hilo e iniciarlo
     *
     * @param args
     * @throws UnknownHostException
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public static void main(String args[]) throws UnknownHostException, IOException, CloneNotSupportedException {

        ControlServer server = new ControlServer(null);
        server.start();

    }
}
