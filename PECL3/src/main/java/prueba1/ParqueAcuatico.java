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
        this.actividades.add(new ActividadVestuario());
        this.actividades.add(new ActividadTumbonas());
        this.actividades.add(new ActividadPiscinaOlas());
       this.actividades.add(new ActividadPiscinaNinos());
    }

    public ParqueAcuatico() {
        iniciarActividades();
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
        
//        actividadesEscogidas.add(actividades.get(1));
//        actividadesEscogidas.add(actividades.get(2));
        
        actividadesEscogidas.add(actividades.get(0));

        return actividadesEscogidas;

    }

    public void entrar(Ninio visitante) {
        try {
            encolarNinio(visitante);
            imprimirColaEspera();
            semaforo.acquire(2);
            desencolarNinioColaEspera(visitante);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void entrar(Adulto visitante) {
        try {
            getColaEspera().offer(visitante);
            imprimirColaEspera();
            semaforo.acquire();
            getColaEspera().remove(visitante);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disfrutar() {
        try {
            Thread.sleep((long) ((int) (5000) + (5000 * Math.random())));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void salir(Adulto visitante) {
        getColaEspera().remove(visitante);
        imprimirColaEspera();
        semaforo.release();
    }

    public void salir(Ninio visitante) {
        desencolarNinioColaEspera(visitante);
        imprimirColaEspera();
        semaforo.release();
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
        System.out.println("La cola de espera del parque es: " + colaEspera.toString());
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
