package prueba1;

import java.util.List;

public class Visitante extends Thread {
	
	private String identificador;
	private int edad;
	private Visitante acompaniante;
	private ParqueAcuatico parque;
	private List<Actividad> actividades;
	
	public Visitante(String identificador, int edad, Visitante acompaniante, ParqueAcuatico parque) {
		this.identificador = identificador;
		this.edad = edad;
		this.acompaniante = acompaniante;
		this.parque = parque;
	}
	
	public String toString() {
		return this.identificador;
	}
	
	public void run() {
		try {
			sleep((long) ((int)(500) +(400*Math.random())));
			System.out.println("Entrando al parque: " + toString());
			parque.entrar();
			
			int cantidadActividades = (int) (10 * Math.random() + 5);
			System.out.println("Escogiendo actividade del parque: " + cantidadActividades);
			actividades = parque.escogerActividades(cantidadActividades);
			
			for (Actividad actividad: actividades) {
				System.out.println("Entrando a la actividad: " + actividad.toString());
				actividad.entrar();
				boolean dentro = true; // viene de actividad.entrar()
				if (dentro) {
					actividad.disfrutar();
					actividad.salir();
				}
			}
			
			System.out.println("Disfrutando en el parque: " + toString());
			parque.disfrutar();
			
			System.out.println("Saliendo del parque: " + toString());
			parque.salir();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
