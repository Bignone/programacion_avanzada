package bloque3tarea2;


import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thermometer {
	
	private static int MEMORY_SIZE = 15;
	private Lock lock = new ReentrantLock();
    private Condition spaceInMemory = lock.newCondition();
    private Condition valuesInMemory = lock.newCondition();

	ArrayList<Double> memory = new ArrayList<>(MEMORY_SIZE);
    
	public boolean isMemoryFull() {
		return memory.size() == MEMORY_SIZE;
	}
	
	public boolean isMemoryEmpty() {
		return memory.isEmpty();
	}
	
	public void addtoMemory(double value) {
		memory.add(value);
	}
	
	public double readFromMemory() {
		return memory.remove(0);
	}
    
    public void printMemory() {
    	System.out.println("Memory: " + memory.toString());
    }
    
    public void sendMessage(double num, String productorName) {
    	try {
            lock.lock();
            while (isMemoryFull()) {
                try {
                	spaceInMemory.await();
                } catch (Exception e) {
                }
            }
            addtoMemory(num);
            System.out.println("Producer " + productorName + " writes numer " + num);
            printMemory();
            valuesInMemory.signalAll();
        } finally {
            lock.unlock();
        }
    }
    
    public double receiveMessage() {
    	try {
            lock.lock();
            while (isMemoryEmpty()) {
                try {
                	valuesInMemory.await(); // espera a que le manden una señal
                } catch (Exception e) {
                }
            }
            double readed = readFromMemory();
            spaceInMemory.signalAll();
            return readed;
        } finally {
        	lock.unlock();
        }
    }
	

}
