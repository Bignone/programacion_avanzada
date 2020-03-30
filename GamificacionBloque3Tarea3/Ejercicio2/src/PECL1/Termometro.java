/*
 * La clase Termometro tiene que estar protegida con un cerrojo
 * El método enviaMensaje debe esperar si el buzón está lleno
 * El método recibeMensaje debe esperar si el buzón está vacío.
 * Cuando un hilo completa su operación, desbloquea a los que estén esperando
 * para que puedan continuar intentando su acción.
 */
package PECL1;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Termometro {

	private static int NUM_WRITERS = 10;
	private static int NUM_READERS = 10;
	private Semaphore semaphoreWriters = new Semaphore(NUM_WRITERS);
	private Semaphore semaphoreReaders = new Semaphore(NUM_READERS);
	
	private Lock cerrojo = new ReentrantLock();
    private Condition hay_hueco = cerrojo.newCondition();
    private Condition hay_mensaje = cerrojo.newCondition();

    ArrayList<String> AL = new ArrayList<String>(15);
    ArrayList<Double> memory = new ArrayList<>(10);
    
    public Termometro() {
    	try {
			semaphoreReaders.acquire(NUM_READERS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private boolean isMemoryEmpty() {
    	return memory.isEmpty();
    }
    
    private boolean esVacio (ArrayList<String> AL){
        if (AL.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
    
    private boolean esLleno (ArrayList<String> AL){
        if (AL.size() == 15){
            return true;
        }else{
            return false;
        }
    }

//    public void enviaMensaje(String num, String Productor) {
//        try {
//            cerrojo.lock();
//            while (esLleno(AL)) {
//                try {
//                    hay_hueco.await(); // espera a que le manden una señal
//                } catch (Exception e) {
//                }
//            }
//            AL.add(num);
//            System.out.println(Productor+" genera "+ num);
//            hay_mensaje.signalAll();
//        } finally {
//            cerrojo.unlock();
//        }
//    }
    
    
    synchronized public double memoryAccess(String operation, double value) {
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

//    public String recibeMensaje() {
//        String mensaje;
//        try {
//            cerrojo.lock();
//            while (esVacio(AL)) {
//                try {
//                    hay_mensaje.await(); // espera a que le manden una señal
//                } catch (Exception e) {
//                }
//            }
//            mensaje = AL.get(0);
//            AL.remove(0);   
//            hay_hueco.signalAll();
//            return mensaje;
//        } finally {
//            cerrojo.unlock();
//        }
//    }
}
