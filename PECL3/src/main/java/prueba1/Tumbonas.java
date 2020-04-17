package prueba1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Tumbonas {
	
	private static int CAPACIDAD = 20;
	
	String id;
	VigilanteSeguridad vigilante;
	Semaphore semaforo = new Semaphore(CAPACIDAD, false);
	ArrayBlockingQueue<Visitor> espacio = new ArrayBlockingQueue<>(CAPACIDAD, false);
	
	public Tumbonas(String id) {
		this.id = id;
		this.vigilante = new VigilanteSeguridad("Vigilante-tumbonas", espacio, this);
		this.vigilante.start();
	}
	
	public void entrar(Visitor visitor) {
        espacio.offer(visitor);
        System.out.println("Entra el visitante " + visitor.toString());
        System.out.println(espacio.toString());
	}
	
	public void disfrutar(Visitor visitor) {
		try {
			visitor.sleep((long) ((int)(2000) +(2000*Math.random())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void salir (Visitor visitor) {
		espacio.remove(visitor);
	}
	
	public void echar() {
		for (Visitor visitor: espacio) {
			if (visitor.getEdad() < 15) {
				System.out.println("Echando al visitante " + visitor.getname() + " con edad " + visitor.getEdad());
				visitor.interrupt();
			}
		}
	}

}
