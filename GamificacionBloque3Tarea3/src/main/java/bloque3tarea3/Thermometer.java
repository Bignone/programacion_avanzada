package bloque3tarea3;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Thermometer {
	
	private static int NUM_WRITERS = 1;
	private static int NUM_READERS = 1;
	private Semaphore semaphoreWriters = new Semaphore(NUM_WRITERS);
	private Semaphore semaphoreReaders = new Semaphore(NUM_READERS);
	private Semaphore semaphoreMemory = new Semaphore(1);
	ArrayList<Double> memory = new ArrayList<>(10);
    
	
	public Thermometer() {
    	try {
			semaphoreReaders.acquire(NUM_READERS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
	public void writeInMemory(double value) {
		try {
			semaphoreMemory.acquire();
			memory.add(value);
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		semaphoreMemory.release();
    	}
	}
	
	public double readFromMemory() {
		double result = -99.9;
		try {
			semaphoreMemory.acquire();
			result = memory.remove(0);
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		semaphoreMemory.release();
    	}
		return result;
	}
	
	public double memoryAccess(String operation, double value) {
		
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
    		semaphoreWriters.acquire();  
    		writeInMemory(num);
	        System.out.println("Productor " + productorName +" add number: " + num); 
	        printMemory();
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		semaphoreReaders.release(); 
    	}
    }
    
    public double receiveMessage() {
    	double readed = -999.99;
    	try {
    		semaphoreReaders.acquire();
    		readed = readFromMemory();
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		semaphoreWriters.release();
    	}
    	return readed;
    }
	

}
