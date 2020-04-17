package prueba1;

public class Acompaniante extends Visitante {
	
	public Acompaniante(String identificador, int edad, ParqueAcuatico parque) {
		super(identificador, edad, null, parque);
	}
	
	public void run() {
		// no hace nada, los ninios se crian solos
	}

}
