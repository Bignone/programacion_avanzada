package prueba1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;


public class ControlCliente {
	
	private static final String REGISTRO_VISITANTES = "RegistroVisitantes";
	private static final String IP_REMOTA = "localhost";
	RegistroVisitantes registroRemoto;
	Registry registry;
	
	public ControlCliente() throws RemoteException, NotBoundException {
		this.registry = LocateRegistry.getRegistry(IP_REMOTA);
		this.registroRemoto = (RegistroVisitantes) registry.lookup(REGISTRO_VISITANTES);
		
	}
	
	public ArrayList<String> controlUbicacion(String identificadorUsuario) {
		// returns [id_usuario, ubicacion, actividades]
		ArrayList<String> resultado = new ArrayList<>();
		Visitante visitante = registroRemoto.buscarVisitante(identificadorUsuario);
		resultado.add(visitante.getIdentificador());
		resultado.add(visitante.getActividadActual());
		resultado.add(String.valueOf(visitante.getConteoActividades()));
		return resultado;
	}
	
	public int controlMenores() {
		return registroRemoto.getNumeroNinios();
	}
	
	public ArrayList<String> controlToboganes() {
		// returns [tobogan_a, tobogan_b, tobogan_c]
		ArrayList<String> resultado = new ArrayList<>();
		resultado.add(registroRemoto.getIdentificadoresUsuariosEnActividad("ActividadTobogan", "-zonaActividadA").get(0));
		resultado.add(registroRemoto.getIdentificadoresUsuariosEnActividad("ActividadTobogan", "-zonaActividadB").get(0));
		resultado.add(registroRemoto.getIdentificadoresUsuariosEnActividad("ActividadTobogan", "-zonaActividadC").get(0));
		return resultado;
	}
	
	public ArrayList<String> controlAforo() {
		// returns [vestuarios, piscina_olas, piscina_ninios, tumbonas, toboganes, piscina_grande]
		ArrayList<String> resultado = new ArrayList<>();
		resultado.add(String.valueOf(registroRemoto.getVisitantesActualesEnZona("ActividadVestuario")));
		resultado.add(String.valueOf(registroRemoto.getVisitantesActualesEnZona("ActividadPiscinaOlas")));
		resultado.add(String.valueOf(registroRemoto.getVisitantesActualesEnZona("ActividadPiscinaNinios")));
		resultado.add(String.valueOf(registroRemoto.getVisitantesActualesEnZona("ActividadTumbonas")));
		resultado.add(String.valueOf(registroRemoto.getVisitantesActualesEnZona("ActividadTobogan")));
		resultado.add(String.valueOf(registroRemoto.getVisitantesActualesEnZona("ActividadPscinaGrande")));
		return resultado;
	}

}
