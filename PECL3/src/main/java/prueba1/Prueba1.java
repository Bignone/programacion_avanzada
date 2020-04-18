package prueba1;

public class Prueba1 {

	public static void main(String[] args) {
		ParqueAcuatico parque = new ParqueAcuatico();
		GeneradorVisitantes generadorVisitantes = new GeneradorVisitantes(parque);
		generadorVisitantes.start();
		
	}

}
