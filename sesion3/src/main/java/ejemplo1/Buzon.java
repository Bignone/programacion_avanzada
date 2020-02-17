/*
 * La clase Buzon tiene que estar protegida con un cerrojo
 * El método enviaMensaje debe esperar si el buzón está lleno
 * El método recibeMensaje debe esperar si el buzón está vacío.
 * Cuando un hilo completa su operación, desbloquea a los que estén esperando
 * para que puedan continuar intentando su acción.
 */
package ejemplo1;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buzon
{
    private ArrayList<Integer> buffer = new ArrayList<Integer>();
    private static int bufferLimit = 15;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    
    public void sendMessage(int number)
    {
    	
    	try {
    		lock.lock();
    		while (isbBufferFull()) {
    			try {
    				condition.await();
    			} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			
    		}
    		
    		condition.signalAll();
    		bufferPush(number);
	        	
		} finally {
			lock.unlock();
		}
    }
    
    public int receiveMessage()
    {
    	try {
	    	lock.lock();
	    	while (isbBufferEmpty()) {
	    		try {
					condition.await();
	    		
	    		} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    	
	    	condition.signalAll();
	        return bufferPop();
    	} finally
    	{
			lock.unlock();
		}
    }
    
    public int bufferPush(int number) {
        buffer.add(number);
		return number;
    }
    
    public int bufferPop() {
    	if (!buffer.isEmpty()) {
    		return (int) buffer.remove(0);
    	} 
    	else {
    		throw new IndexOutOfBoundsException("Empty buffer");
    	}
    }
    
    public boolean isbBufferFull() {
        return buffer.size() == bufferLimit;
    }
    
    public boolean isbBufferEmpty() {
        return buffer.isEmpty();
    }


}