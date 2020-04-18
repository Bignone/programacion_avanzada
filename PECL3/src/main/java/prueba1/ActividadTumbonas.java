package prueba1;

import java.util.concurrent.ArrayBlockingQueue;

public class ActividadTumbonas extends Actividad {
	
	private static int CAPACIDAD = 20;
	private static String IDENTIFICADOR = "ActividadTumbonas";
	private static boolean ES_COLA_FIFO = false;
	private ArrayBlockingQueue<Visitante> espacio = new ArrayBlockingQueue<>(CAPACIDAD, false);
	// TODO: valorar el espacio de las actividades
	// TODO: valorar si valen los metodos del padre entrar, salir, etc
	// TODO: re-implementar el entrar(Ninio) cuando zonas zonas separadas
	
	
	public ActividadTumbonas() {
		super(IDENTIFICADOR, CAPACIDAD, ES_COLA_FIFO);
		
	}
	
	public Vigilante iniciarVigilante() {
    	return new VigilanteTumbonas("VigilanteTumbonas", getColaEspera());
    }
	
	public void entrar(Visitante visitor) {
        espacio.offer(visitor);
        System.out.println("Entra el visitante " + visitor.toString());
        System.out.println(espacio.toString());
	}
	
	public void disfrutar(Visitante visitor) {
		try {
			visitor.sleep((long) ((int)(2000) +(2000*Math.random())));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void salir (Visitante visitor) {
		espacio.remove(visitor);
	}


}

