package bloque3tarea3;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Thermometer {
	
	private static int NUM_WRITERS = 5;
	private static int NUM_READERS = 5;
	private Semaphore semaphoreWriters = new Semaphore(NUM_WRITERS);
	private Semaphore semaphoreReaders = new Semaphore(NUM_READERS);
	ArrayList<Double> memory = new ArrayList<>(10);
    
	
	public Thermometer() {
    	try {
			semaphoreReaders.acquire(NUM_READERS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
	public synchronized double memoryAccess(String operation, double value) {
    	double result = -99.9;
    	if (operation.equals("get")) {
    		result = memory.remove(0);
    	}
    	else if (operation.equals("put")) {
    		memory.add(value);
    	}
    	return result;
    }
    
    public void printMemory() {
    	System.out.println("Memory: " + memory.toString());
    }
    
    public void sendMessage(double num, String productorName) {
    	try {
    		semaphoreWriters.acquire();//Si el contador es cero el thread se duerme, de lo contrario se reduce y obtiene el acceso  
    		memoryAccess("put", num);
	        System.out.println("Productor " + productorName +" add number: " + num); 
	        printMemory();
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		semaphoreReaders.release();//Libera el semaphore e incrementa el countador 
    	}
    }
    
    public double receiveMessage() {
    	double readed = -999.99;
    	try {
    		semaphoreReaders.acquire();//Si el contador es cero el thread se duerme, de lo contrario se reduce y obtiene el acceso  
    		readed = memoryAccess("get", 0);
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		semaphoreWriters.release();//Libera el semaphore e incrementa el countador 
    	}
    	return readed;
    }
	

}
