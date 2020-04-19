package prueba1;

import java.util.concurrent.ArrayBlockingQueue;

public class VigilanteVestuario extends Vigilante {

    public VigilanteVestuario(String id, ArrayBlockingQueue<Visitante> espacio) {
        super(id, espacio);
    }
    
    public long getTiempoVigilancia() {
        return 1000;
    }

    public Permiso tipoPermiso(Visitante visitante) {
        Permiso tipoPermiso = Permiso.PERMITIDO;
        if (visitante.getEdad() <= 10) {
            tipoPermiso = Permiso.CON_ACOMPANIANTE;
        }
        return tipoPermiso;
    }

}
