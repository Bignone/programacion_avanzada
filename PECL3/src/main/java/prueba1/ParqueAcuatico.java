package prueba1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class ParqueAcuatico {
	
	private static int NUM_VISITANTES = 20;
	private Semaphore semaforo = new Semaphore(NUM_VISITANTES, true);
	private List<Actividad> actividades = new ArrayList<>();
	private BlockingQueue<Visitante> colaEspera = new ArrayBlockingQueue<>(5000, true);

	
	public void iniciarActividades() {
//		this.actividades.add(new Actividad("Actividad-1-10", 10));
//		this.actividades.add(new Actividad("Actividad-2-8", 8));
//		this.actividades.add(new Actividad("Actividad-3-15", 15));
//		this.actividades.add(new Actividad("Actividad-4-3", 3));
//		this.actividades.add(new Actividad("Actividad-5-5", 5));
		this.actividades.add(new Actividad("Actividad-6-4", 4));
		this.actividades.add(new ActividadTumbonas());
		this.actividades.add(new ActividadPiscinaOlas());
	}
	
	public ParqueAcuatico() {
		iniciarActividades();
	}
	
	public List<Actividad> escogerActividades(int cantidad) {
		List<Actividad> actividadesEscogidas = new ArrayList<>();
		if (cantidad <= 0) {
			cantidad = actividades.size();
		}
		
//		while (cantidad > 0) {
//			int indice_random = (int) (actividades.size() * Math.random());
//			actividadesEscogidas.add(actividades.get(indice_random));
//			cantidad--;
//		}
		
		actividadesEscogidas.add(actividades.get(0));
		actividadesEscogidas.add(actividades.get(1));
		actividadesEscogidas.add(actividades.get(2));
		
		return actividadesEscogidas;
		
	}
	
	public synchronized void encolarNinio(Ninio visitante) {
		colaEspera.offer(visitante);
		colaEspera.offer(visitante.getAcompaniante());
	}
	
	public void entrar(Ninio visitante) {
		try {
			encolarNinio(visitante);
            imprimirColaEspera();
			semaforo.acquire(2);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void entrar(Adulto visitante) {
		try {
			colaEspera.offer(visitante);
            imprimirColaEspera();
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
	
	public void salir(Visitante visitante) {
		colaEspera.remove(visitante);
		imprimirColaEspera();
		semaforo.release();
	}
	
	private void imprimirColaEspera(){
        System.out.println("La cola de espera del parque es: " + colaEspera.toString());
    }

}
