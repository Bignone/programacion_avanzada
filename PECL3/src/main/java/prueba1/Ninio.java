package prueba1;

public class Ninio extends Visitante {
	
	public Ninio(String identificador, int edad, Acompaniante acompaniante, ParqueAcuatico parque) {
		super(identificador, edad, acompaniante, parque);
	}
	
	public void run() {
		try {
			sleep((long) ((int)(500) +(400*Math.random())));
			System.out.println("Entrando al parque: " + toString());
			getParque().entrar(this);
			
			int cantidadActividades = (int) (10 * Math.random() + 5);
			System.out.println("Escogiendo actividade del parque: " + cantidadActividades);
			setActividades(getParque().escogerActividades(cantidadActividades));
			
			for (Actividad actividad: getActividades()) {
				System.out.println("Entrando a la actividad "+getIdentificador()+": " + actividad.toString());
				boolean dentro = actividad.entrar(this);				
				if (dentro) {
					actividad.disfrutar(this);
					actividad.salir(this);
				}
			}
			
			System.out.println("Disfrutando en el parque: " + toString());
			getParque().disfrutar();
			
			System.out.println("Saliendo del parque: " + toString());
			getParque().salir(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
