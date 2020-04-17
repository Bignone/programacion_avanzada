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
	
	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Visitante getAcompaniante() {
		return acompaniante;
	}

	public void setAcompaniante(Visitante acompaniante) {
		this.acompaniante = acompaniante;
	}

	public ParqueAcuatico getParque() {
		return parque;
	}

	public void setParque(ParqueAcuatico parque) {
		this.parque = parque;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

}
