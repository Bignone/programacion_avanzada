package bloque5tarea3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
	
	public static void main(String args[]) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost");
        ServerRMI obj = (ServerRMI) registry.lookup("ServerRMI");
        double op1 = 3;
        double op2 = 6;
        System.out.println("Multiply " + op1 + " * " + op2 + " = " + obj.multiply(op1, op2)); 
        double base = 2;
        double exp = 3;
        System.out.println("Pow " + base + " ^ " + exp + " = " + obj.pow(base, exp)); 
    }

}
