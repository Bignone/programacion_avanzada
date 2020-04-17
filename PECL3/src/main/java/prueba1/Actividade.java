package prueba1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Actividade {
	
	String nombre;
	ArrayBlockingQueue<Visitor> colaEspera = new ArrayBlockingQueue<>(100, true);
	ArrayBlockingQueue<Visitor> espacioDentro = new ArrayBlockingQueue<>(10, true);
	
	public Actividade(String nombre) {
		this.nombre = nombre;
	}
	
	public ArrayBlockingQueue<Visitor> getColaEspera() {
		return colaEspera;
	}
	
	public ArrayBlockingQueue<Visitor> getEspacioDentro() {
		return espacioDentro;
	}
	
	public BlockingQueue<Visitor> getEspacioParaVisitor(Visitor visitor) {
		BlockingQueue<Visitor> espacio = null;
		if (visitor instanceof Visitor) {
			espacio = this.espacioDentro;	
		}
		return espacio;
	}
	
	public void entrar(Visitor visitor) throws InterruptedException {
		colaEspera.add(visitor);
		visitor.wait();
	}
	
	public void disfrutar(Visitor visitor) throws InterruptedException {
			visitor.sleep(5000);
	}
	
	public void salir(Visitor visitor) {
		espacioDentro.remove(visitor);
		
		
	}

}
