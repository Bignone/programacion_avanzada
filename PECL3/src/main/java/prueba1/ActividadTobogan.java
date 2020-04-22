package prueba1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ActividadTobogan extends Actividad {

    private static int CAPACIDAD = 1;
    private static String IDENTIFICADOR = "ActividadTobogan";
    private TicketTobogan ticket;
    private ArrayBlockingQueue<Visitante> toboganC = new ArrayBlockingQueue<Visitante>(1, true);
    private ArrayBlockingQueue<Visitante> toboganB = new ArrayBlockingQueue<Visitante>(1, true);
    private ActividadPiscinaGrande piscinaGrande;
    private Vigilante vigilante2;
    private Vigilante vigilante3;
    private static final String COLA_ESPERA = "-colaEspera";
    private static final String ZONA_ACTIVIDAD = "-zonaActividad";
    private static final String ZONA_ACTIVIDAD_A = "-zonaActividadA";
    private static final String ZONA_ACTIVIDAD_B = "-zonaActividadB";
    private static final String ZONA_ACTIVIDAD_C = "-zonaActividadC";
    private static final String ZONA_ESPERA = "-zonaEsperaAcompanante";

    public ActividadTobogan(RegistroVisitantes registro, ActividadPiscinaGrande piscinaGrande) {
        super(IDENTIFICADOR, CAPACIDAD, registro);
        this.piscinaGrande = piscinaGrande;
        this.vigilante2 = iniciarVigilante();
        this.vigilante3 = iniciarVigilante();
        iniciarVigilantes();
    }
    
    public List<String> getAreasActividad() {
    	ArrayList<String> areas = new ArrayList<>();
    	areas.add(COLA_ESPERA);
    	areas.add(ZONA_ACTIVIDAD);
    	areas.add(ZONA_ACTIVIDAD_A);
    	areas.add(ZONA_ACTIVIDAD_B);
    	areas.add(ZONA_ACTIVIDAD_C);
    	areas.add(ZONA_ESPERA);
    	return areas;
    }

    public Vigilante iniciarVigilante() {
        return new VigilanteTobogan("VigilanteTobogan", getColaEspera());
    }

    public long getTiempoActividad() {
        return (long) ((int) (2000) + (1000 * Math.random()));
    }
    
    public void iniciarVigilantes() {
        vigilante2.start();
        vigilante3.start();
    }

    public boolean entrar(Ninio visitante) throws InterruptedException {
        boolean resultado = false;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            visitante.setActividadActual(getIdentificador());
            visitante.getAcompaniante().setActividadActual(getIdentificador());
            getColaEspera().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getPiscinaGrande().getZonaEsperaAcompanante().offer(visitante.getAcompaniante());
            getRegistro().aniadirVisitanteZonaActividad(getPiscinaGrande().getIdentificador(), ZONA_ESPERA,visitante.getAcompaniante().getIdentificador());
            getPiscinaGrande().getSemaforo().acquire();
            imprimirColas();
            while (visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO) {
                visitante.sleep(500);
            }

            if (visitante.getPermisoActividad() == Permiso.PERMITIDO) {
                meterTobogan(visitante);
            } else if (visitante.getPermisoActividad() == Permiso.NO_PERMITIDO) {
                throw new SecurityException();
            }
            imprimirColas();
            resultado = true;
        } catch (SecurityException e) {
            getColaEspera().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getPiscinaGrande().getZonaEsperaAcompanante().remove(visitante.getAcompaniante());
            getRegistro().eliminarVisitanteZonaActividad(getPiscinaGrande().getIdentificador(), ZONA_ESPERA,visitante.getAcompaniante().getIdentificador());
            getPiscinaGrande().getSemaforo().release(2);
            imprimirColas();
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            visitante.setActividadActual("ParqueAcuatico");
        }
        return resultado;
    }

    public boolean entrar(Adulto visitante) throws InterruptedException {
        boolean resultado = false;
        try {
        	visitante.setActividadActual(getIdentificador());
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            getColaEspera().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getPiscinaGrande().getSemaforo().acquire();
            imprimirColas();
            while (visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO) {
                visitante.sleep(500);
            }

            if (visitante.getPermisoActividad() == Permiso.PERMITIDO) {
                meterTobogan(visitante);
            }
            imprimirColas();
            resultado = true;
        } catch (SecurityException e) {
            getColaEspera().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getPiscinaGrande().getSemaforo().release();
            imprimirColas();
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            visitante.setActividadActual("ParqueAcuatico");

        }
        return resultado;
    }

    public void salir(Ninio visitante) {
        try {
            if (visitante.getTicket() == TicketTobogan.TOBOGAN_A) {
                getZonaActividad().remove(visitante); //Tobogan A
                getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_A,visitante.getIdentificador());
            } else {
                getToboganB().remove(visitante);
                getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_B,visitante.getIdentificador());
            }
            visitante.setActividadActual(getPiscinaGrande().getIdentificador());
            imprimirColas();
            getPiscinaGrande().getZonaActividad().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getPiscinaGrande().getIdentificador(), ZONA_ACTIVIDAD,visitante.getIdentificador());
            getPiscinaGrande().disfrutar(visitante);
            getPiscinaGrande().salir(visitante);
            visitante.setActividadActual("ParqueAcuatico");
        } catch (Exception e) {
        }
    }

    public void salir(Adulto visitante) {
        try {
            getToboganC().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_C,visitante.getIdentificador());
            imprimirColas();
            visitante.setActividadActual(getPiscinaGrande().getIdentificador());
            getPiscinaGrande().getZonaActividad().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getPiscinaGrande().getIdentificador(), ZONA_ACTIVIDAD,visitante.getIdentificador());
            getPiscinaGrande().disfrutar(visitante);
            getPiscinaGrande().salir(visitante);
            visitante.setActividadActual("ParqueAcuatico");
        } catch (Exception e) {
        }
    }

    public void meterTobogan(Visitante visitante) {
        getColaEspera().remove(visitante);
        getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
        if (visitante.getTicket() == TicketTobogan.TOBOGAN_A) {
        	visitante.setActividadActual("ToboganA");
            getZonaActividad().offer(visitante); //Tobogan A
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_A, visitante.getIdentificador());
        } else if (visitante.getTicket() == TicketTobogan.TOBOGAN_B) {
        	visitante.setActividadActual("ToboganB");
            getToboganB().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_B, visitante.getIdentificador());
        } else {
        	visitante.setActividadActual("ToboganC");
            getToboganC().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_C, visitante.getIdentificador());
        }
    }

    public void imprimirColas() {
        System.out.println("******************************");
        System.out.println(getIdentificador() + " - cola de espera: " + getColaEspera().toString());
        System.out.println(getIdentificador() + " - TOBOGAN A: " + getZonaActividad().toString());  //Tobogan A
        System.out.println(getIdentificador() + " - TOBOGAN B: " + getToboganB().toString());       //Tobogan B
        System.out.println(getIdentificador() + " - TOBOGAN C: " + getToboganC().toString());       //Tobogan C
        System.out.println(getIdentificador() + " - zona de espera de actividad: " + getPiscinaGrande().getZonaEsperaAcompanante().toString());
        System.out.println("******************************");
    }

    public static int getCAPACIDAD() {
        return CAPACIDAD;
    }

    public static void setCAPACIDAD(int CAPACIDAD) {
        ActividadTobogan.CAPACIDAD = CAPACIDAD;
    }

    public static String getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }

    public static void setIDENTIFICADOR(String IDENTIFICADOR) {
        ActividadTobogan.IDENTIFICADOR = IDENTIFICADOR;
    }

    public TicketTobogan getTicket() {
        return ticket;
    }

    public void setTicket(TicketTobogan ticket) {
        this.ticket = ticket;
    }

    public ArrayBlockingQueue<Visitante> getToboganC() {
        return toboganC;
    }

    public void setToboganC(ArrayBlockingQueue<Visitante> toboganC) {
        this.toboganC = toboganC;
    }

    public ArrayBlockingQueue<Visitante> getToboganB() {
        return toboganB;
    }

    public void setToboganB(ArrayBlockingQueue<Visitante> toboganB) {
        this.toboganB = toboganB;
    }

    public ActividadPiscinaGrande getPiscinaGrande() {
        return piscinaGrande;
    }

    public void setPiscinaGrande(ActividadPiscinaGrande piscinaGrande) {
        this.piscinaGrande = piscinaGrande;
    }

}
