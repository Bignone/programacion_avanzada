package bloque5tarea4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
	
	public static void main(String args[]) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost");
        ServerRMI obj = (ServerRMI) registry.lookup("ServerRMI");
        String birthDate = "1992/12/22";
        System.out.println("You are " + obj.age(birthDate) + " years old"); 
    }

}
