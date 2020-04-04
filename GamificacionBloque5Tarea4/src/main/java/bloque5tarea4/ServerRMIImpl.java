package bloque5tarea4;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class ServerRMIImpl implements ServerRMI {
	
	@Override
	public int age(String birthDate) throws RemoteException {
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date d;
			try {
				d = sdf.parse(birthDate);
			} catch (ParseException e) {
				throw new RemoteException(e.getMessage());
			}
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			LocalDate l1 = LocalDate.of(year, month, date);
			LocalDate now1 = LocalDate.now();
			Period diff1 = Period.between(l1, now1);
		
		return diff1.getYears();
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
