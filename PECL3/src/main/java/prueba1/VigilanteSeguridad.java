package prueba1;

import java.util.concurrent.ArrayBlockingQueue;

public class VigilanteSeguridad extends Thread {
	
	String id;
	ArrayBlockingQueue<Visitor> espacio;
	Tumbonas tumbonas;
	
	public VigilanteSeguridad(String id, ArrayBlockingQueue<Visitor> espacio, Tumbonas tumbonas) {
		this.id = id;
		this.espacio = espacio;
		this.tumbonas = tumbonas;
	}
	
	public void run() {
		while (true) {
			try {
				sleep((long) ((int)(500) +(400*Math.random())));
				for (Visitor visitor: espacio) {
					if (visitor.getEdad() < 15) {
						System.out.println("Echando al visitante " + visitor.getname() + " con edad " + visitor.getEdad());
						visitor.interrupt();
						//tumbonas.echar(visitor);
					}
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			
		}
	}

}
