package prueba1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ParqueAcuatico {
	
	private static int NUM_VISITANTES = 20;
	Semaphore semaforo = new Semaphore(NUM_VISITANTES, true);
	List<Actividad> actividades = new ArrayList<>();
	
	public void iniciarActividades() {
		this.actividades.add(new Actividad("Actividad-1-10", 10));
		this.actividades.add(new Actividad("Actividad-2-8", 8));
		this.actividades.add(new Actividad("Actividad-3-15", 15));
		this.actividades.add(new Actividad("Actividad-4-3", 3));
		this.actividades.add(new Actividad("Actividad-5-5", 5));
		this.actividades.add(new Actividad("Actividad-6-4", 4));
	}
	
	public ParqueAcuatico() {
		iniciarActividades();
	}
	
	public List<Actividad> escogerActividades(int cantidad) {
		List<Actividad> actividadesEscogidas = new ArrayList<>();
		if (cantidad < actividades.size()) {
			cantidad = actividades.size();
		}
		
		while (cantidad > 0) {
			int indice_random = (int) (actividades.size() * Math.random());
			actividadesEscogidas.add(actividades.get(indice_random));
			cantidad--;
		}
		
		return actividadesEscogidas;
		
	}
	
	public void entrar() {
		try {
			semaforo.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disfrutar() {
		try {
			Thread.sleep((long) ((int)(5000) +(5000*Math.random())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void salir() {
		semaforo.release();
	}

}
