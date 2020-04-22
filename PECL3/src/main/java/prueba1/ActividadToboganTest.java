package prueba1;

import java.util.ArrayList;
import java.util.List;

public class ActividadToboganTest {

    public static void main(String[] args) {
        ParqueAcuatico parque = new ParqueAcuatico();
        ActividadVestuario vestuario = new ActividadVestuario(parque.getRegistro());
        ActividadPiscinaGrande piscinaGrande = new ActividadPiscinaGrande(parque.getRegistro());
        List<Actividad> actividades = new ArrayList<>();
        ActividadTobogan actividadTobogan = new ActividadTobogan(parque.getRegistro(), piscinaGrande);
        actividades.add(vestuario);
        actividades.add(actividadTobogan);
        parque.setActividades(actividades);

        GeneradorVisitantes generadorVisitantes = new GeneradorVisitantes(parque);
        Adulto adulto1 = generadorVisitantes.crearAdulto(1, 30);
        Adulto adulto2 = generadorVisitantes.crearAdulto(2, 40);
        Adulto adulto3 = generadorVisitantes.crearAdulto(3, 50);
        Ninio ninio1 = generadorVisitantes.crearNinio(4, 3);
        Ninio ninio2 = generadorVisitantes.crearNinio(6, 11);
        Ninio ninio3 = generadorVisitantes.crearNinio(8, 16);

        adulto1.start();
        adulto2.start();
        adulto3.start();
        ninio1.start();
        ninio2.start();
        ninio3.start();

    }

}
