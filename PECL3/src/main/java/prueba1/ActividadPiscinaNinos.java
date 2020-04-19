/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba1;

/**
 *
 * @author vic88
 */
public class ActividadPiscinaNinos extends Actividad {

    private static int CAPACIDAD = 15;
    private static String IDENTIFICADOR = "ActividadPiscinaNinos";

    public ActividadPiscinaNinos() {
        super(IDENTIFICADOR, CAPACIDAD);

    }

    public Vigilante iniciarVigilante() {
        return new VigilantePiscinaNinos("VigilantePiscinaNinos", getColaEspera());
    }

    public long getTiempoActividad() {
        return (long) ((int) (1000) + (2000 * Math.random()));
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

}
