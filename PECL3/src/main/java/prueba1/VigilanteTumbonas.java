package prueba1;

import java.util.concurrent.ArrayBlockingQueue;

public class VigilanteTumbonas extends Vigilante {
	
	public VigilanteTumbonas(String id, ArrayBlockingQueue<Visitante> espacio) {
		super(id, espacio);
	}
	
	public boolean tienePermiso(Visitante visitante) {
		return visitante.getEdad() >= 15;
	}

}
