package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class VigilanteCola extends Thread {
	
	String id;
	BlockingQueue<Visitante> espacio;
	
	public VigilanteCola(String id, ArrayBlockingQueue<Visitante> espacio) {
		this.id = id;
		this.espacio = espacio;
	}
	
	public boolean tienePermiso(Visitante visitante) {
		return visitante.getEdad() < 15;
	}
	
	public void run() {
		while (true) {
			try {
				sleep((long) ((int)(500) +(400*Math.random())));
				for (Visitante visitante: espacio) {
					if (tienePermiso(visitante)) {
						// visitante.darPermiso();
					}
					else {
						System.out.println("Echando al visitante " + visitante.getIdentificador() + " con edad " + visitante.getEdad());
						visitante.interrupt(); // echar al visitante del espacio (cola)
						
					}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
