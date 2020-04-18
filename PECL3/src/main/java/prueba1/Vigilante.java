package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Vigilante extends Thread {

    String identificador;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    BlockingQueue<Visitante> espacio;

    public Vigilante(String id, ArrayBlockingQueue<Visitante> espacio) {
        this.identificador = id;
        this.espacio = espacio;
    }

    public Permiso tipoPermiso(Visitante visitante) {
    	Permiso tipoPermiso = Permiso.NO_PERMITIDO;
    	if (visitante.getEdad() > 0) {
    		tipoPermiso = Permiso.PERMITIDO;
    	}
        return tipoPermiso;
    }

    public void run() {
        while (true) {
            try {
                sleep((long) ((int) (500) + (400 * Math.random())));
                for (Visitante visitante : espacio) {
                	
                	Permiso permiso = tipoPermiso(visitante);
                	visitante.setPermisoActividad(permiso);
                    if (permiso == Permiso.NO_PERMITIDO) {
                    	System.out.println("Vigilante " + getIdentificador() + " echando al visitante " + visitante.getIdentificador() + " con edad " + visitante.getEdad());
                        System.out.println("----------------------------------------------------------------------------------------------------------");
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
