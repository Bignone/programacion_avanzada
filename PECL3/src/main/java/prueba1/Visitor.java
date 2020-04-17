package prueba1;

import java.util.ArrayList;
import java.util.List;

public class Visitor extends Thread {
	
	private String name;
	private int edad;
	Tumbonas tumbonas;
	
	public Visitor(String name, int edad, Tumbonas tumbonas) {
		this.name = name;
		this.edad = edad;
		this.tumbonas = tumbonas;
	}
	
	public String getname() {
		return this.name;
	}
	

	public int getEdad() {
		return edad;
	}
	
	
	
	public void run()
    {
    	
        try
        {
            sleep((long) ((int)(2000) +(1500*Math.random())));
        } catch (InterruptedException e){ 
        	
        }
        tumbonas.entrar(this);
		tumbonas.disfrutar(this);
		tumbonas.salir(this); 
        
    }

}
