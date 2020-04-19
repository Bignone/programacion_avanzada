package prueba1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistroVisitantes {
	
	private Map<String, Visitante> visitantes = new HashMap<>();
	private Map<String, Integer> usuariosEnZonaActual = new ConcurrentHashMap<>();
	private Map<String, Integer> usuariosEnZonaAcumulado = new ConcurrentHashMap<>();
	private int numeroAdultos = 0;
	private int numeroNinios = 0;
	
	public void registrarZonaActividad(String identificador) {
		this.usuariosEnZonaActual.put(identificador, 0);
		this.usuariosEnZonaAcumulado.put(identificador, 0);
	}
	
	public void aniadirVisitanteZonaActividad(String identificador) {
		int cantidadActual = this.usuariosEnZonaActual.get(identificador) + 1;
		this.usuariosEnZonaActual.put(identificador, cantidadActual);
		int cantidadAcumulado = this.usuariosEnZonaAcumulado.get(identificador) + 1;
		this.usuariosEnZonaAcumulado.put(identificador, cantidadAcumulado);
	}
	
	public void eliminarVisitanteZonaActividad(String identificador) {
		int cantidadActual = this.usuariosEnZonaActual.get(identificador) - 1;
		this.usuariosEnZonaActual.put(identificador, cantidadActual);
	}
	
	public Visitante buscarVisitante(String identificador) {
		Visitante visitanteEncontrado = null;
		if (this.visitantes.containsKey(identificador)) {
			visitanteEncontrado = this.visitantes.get(identificador);
		}
		return visitanteEncontrado;
	}

	public Map<String, Visitante> getVisitantes() {
		return visitantes;
	}

	public void aniadirVisitante(Visitante visitante) {
		this.visitantes.put(visitante.getIdentificador(), visitante);
		if (visitante instanceof Ninio) {
			numeroNinios++;
		} else {
			numeroAdultos++;
		}
	}
	
	public int getNumeroAdultos() {
		return numeroAdultos;
	}

	public int getNumeroNinios() {
		return numeroNinios;
	}
	
	public int getVisitantesActualesEnZona(String identificador) {
		return this.usuariosEnZonaActual.get(identificador);
	}
	
	public int getVisitantesAcumuladoEnZona(String identificador) {
		return this.usuariosEnZonaAcumulado.get(identificador);
	}

}
