package bloque5tarea4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote {
	
	public int age(String birthDate) throws RemoteException;

}
