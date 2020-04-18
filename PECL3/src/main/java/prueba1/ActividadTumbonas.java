package prueba1;


public class ActividadTumbonas extends Actividad {
	
	private static int CAPACIDAD = 20;
	private static String IDENTIFICADOR = "ActividadTumbonas";
	private static boolean ES_COLA_FIFO = false;
	// TODO: valorar el espacio de las actividades
	// TODO: valorar si valen los metodos del padre entrar, salir, etc
	// TODO: re-implementar el entrar(Ninio) cuando zonas zonas separadas
	
	
	public ActividadTumbonas() {
		super(IDENTIFICADOR, CAPACIDAD, ES_COLA_FIFO);
		
	}
	
	public long getTiempoActividad() {
    	return (long) ((int) (2000) + (2000 * Math.random()));
    }
	
	public Vigilante iniciarVigilante() {
    	return new VigilanteTumbonas("VigilanteTumbonas", getColaEspera());
    }

}

