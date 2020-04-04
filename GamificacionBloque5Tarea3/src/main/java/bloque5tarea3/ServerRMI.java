package bloque5tarea3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote {
	
	public double multiply(double op1, double op2) throws RemoteException;
	public double pow(double base, double exp) throws RemoteException;

}
