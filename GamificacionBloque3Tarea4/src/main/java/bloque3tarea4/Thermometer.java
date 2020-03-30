package bloque3tarea4;

import java.util.ArrayList;

public class Thermometer {
	
	private static int MEMORY_SIZE = 15;
	ArrayList<Double> memory = new ArrayList<>(MEMORY_SIZE);
    
	
	public boolean isMemoryFull() {
		return memory.size() == MEMORY_SIZE;
	}
	
	public boolean isMemoryEmpty() {
		return memory.isEmpty();
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
    
    public synchronized void sendMessage(double num, String productorName) {
    	while (isMemoryFull()) {
    		try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	memoryAccess("put", num);
        System.out.println("Productor " + productorName +" add number: " + num); 
        printMemory();
        notifyAll();
    }
    
    public synchronized double receiveMessage() {
    	while (isMemoryEmpty()) {
    		try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	double result = memoryAccess("get", 0);
    	notifyAll();
    	return result;
    }
	

}
