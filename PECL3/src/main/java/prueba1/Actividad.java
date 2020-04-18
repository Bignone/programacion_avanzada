package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Actividad {

	private String identificador;
    private int capacidad = 5;
    private Semaphore semaforo;
    private ArrayBlockingQueue<Visitante> colaEspera;
    private ArrayBlockingQueue<Visitante> zonaActividad;
    private Vigilante vigilante;


    public Actividad(String identificador, int capacidad) {
        this.identificador = identificador;
        this.capacidad = capacidad;
        this.colaEspera = new ArrayBlockingQueue<>(5000, true);
        this.semaforo = new Semaphore(capacidad, true);
        this.vigilante = iniciarVigilante();
        this.zonaActividad= new ArrayBlockingQueue<>(capacidad, true);
        getVigilante().start();
    }
    
    public Actividad(String identificador, int capacidad, boolean colaFifo) {
        this.identificador = identificador;
        this.capacidad = capacidad;
        this.colaEspera = new ArrayBlockingQueue<>(5000, colaFifo);
        this.semaforo = new Semaphore(capacidad, true);
        this.vigilante = iniciarVigilante();
        this.zonaActividad= new ArrayBlockingQueue<>(capacidad, true);
        getVigilante().start();
    }
    
    public Vigilante iniciarVigilante() {
    	return new Vigilante("VigilanteDefault", getColaEspera());
    }
    
    public long getTiempoActividad() {
    	return (long) ((int) (5000) + (5000 * Math.random()));
    }
    
    public synchronized void encolarNinio(Ninio visitante) {
        getColaEspera().offer(visitante);
        getColaEspera().offer(visitante.getAcompaniante());
    }
    public synchronized void desencolarNinioColaEspera(Ninio visitante) {
    	getColaEspera().remove(visitante);
    	getColaEspera().remove(visitante.getAcompaniante());
    }
    
    public synchronized void encolarNinioActividad(Ninio visitante) {
    	getColaEspera().remove(visitante);
    	getColaEspera().remove(visitante.getAcompaniante());
        getZonaActividad().offer(visitante);
        getZonaActividad().offer(visitante.getAcompaniante());
    }

	public synchronized void desencolarNinio(Ninio visitante) {
		getZonaActividad().remove(visitante);
		getZonaActividad().remove(visitante.getAcompaniante());
    }
    
    public boolean entrar(Ninio visitante) throws InterruptedException {
        boolean resultado = false;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            encolarNinio(visitante);
            imprimirColaEspera();
            getSemaforo().acquire(2);
            while(visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO){
                Thread.sleep(500);
            }
            if (visitante.getPermisoActividad() == Permiso.NO_PERMITIDO) {
                    throw new SecurityException();
            } else if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
            	encolarNinioActividad(visitante);
            } else if (visitante.getPermisoActividad() == Permiso.PERMITIDO) {
            	getColaEspera().offer(visitante);
            }
            
            resultado = true;
        } catch (SecurityException e) {
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            desencolarNinioColaEspera(visitante);
            getSemaforo().release(2);
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            imprimirColaEspera();
            
        }
        return resultado;
    }

    public boolean entrar(Adulto visitante) throws InterruptedException {
        boolean resultado = false;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            getColaEspera().offer(visitante);
            imprimirColaEspera();
            getSemaforo().acquire();
            while(visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO){
                Thread.sleep(500);
            }
            if (visitante.getPermisoActividad() != Permiso.PERMITIDO) {
                    throw new SecurityException();
                }
            getColaEspera().remove(visitante);
            getZonaActividad().offer(visitante);
            resultado= true;
            
        } catch (SecurityException e) {
        	getColaEspera().remove(visitante);
        	getSemaforo().release();
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            
        }
        return resultado;
    }

    public void disfrutar() {
        try {
            imprimirZonaActividad();
            Thread.sleep(getTiempoActividad());
        } catch (InterruptedException e) {
            // quitar el visitante de la cola de espera
            // liberar el espacio del semaforo para que pase el siguiente
        }
    }
    
    public void salir(Adulto visitante) {
    	getZonaActividad().remove(visitante);
    	getSemaforo().release();
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);// poner el permiso a false (que deambulen por ahi sin permiso)
    }
    
    public void salir(Ninio visitante) {
        desencolarNinio(visitante);
        getSemaforo().release(2);
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);// poner el permiso a false (que deambulen por ahi sin permiso)
    }
    
    public String toString() {
        return this.identificador;
    }

    protected void imprimirColaEspera() {
        System.out.println( "La actividad: " + identificador +" y la cola de espera es: " + colaEspera.toString());
    }
    
    private void imprimirZonaActividad() {
        System.out.println( "La actividad: " + identificador +" y la zona de la actividad es: " + zonaActividad.toString());
    }
    
    public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public ArrayBlockingQueue<Visitante> getZonaActividad() {
		return zonaActividad;
	}

	public void setZonaActividad(ArrayBlockingQueue<Visitante> zonaActividad) {
		this.zonaActividad = zonaActividad;
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
