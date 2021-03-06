package prueba1;


public class ActividadTumbonas extends Actividad {
	
	private static int CAPACIDAD = 20;
	private static String IDENTIFICADOR = "ActividadTumbonas";
	private static boolean ES_COLA_FIFO = false;
	
	public ActividadTumbonas(RegistroVisitantes registro) {
		super(IDENTIFICADOR, CAPACIDAD, CAPACIDAD, ES_COLA_FIFO, registro);
	}
	
	public long getTiempoActividad() {
    	return (long) ((int) (2000) + (2000 * Math.random()));
    }
	
	public Vigilante iniciarVigilante() {
        Vigilante vigilante = new VigilanteTumbonas("VigilanteTumbonas", getColaEspera());
    	getRegistro().aniadirMonitorEnZona(getIdentificador(), "-monitor", vigilante.getIdentificador());
        return vigilante;
    }

}

