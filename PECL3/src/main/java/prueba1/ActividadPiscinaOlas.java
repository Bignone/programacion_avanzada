package prueba1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ActividadPiscinaOlas extends Actividad {
	
	private static int CAPACIDAD = 20;
	private static String IDENTIFICADOR = "ActividadPiscinaOlas";
	CyclicBarrier barrera = new CyclicBarrier(2);
	
	
	public ActividadPiscinaOlas(RegistroVisitantes registro) {
		super(IDENTIFICADOR, CAPACIDAD, registro);
		
	}
	
	public Vigilante iniciarVigilante() {
    	return new VigilantePiscinaOlas("VigilantePisinaOlas", getColaEspera());
    }
	
	public long getTiempoActividad() {
    	return (long) ((int) (2000) + (3000 * Math.random()));
    }
	
	public boolean entrar(Ninio visitante) {
		boolean resultado = false;
		int espaciosOcupados = 2;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            encolarNinio(visitante);
            imprimirColas();
            getSemaforo().acquire(2);
            
            while(visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO){
                visitante.sleep(500);
            }
            
            if (visitante.getPermisoActividad() == Permiso.NO_PERMITIDO) {
                throw new SecurityException();
            } else if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
            	encolarNinioActividad(visitante);
            } else if (visitante.getPermisoActividad() == Permiso.PERMITIDO) {
            	espaciosOcupados = 1;
            	getSemaforo().release();
            	barrera.await();
            	desencolarNinioColaEspera(visitante);
                getZonaActividad().offer(visitante);
                getZonaEsperaAcompanante().offer(visitante.getAcompaniante());
            }
            
            resultado = true;
        } catch (SecurityException | InterruptedException | BrokenBarrierException e) {
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            desencolarNinioColaEspera(visitante);
            getSemaforo().release(espaciosOcupados);
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
            
            while(visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO){
            	visitante.sleep(500);
            }
            
            if (visitante.getPermisoActividad() != Permiso.PERMITIDO) {
                throw new SecurityException();
            }
        	barrera.await();
        	getColaEspera().remove(visitante);
            getZonaActividad().offer(visitante);
            
            resultado= true;
            
        } catch (SecurityException | BrokenBarrierException e) {
        	getColaEspera().remove(visitante);
        	getSemaforo().release();
        	visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            imprimirColas();
        }
        return resultado;
	}


}

