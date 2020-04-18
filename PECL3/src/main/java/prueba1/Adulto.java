package prueba1;

public class Adulto extends Visitante {
	
	public Adulto(String identificador, int edad, ParqueAcuatico parque) {
		super(identificador, edad, null, parque);
	}
	
	public void run() {
		try {
			sleep((long) ((int)(500) +(400*Math.random())));
			System.out.println("Entrando al parque: " + toString());
			getParque().entrar(this);
			boolean dentroParque = true; // viene de getParque().entrar()
			
			if (dentroParque) {
				int cantidadActividades = (int) (10 * Math.random() + 5);
				System.out.println("Escogiendo actividade del parque: " + cantidadActividades);
				setActividades(getParque().escogerActividades(cantidadActividades));
				
				for (Actividad actividad: getActividades()) {
					System.out.println("Entrando a la actividad: " + actividad.toString());
					boolean dentro = actividad.entrar(this);
					if (dentro) {
						actividad.disfrutar();
						actividad.salir(this);
					}
				}
				
				System.out.println("Disfrutando en el parque: " + toString());
				getParque().disfrutar();
				
				System.out.println("Saliendo del parque: " + toString());
				getParque().salir(this);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
