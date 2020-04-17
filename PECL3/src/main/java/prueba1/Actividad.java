package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
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
			// poner el permiso del visitante a false (por si viene en true de otra actividad)
			// colocar al visitante en la cola (aqui el vigilante la pondra a true cuando pase)
			semaforo.acquire(); // el visitante se va a a dormir
			// esperar a tener permiso para pasar (espera activa, es finita hasta que pase el vigilante o hasta que el vigilante le eche)
		} catch (InterruptedException  e) {
			// el vigilante te ha echado porque eres demasiado malo para la actividad
			// quitar el visitante de la cola de espera
			// liberar el espacio del semaforo para que pase el siguiente
			e.printStackTrace();
		}
	}
	
	public void disfrutar() {
		try {
			Thread.sleep((long) ((int)(5000) +(5000*Math.random())));
		} catch (InterruptedException e) {
			// quitar el visitante de la cola de espera
			// liberar el espacio del semaforo para que pase el siguiente
			e.printStackTrace();
		}
	}
	
	public void salir() {
		semaforo.release();
		// poner el permiso a false (que deambulen por ahi sin permiso)
	}
	
	public String toString() {
		return this.identificador;
	}

}
