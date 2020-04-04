package bloque3tarea1;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thermometer {
	
	private Lock lock = new ReentrantLock();
	public static double DEFAULT_MEMORY_VALUE = -99.99999;
	double memory = DEFAULT_MEMORY_VALUE;
	
	public void addtoMemory(double value) {
		memory = value;
	}
	
	public double readFromMemory() {
		return memory;
	}
	
    
    public void printMemory() {
    	System.out.println("Memory: " + memory);
    }
    
    public void sendMessage(double num, String productorName) {
    	try {
            lock.lock();
            
            addtoMemory(num);
            System.out.println("Producer " + productorName + " writes numer " + num);
            printMemory();
        } finally {
            lock.unlock();
        }
    }
    
    public double receiveMessage() {
    	try {
            lock.lock();
            return readFromMemory();
        } finally {
        	lock.unlock();
        }
    }
	

}
