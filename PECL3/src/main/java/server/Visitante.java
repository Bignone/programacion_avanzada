package server;

import java.util.ArrayList;
import java.util.List;

import prueba1.Actividad;

public class Visitante extends Thread
{
    Exposicion expo;
    
    public Visitante(int id, Exposicion expo)
    {
        super.setName(String.valueOf(id));
        this.expo=expo;
    }
    
    public void run()
    {
    	for (Actividad act: actividades) {
	        try
	        {
	            sleep((long) ((int)(2000) +(1500*Math.random())));
	        } catch (InterruptedException e){ }
	        act.entrarYEsperar(this); //Entra en la exposición, si hay hueco; y sino espera en la cola
	        if (permiso) {
	        	act.mirar(this); //Está un tiempo dentro de la exposición
	        	act.salir(this); //Sale de la exposición
	        }
    	}
    }
    
    
    public void runSoyVigilante() {
    	List<String> colaEspera = new ArrayList<>();
    	while (true) {
    		if (actividad.tieneEspacio()) {
    			primerV = colaEspera.remove(0);
    			if (primerV.edad > actividad.edadPermitida) {
    				primerV.permiso = true;
    			} else {
    				primerV.permiso = false;
    			}
    			if primerV.esNinno() || esPadre() {
    				asociado = actividad.zonaEsperaParejas.buscar(primerV.asociado());
    				if asociado & actividad.zonaNinno {
    					actividad.zonaNinnos.meter(asociado)
    					actividad.zonaNinnos.meter(primerV)
    					asociado.notify();
    					primerV.notify();
    				} else {
    					 zonaEsperaParejas.meter(primerV);
    				}
    				actividad.zonaNinnos.meter(v);
    			} else {
    				actividad.zonaAdultos.meter(v)
    				primerV.notify();
    			}
    			
    		}
    	}
    }
}
