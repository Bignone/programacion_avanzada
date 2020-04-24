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
    private RegistroVisitantes registro = new RegistroVisitantes();
    private ActividadPiscinaGrande piscinaGrande;

    public ParqueAcuatico() {
        iniciarActividades();
    }
    
    public void iniciarActividades() {
        this.actividades.add(new ActividadVestuario(registro));
        this.actividades.add(new ActividadTumbonas(registro));
        this.actividades.add(new ActividadPiscinaOlas(registro));
        this.actividades.add(new ActividadPiscinaNinos(registro));
        this.piscinaGrande = new ActividadPiscinaGrande(registro);
        this.actividades.add(piscinaGrande);
        this.actividades.add(new ActividadTobogan(registro, piscinaGrande));
    }

    public RegistroVisitantes getRegistro() {
		return registro;
	}

    public List<Actividad> escogerActividades(int cantidad) {
        List<Actividad> actividadesEscogidas = new ArrayList<>();
        if (cantidad <= 0) {
            cantidad = actividades.size();
        }
        actividadesEscogidas.add(actividades.get(0));

        while (cantidad > 0) {
            int indice_random = (int) (actividades.size() * Math.random());
            if (indice_random == 0) { // vestuario
                indice_random = 1;
            }
            actividadesEscogidas.add(actividades.get(indice_random));
            cantidad--;
        }
        actividadesEscogidas.add(actividades.get(0));

        return actividadesEscogidas;

    }

    public boolean entrar(Ninio visitante) {
    	boolean resultado = false;
        try {
            encolarNinio(visitante);
            visitante.setActividadActual("ParqueAcuatico");
            imprimirColaEspera();
            semaforo.acquire(2);
            desencolarNinioColaEspera(visitante);
            resultado = true;
        } catch (Exception e) {
            e.printStackTrace();
            visitante.setActividadActual("Fuera");
        }
        return resultado;
    }

    public boolean entrar(Adulto visitante) {
    	boolean resultado = false;
        try {
            getColaEspera().offer(visitante);
            visitante.setActividadActual("ParqueAcuatico");
            imprimirColaEspera();
            semaforo.acquire();
            getColaEspera().remove(visitante);
            resultado = true;
        } catch (Exception e) {
            e.printStackTrace();
            visitante.setActividadActual("Fuera");
        }
        return resultado;
    }

    public void salir(Adulto visitante) {
        getColaEspera().remove(visitante);
        imprimirColaEspera();
        semaforo.release();
        visitante.setActividadActual("Fuera");
    }

    public void salir(Ninio visitante) {
        desencolarNinioColaEspera(visitante);
        imprimirColaEspera();
        semaforo.release();
        visitante.setActividadActual("Fuera");
    }

    public synchronized void encolarNinio(Ninio visitante) {
        getColaEspera().offer(visitante);
        getColaEspera().offer(visitante.getAcompaniante());
    }

    public synchronized void desencolarNinioColaEspera(Ninio visitante) {
        getColaEspera().remove(visitante);
        getColaEspera().remove(visitante.getAcompaniante());
    }

    private void imprimirColaEspera() {
        
    }

    public static int getNUM_VISITANTES() {
        return NUM_VISITANTES;
    }

    public static void setNUM_VISITANTES(int NUM_VISITANTES) {
        ParqueAcuatico.NUM_VISITANTES = NUM_VISITANTES;
    }

    public Semaphore getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public BlockingQueue<Visitante> getColaEspera() {
        return colaEspera;
    }

    public void setColaEspera(BlockingQueue<Visitante> colaEspera) {
        this.colaEspera = colaEspera;
    }

}
