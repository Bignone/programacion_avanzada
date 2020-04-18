package prueba1;

import static java.lang.Thread.sleep;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ActividadPiscinaOlas extends Actividad {
	
	private static int CAPACIDAD = 20;
	private static String IDENTIFICADOR = "ActividadPiscinaOlas";
	CyclicBarrier barrera = new CyclicBarrier(2);
	
	
	public ActividadPiscinaOlas() {
		super(IDENTIFICADOR, CAPACIDAD);
		
	}
	
	public Vigilante iniciarVigilante() {
    	return new VigilantePiscinaOlas("VigilantePisinaOlas", getColaEspera());
    }
	
	public long getTiempoActividad() {
    	return (long) ((int) (2000) + (3000 * Math.random()));
    }
	
	public boolean entrar(Ninio visitante) {
		boolean resultado = false;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            encolarNinio(visitante);
            imprimirColaEspera();
            getSemaforo().acquire(2);
            
            while(visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO){
                Thread.sleep(500);
            }
            
            if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
            	encolarNinioActividad(visitante);
            } else if (visitante.getPermisoActividad() == Permiso.PERMITIDO) {
            	getSemaforo().release();
            	barrera.await();
            	getColaEspera().remove(visitante);
                getZonaActividad().offer(visitante);
            } else {
            	throw new SecurityException();
            }
            
            resultado = true;
        } catch (SecurityException | InterruptedException | BrokenBarrierException e) {
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            desencolarNinioColaEspera(visitante);
            getSemaforo().release(2);
            
            imprimirColaEspera();
            
        }
        return resultado;
	}
	
	public boolean entrar(Adulto visitante) throws InterruptedException {
        boolean resultado = false;
        try {
            visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);
            getColaEspera().offer(visitante);
            imprimirColaEspera();
            getSemaforo().acquire();
            
            while(visitante.getPermisoActividad() == Permiso.NO_ESPECIFICADO){
                Thread.sleep(500);
            }
            
            if (visitante.getPermisoActividad() == Permiso.PERMITIDO){
            	barrera.await();
            	getColaEspera().remove(visitante);
                getZonaActividad().offer(visitante);
            }
            else {
                    throw new SecurityException();
                }
            
            resultado= true;
            
        } catch (SecurityException | BrokenBarrierException e) {
        	getColaEspera().remove(visitante);
        	getSemaforo().release();
            
        }
        return resultado;
	}
    
    public void salir(Ninio visitante) {
    	if (visitante.getPermisoActividad() == Permiso.CON_ACOMPANIANTE) {
    		desencolarNinio(visitante);
    		getSemaforo().release(2);
        } else {
        	getZonaActividad().remove(visitante);
        	getSemaforo().release(1);
        }
        
        
        visitante.setPermisoActividad(Permiso.NO_ESPECIFICADO);// poner el permiso a false (que deambulen por ahi sin permiso)
    }


}

