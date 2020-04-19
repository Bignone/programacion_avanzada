package prueba1;

import java.util.concurrent.ArrayBlockingQueue;

public class VigilantePiscinaOlas extends Vigilante {
	
	public VigilantePiscinaOlas(String id, ArrayBlockingQueue<Visitante> espacio) {
		super(id, espacio);
	}
	
	public long getTiempoVigilancia() {
        return 1000;
    }
	
	public Permiso tipoPermiso(Visitante visitante) {
    	Permiso tipoPermiso = Permiso.NO_PERMITIDO;
    	if (visitante.getEdad() >= 6 && visitante.getEdad() <= 10) {
    		tipoPermiso = Permiso.CON_ACOMPANIANTE;
    	} else if (visitante.getEdad() > 10) {
    		tipoPermiso = Permiso.PERMITIDO;
    	}
        return tipoPermiso;
    }

}
