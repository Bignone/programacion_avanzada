package prueba1;

public class GeneradorVisitantes extends Thread {
	
	private static int NUM_VISITANTES = 100;
	private ParqueAcuatico parque;
	
	public GeneradorVisitantes(ParqueAcuatico parque) {
		this.parque = parque;
	}
	
	private boolean necesitaAcompanniante(int edad) {
		return edad < 18;
	}
	
	public Acompaniante crearAcompannante(int idHijo, int contador) {
		int edad = (int) (32 * Math.random() + 18);
		String identificador = "ID" + contador + "-" + edad + "-" + idHijo;
		return new Acompaniante(identificador, edad, parque);
	}
	
	public Adulto crearAdulto(int contador, int edad) {
		String identificador = "ID" + contador + "-" + edad;
		return new Adulto(identificador, edad, parque);
	}
	
	public Ninio crearNinio(int contador, int edad) {
		String identificador = "ID" + contador + "-" + edad + "-" + (contador+1);
		Acompaniante acompaniante = crearAcompannante(contador, contador+1);
		return new Ninio(identificador, edad, acompaniante, parque);
	}
	
	public void run() {
		int contador = 1;
		
		try {
			while (contador < NUM_VISITANTES) {
			
				sleep((long) ((int)(500) +(400*Math.random())));
				
				int edad = (int) (49 * Math.random() +1);
				String identificador;
				Visitante visitante;
				Acompaniante acompaniante = null;
				if (necesitaAcompanniante(edad)) {
					identificador = "ID" + contador + "-" + edad + "-" + (contador+1);
					acompaniante = crearAcompannante(contador, contador+1);
					contador++;
					visitante = new Ninio(identificador, edad, acompaniante, parque);
				} else {
					identificador = "ID" + contador + "-" + edad;
					visitante = new Adulto(identificador, edad, parque);
				}
				
				System.out.println("Starting visitante: " + visitante.toString());
				visitante.start();
				if (acompaniante != null) {
					System.out.println("Starting acompaniante: " + acompaniante.toString());
					acompaniante.start();
					//break;
				}
				contador++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
	}

}
