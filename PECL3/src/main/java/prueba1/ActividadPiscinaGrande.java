package prueba1;

public class ActividadPiscinaGrande extends Actividad {

    private static int CAPACIDAD = 5;
    private static String IDENTIFICADOR = "ActividadPiscinaGrande";

    public ActividadPiscinaGrande(RegistroVisitantes registro) {
        super(IDENTIFICADOR, CAPACIDAD, registro);
    }

    public Vigilante iniciarVigilante() {
        return new VigilantePiscinaGrande("VigilantePiscinaGrande", getColaEspera(), getZonaActividad());
    }

    public long getTiempoActividad() {
        return (long) ((int) (3000) + (2000 * Math.random()));
    }

}
