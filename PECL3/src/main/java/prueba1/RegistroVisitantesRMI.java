package prueba1;

import java.rmi.Remote;

public interface RegistroVisitantesRMI extends Remote {
	
	public Visitante buscarVisitante(String identificador);
	
	public int getNumeroAdultos();

	public int getNumeroNinios();
	
	public int getVisitantesActualesEnZona(String identificador);
	
	public int getVisitantesAcumuladoEnZona(String identificador);

}
