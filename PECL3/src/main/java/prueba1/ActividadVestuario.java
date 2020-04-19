package prueba1;

import java.util.concurrent.ArrayBlockingQueue;

public class ActividadVestuario extends Actividad {

    private static int CAPACIDAD_TOTAL = 30;
    private static int CAPACIDAD_ADULTOS = 20;
    private static int CAPACIDAD_NINIOS = 10;
    private static String IDENTIFICADOR = "ActividadVestuario";
    private ArrayBlockingQueue<Visitante> zonaActividadAdultos;
    private static boolean ES_COLA_FIFO = true;

    public ActividadVestuario(RegistroVisitantes registro) {
        super(IDENTIFICADOR, CAPACIDAD_TOTAL, CAPACIDAD_NINIOS, ES_COLA_FIFO, registro);
        this.zonaActividadAdultos = new ArrayBlockingQueue<>(CAPACIDAD_ADULTOS, true);
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

    public boolean entrar(Ninio visitante) throws InterruptedException {
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
            	getZonaActividadAdultos().offer(visitante.getAcompaniante());
            }

            resultado = true;
        } catch (SecurityException e) {
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            desencolarNinioColaEspera(visitante);
            getSemaforo().release(2);
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
            getColaEspera().remove(visitante);
            getZonaActividadAdultos().offer(visitante);
            imprimirZonaActividadAdultos();
            resultado = true;

        } catch (SecurityException e) {
            getColaEspera().remove(visitante);
            getSemaforo().release();
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            imprimirColas();

        }
        return resultado;
    }

    private void imprimirZonaActividadAdultos() {
        System.out.println("La actividad: " + getIdentificador() + " tiene una cola de adultos: " + zonaActividadAdultos.toString());
    }

    public void salir(Adulto visitante) {
    	getZonaActividadAdultos().remove(visitante);
        getSemaforo().release();
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);// poner el permiso a false (que deambulen por ahi sin permiso)
    }

    public void salir(Ninio visitante) {
        if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
            desencolarNinio(visitante);
        } else {
            getZonaActividad().remove(visitante);
            getZonaActividadAdultos().remove(visitante.getAcompaniante());
        }
        getSemaforo().release(2);
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);// poner el permiso a false (que deambulen por ahi sin permiso)
    }

    public static String getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }

    public static void setIDENTIFICADOR(String IDENTIFICADOR) {
        ActividadVestuario.IDENTIFICADOR = IDENTIFICADOR;
    }

    public ArrayBlockingQueue<Visitante> getZonaActividadAdultos() {
        return zonaActividadAdultos;
    }

    public void setZonaActividadAdultos(ArrayBlockingQueue<Visitante> zonaActividadAdultos) {
        this.zonaActividadAdultos = zonaActividadAdultos;
    }

}
