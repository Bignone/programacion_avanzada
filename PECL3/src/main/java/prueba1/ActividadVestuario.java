package prueba1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ActividadVestuario extends Actividad {

    private static int CAPACIDAD_TOTAL = 30;
    private static int CAPACIDAD_ADULTOS = 20;
    private static int CAPACIDAD_NINIOS = 10;
    private static String IDENTIFICADOR = "ActividadVestuario";
    private ArrayBlockingQueue<Visitante> zonaActividadAdultos;
    private static boolean ES_COLA_FIFO = true;
    private RegistroVisitantes registro;
    private static final String COLA_ESPERA = "-colaEspera"; 
    private static final String ZONA_ACTIVIDAD = "-zonaActividad";
    private static final String ZONA_ACTIVIDAD_ADULTOS = "-zonaActividadAdultos"; 

    public ActividadVestuario(RegistroVisitantes registro) {
        super(IDENTIFICADOR, CAPACIDAD_TOTAL, CAPACIDAD_NINIOS, ES_COLA_FIFO, registro);
        this.zonaActividadAdultos = new ArrayBlockingQueue<>(CAPACIDAD_ADULTOS, true);
    }

    public List<String> getAreasActividad() {
    	ArrayList<String> areas = new ArrayList<>();
    	areas.add(COLA_ESPERA);
    	areas.add(ZONA_ACTIVIDAD);
    	areas.add(ZONA_ACTIVIDAD_ADULTOS);
    	return areas;
    }
    
    public long getTiempoActividad() {
        return 3000;
    }

    public Vigilante iniciarVigilante() {
        return new VigilanteVestuario("VigilanteVestuarios", getColaEspera());
    }
    
    public void imprimirColas() {
    	System.out.println("******************************");
    	System.out.println(getIdentificador() + " - cola de espera: " + getColaEspera().toString());
    	System.out.println(getIdentificador() + " - zona de actividad: " + getZonaActividad().toString());
    	System.out.println(getIdentificador() + " - zona de actividad adultos: " + getZonaActividadAdultos().toString());
    	System.out.println("******************************");
    }

    public boolean entrar(VisitanteNinio visitante) throws InterruptedException {
        boolean resultado = false;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            encolarNinio(visitante);
            imprimirColas();
            getSemaforo().acquire(2);
            
            while (visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO) {
                visitante.sleep(500);
            }
            
            if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
                encolarNinioActividad(visitante);
            } else if (visitante.getPermisoActividad() == Permiso.PERMITIDO) {
            	desencolarNinioColaEspera(visitante);
            	getZonaActividad().offer(visitante);
                getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD,visitante.getIdentificador());
            	getZonaActividadAdultos().offer(visitante.getAcompaniante());
                getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_ADULTOS,visitante.getAcompaniante().getIdentificador());
            }

            resultado = true;
        } catch (SecurityException e) {
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            desencolarNinioColaEspera(visitante);
            getSemaforo().release(2);
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            visitante.setActividadActual("ParqueAcuatico");
            imprimirColas();

        }
        return resultado;
    }

    public boolean entrar(VisitanteAdulto visitante) throws InterruptedException {
        boolean resultado = false;
        try {
        	
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            getColaEspera().offer(visitante);
            visitante.setActividadActual(getIdentificador());
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            imprimirColas();
            getSemaforo().acquire();
            while (visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO) {
                visitante.sleep(500);
            }
            getColaEspera().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getZonaActividadAdultos().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_ADULTOS,visitante.getIdentificador());
            imprimirZonaActividadAdultos();
            resultado = true;

        } catch (SecurityException e) {
            getColaEspera().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getSemaforo().release();
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            visitante.setActividadActual("ParqueAcuatico");
            imprimirColas();

        }
        return resultado;
    }
    
    public boolean entrar(VisitanteMenor visitante) throws InterruptedException {
        boolean resultado = false;
        try {
        	
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            getColaEspera().offer(visitante);
            visitante.setActividadActual(getIdentificador());
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            imprimirColas();
            getSemaforo().acquire();
            while (visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO) {
                visitante.sleep(500);
            }
            getColaEspera().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getZonaActividad().offer(visitante);
            getRegistro().aniadirVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD,visitante.getIdentificador());
            imprimirZonaActividadAdultos();
            resultado = true;

        } catch (SecurityException e) {
            getColaEspera().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), COLA_ESPERA,visitante.getIdentificador());
            getSemaforo().release();
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            visitante.setActividadActual("ParqueAcuatico");
            imprimirColas();

        }
        return resultado;
    }

    private void imprimirZonaActividadAdultos() {
        System.out.println("La actividad: " + getIdentificador() + " tiene una cola de adultos: " + zonaActividadAdultos.toString());
    }

    public void salir(VisitanteAdulto visitante) {
    	getZonaActividadAdultos().remove(visitante);
        getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_ADULTOS,visitante.getIdentificador());
        getSemaforo().release();
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
        visitante.setActividadActual("ParqueAcuatico");
    }
    
    public void salir(VisitanteMenor visitante) {
    	getZonaActividad().remove(visitante);
        getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD,visitante.getIdentificador());
        getSemaforo().release();
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
        visitante.setActividadActual("ParqueAcuatico");
    }

    public void salir(VisitanteNinio visitante) {
        if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
            desencolarNinio(visitante);
        } else {
            getZonaActividad().remove(visitante);
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD,visitante.getIdentificador());
            getZonaActividadAdultos().remove(visitante.getAcompaniante());
            getRegistro().eliminarVisitanteZonaActividad(getIdentificador(), ZONA_ACTIVIDAD_ADULTOS,visitante.getAcompaniante().getIdentificador());
        }
        getSemaforo().release(2);
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
        visitante.setActividadActual("ParqueAcuatico");
    }

    public ArrayBlockingQueue<Visitante> getZonaActividadAdultos() {
        return zonaActividadAdultos;
    }

    public void setZonaActividadAdultos(ArrayBlockingQueue<Visitante> zonaActividadAdultos) {
        this.zonaActividadAdultos = zonaActividadAdultos;
    }

}
