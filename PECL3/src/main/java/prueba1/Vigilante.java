package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Vigilante extends Thread {
	
	String identificador;
	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	BlockingQueue<Visitante> espacio;
	
	public Vigilante(String id, ArrayBlockingQueue<Visitante> espacio) {
		this.identificador = id;
		this.espacio = espacio;
	}
	
	public boolean tienePermiso(Visitante visitante) {
		return visitante.getEdad() < 18;
	}
	
	public void run() {
		while (true) {
			try {
				sleep((long) ((int)(500) +(400*Math.random())));
				for (Visitante visitante: espacio) {
					if (tienePermiso(visitante)) {
						visitante.setPermiso(true);
					}
					else {
						System.out.println("Vigilante " + getIdentificador() + " echando al visitante " + visitante.getIdentificador() + " con edad " + visitante.getEdad());
						System.out.println("----------------------------------------------------------------------------------------------------------");
                                                visitante.interrupt(); // echar al visitante del espacio (cola)
						
					}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
