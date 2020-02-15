package ejemplo3;

import java.awt.Color;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Paleta
{
    private Color[] colores = new Color[4];
    private boolean[] coloresLibres = {true, true, true, true}; // @Ejercicio3
    private Lock l = new ReentrantLock(); // @Ejercicio3
    
    public Paleta() {
        colores[0] = Color.red;
        colores[1] = Color.blue;
        colores[2] = Color.green;
        colores[3] = Color.yellow;
    }
    
    public Color tomaColor() {  // @Ejercicio3
    	Color color = null;
    	boolean searchColor = true;
    	this.l.lock();
    	try {
	    	while (searchColor) {
	    		int i = (int)(4 * Math.random());  //Elige color al azar
	    		if (this.coloresLibres[i]) {
	    			this.coloresLibres[i] = false;
	    			searchColor = false;
	    			color = colores[i];
	    		}
	    	}
    	}
    	
    	finally {
    		this.l.unlock();
    	}
    	return color;
    }
    
    public Color tomaColorNoThreadSafe() {  // @Ejercicio3
    	Color color = null;
    	boolean searchColor = true;
    	while (searchColor) {
    		int i = (int)(4 * Math.random());  //Elige color al azar
	    		if (this.coloresLibres[i]) {
	    			this.coloresLibres[i] = false;
	    			searchColor = false;
	    			color = colores[i];
	    		}
	    	}
 
    	return color;
    }

	public void liberarColor(Color color) {
		for (int i=0; i < colores.length; i++) {
			if (color == colores[i]) {
				coloresLibres[i] = true;
				break;
			}
		}
		
	}
}
