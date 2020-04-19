package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Actividad {

    private String identificador;
    private int capacidadTotal = 5;
    private int capacidadInterior = 5;
    private Semaphore semaforo;
    private ArrayBlockingQueue<Visitante> colaEspera;
    private ArrayBlockingQueue<Visitante> zonaActividad;
    private ArrayBlockingQueue<Visitante> zonaEsperaAcompanante;
    private Vigilante vigilante;
    private RegistroVisitantes registro;

    public Actividad(String identificador, int capacidad, RegistroVisitantes registro) {
        this.identificador = identificador;
        this.capacidadTotal = capacidad;
        this.capacidadInterior = capacidad;
        this.registro = registro;
        this.colaEspera = new ArrayBlockingQueue<>(5000, true);
        this.zonaActividad = new ArrayBlockingQueue<>(capacidad, true);
        this.zonaEsperaAcompanante = new ArrayBlockingQueue<>(capacidad, true);
        this.semaforo = new Semaphore(capacidad, true);
        this.vigilante = iniciarVigilante();
        this.registro.registrarZonaActividad(identificador);
        getVigilante().start();
    }

    public Actividad(String identificador, int capacidadTotal, int capacidadInterior, boolean colaFifo, RegistroVisitantes registro) {
        this.identificador = identificador;
        this.capacidadTotal = capacidadTotal;
        this.capacidadInterior = capacidadInterior;
        this.registro = registro;
        this.colaEspera = new ArrayBlockingQueue<>(5000, colaFifo);
        this.zonaActividad = new ArrayBlockingQueue<>(capacidadInterior, true);
        this.zonaEsperaAcompanante = new ArrayBlockingQueue<>(capacidadTotal, true);
        this.semaforo = new Semaphore(capacidadTotal, true);
        this.vigilante = iniciarVigilante();
        this.registro.registrarZonaActividad(identificador);
        getVigilante().start();
    }

    public Vigilante iniciarVigilante() {
        return new Vigilante("VigilanteDefault", getColaEspera());
    }

    public long getTiempoActividad() {
        return (long) ((int) (5000) + (5000 * Math.random()));
    }

    public void imprimirColas() {
        System.out.println("******************************");
        System.out.println(getIdentificador() + " - cola de espera: " + getColaEspera().toString());
        System.out.println(getIdentificador() + " - zona de actividad: " + getZonaActividad().toString());
        System.out.println(getIdentificador() + " - zona de espera de actividad: " + getZonaEsperaAcompanante().toString());
        System.out.println("******************************");
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
        int espaciosOcupados = 2;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            encolarNinio(visitante);
            imprimirColas();
            getSemaforo().acquire(2);

            while (visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO) {
                visitante.sleep(500);
            }

            if (visitante.getPermisoActividad() == Permiso.NO_PERMITIDO) {
                throw new SecurityException();
            } else if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
                encolarNinioActividad(visitante);
            } else if (visitante.getPermisoActividad() == Permiso.PERMITIDO) {
                espaciosOcupados = 1;
                getSemaforo().release();
                desencolarNinioColaEspera(visitante);
                getZonaActividad().offer(visitante);
                getZonaEsperaAcompanante().offer(visitante.getAcompaniante());
            }

            resultado = true;
        } catch (SecurityException e) {
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            desencolarNinioColaEspera(visitante);
            getSemaforo().release(espaciosOcupados);
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            imprimirColas();

        }
        return resultado;
    }

    public boolean entrar(Adulto visitante) throws InterruptedException {
        boolean resultado = false;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            getColaEspera().offer(visitante);
            imprimirColas();
            getSemaforo().acquire();

            while (visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO) {
                visitante.sleep(500);
            }

            if (visitante.getPermisoActividad() != Permiso.PERMITIDO) {
                throw new SecurityException();
            }
            getColaEspera().remove(visitante);
            getZonaActividad().offer(visitante);
            resultado = true;

        } catch (SecurityException e) {
            getColaEspera().remove(visitante);
            getSemaforo().release();
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            imprimirColas();

        }
        return resultado;
    }

    public void disfrutar(Visitante visitante) {
        try {
            imprimirColas();
            visitante.sleep(getTiempoActividad());
        } catch (InterruptedException e) { 
            if (visitante instanceof Ninio) {
                getZonaActividad().remove(visitante);
                getZonaActividad().remove(visitante.getAcompaniante());
                getSemaforo().release(2);
            }else{
               getZonaActividad().remove(visitante); 
               getSemaforo().release();
            }
        }
    }

    public void salir(Adulto visitante) {
        getZonaActividad().remove(visitante);
        getSemaforo().release();
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);// poner el permiso a false (que deambulen por ahi sin permiso)
        imprimirColas();
    }

    public void salir(Ninio visitante) {
        if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
            desencolarNinio(visitante);
            getSemaforo().release(2);
        } else {
            getZonaActividad().remove(visitante);
            getZonaEsperaAcompanante().remove(visitante.getAcompaniante());
            getSemaforo().release();
        }

        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);// poner el permiso a false (que deambulen por ahi sin permiso)
    }

    public String toString() {
        return this.identificador;
    }

    protected void imprimirColaEspera() {
        System.out.println("La actividad: " + identificador + " y la cola de espera es: " + colaEspera.toString());
    }


    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public ArrayBlockingQueue<Visitante> getZonaActividad() {
        return this.zonaActividad;
    }

    public void setZonaActividad(ArrayBlockingQueue<Visitante> zonaActividad) {
        this.zonaActividad = zonaActividad;
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

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public int getCapacidadInterior() {
        return capacidadInterior;
    }

    public void setCapacidadInterior(int capacidadInterior) {
        this.capacidadInterior = capacidadInterior;
    }

    public ArrayBlockingQueue<Visitante> getZonaEsperaAcompanante() {
        return zonaEsperaAcompanante;
    }

    public void setZonaEsperaAcompanante(ArrayBlockingQueue<Visitante> zonaEsperaAcompanante) {
        this.zonaEsperaAcompanante = zonaEsperaAcompanante;
    }
}
