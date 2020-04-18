package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Actividad {

	private String identificador;
    private int capacidad = 5;
    private Semaphore semaforo;
    private ArrayBlockingQueue<Visitante> colaEspera;
    private Vigilante vigilante;
    // TODO: espacio de dentro como colaEspera
    // TODO: implementar la logica de entrar al espacio
    // TODO: implementar la salida del espacio

    public Actividad(String identificador, int capacidad) {
        this.identificador = identificador;
        this.capacidad = capacidad;
        this.colaEspera = new ArrayBlockingQueue<>(5000, true);
        this.semaforo = new Semaphore(capacidad, true);
        this.vigilante = iniciarVigilante();
        getVigilante().start();
    }
    
    public Actividad(String identificador, int capacidad, boolean colaFifo) {
        this.identificador = identificador;
        this.capacidad = capacidad;
        this.colaEspera = new ArrayBlockingQueue<>(5000, colaFifo);
        this.semaforo = new Semaphore(capacidad, true);
        this.vigilante = iniciarVigilante();
        getVigilante().start();
    }
    
    public Vigilante iniciarVigilante() {
    	return new Vigilante("VigilanteDefault", getColaEspera());
    }
    
    public synchronized void encolarNinio(Ninio visitante) {
        colaEspera.offer(visitante);
        colaEspera.offer(visitante.getAcompaniante());
    }
    public synchronized void desencolarNinio(Ninio visitante) {
        colaEspera.remove(visitante);
        colaEspera.remove(visitante.getAcompaniante());
    }
    
    public void entrar(Ninio visitante) {
        try {
            visitante.setPermiso(false);
            
            encolarNinio(visitante);
            imprimirColaEspera();
            semaforo.acquire(2);
            while(!visitante.getPermiso()){
                //esperando a que le echen o que pase
            }
        } catch (InterruptedException e) {
            desencolarNinio(visitante);
            semaforo.release(2);
            
        }
    }

    public void entrar(Adulto visitante) {
        try {

            colaEspera.offer(visitante);
            imprimirColaEspera();
            semaforo.acquire();
            while(!visitante.getPermiso()){
                //esperando a que le echen o que pase
            }
        } catch (InterruptedException e) {
            colaEspera.remove(visitante);
            semaforo.release();
        }
    }

    public void disfrutar() {
        try {
            Thread.sleep((long) ((int) (5000) + (5000 * Math.random())));
        } catch (InterruptedException e) {
            // quitar el visitante de la cola de espera
            // liberar el espacio del semaforo para que pase el siguiente
        }
    }

    public void salir() {
        semaforo.release();
        // poner el permiso a false (que deambulen por ahi sin permiso)
    }

    public String toString() {
        return this.identificador;
    }

    private void imprimirColaEspera() {
        System.out.println( "La actividad: " + identificador +" y la cola de espera es: " + colaEspera.toString());
    }
    
    public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public Semaphore getSemaforo() {
		return semaforo;
	}

	public void setSemaforo(Semaphore semaforo) {
		this.semaforo = semaforo;
	}

	public ArrayBlockingQueue<Visitante> getColaEspera() {
		return colaEspera;
	}

	public void setColaEspera(ArrayBlockingQueue<Visitante> colaEspera) {
		this.colaEspera = colaEspera;
	}

	public Vigilante getVigilante() {
		return vigilante;
	}

	public void setVigilante(Vigilante vigilante) {
		this.vigilante = vigilante;
	}
}
