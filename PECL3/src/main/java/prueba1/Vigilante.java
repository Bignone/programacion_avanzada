package prueba1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Vigilante extends Thread {
	
	String id;
	Actividade actividad;
	
	public Vigilante(String id, Actividade actividad) {
		this.id = id;
		this.actividad = actividad;
	}
	
	public void run() {
    	ArrayBlockingQueue<Visitor> colaEspera = this.actividad.getColaEspera();
    	ArrayBlockingQueue<Visitor> espacio = this.actividad.getEspacioDentro();
    	BlockingQueue<Visitor> espacioParaVisitor;
    	
    	while (true) {
    		try {
				Visitor visitorPrimero = colaEspera.take();
				espacioParaVisitor = actividad.getEspacioParaVisitor(visitorPrimero);
				if (espacioParaVisitor == null) {
					colaEspera.remove(visitorPrimero);
				} 
				else if (espacioParaVisitor.remainingCapacity() > 0) {
					
				}
				if (!espacio.offer(visitorPrimero)) {
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
	}
    		
}
