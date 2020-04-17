package prueba1;

import java.util.concurrent.Semaphore;

public class Actividad {
	
	private String identificador;
	private int capacidad = 5;
	Semaphore semaforo;
	
	public Actividad(String identificador, int capacidad) {
		this.identificador = identificador;
		this.capacidad = capacidad;
		this.semaforo = new Semaphore(capacidad, true);
		
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
	
	public String toString() {
		return this.identificador;
	}

}
