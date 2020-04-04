package bloque5tarea3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMIImpl implements ServerRMI {

	@Override
	public double multiply(double op1, double op2) {
		return op1 * op2;
	}

	@Override
	public double pow(double base, double exp) {
		return Math.pow(base, exp);
	}
	
	public static void main(String args[]) throws Exception {

        System.out.println("RMI server started");
        
        //Instantiate RmiServer
        ServerRMI obj = new ServerRMIImpl();
 
        try { //special exception handler for registry creation
        	
        	ServerRMI stub = (ServerRMI) UnicastRemoteObject.exportObject(obj,0);
            Registry reg;
            try {
            	reg = LocateRegistry.createRegistry(1099);
                System.out.println("java RMI registry created.");

            } catch(Exception e) {
            	System.out.println("Using existing registry");
            	reg = LocateRegistry.getRegistry();
            }
        	reg.rebind("ServerRMI", stub);

        } catch (RemoteException e) {
        	e.printStackTrace();
        }
    }

}
