// Un objeto de la clase Resultado es un BigInteger con un método sumar(int) 
package ejemplo2;
import java.math.BigInteger;
import java.util.concurrent.locks.Lock;

public class Resultado {
	
	private Lock l;// @Ejercicio2
	
	public Resultado (Lock l) { // @Ejercicio2: Constructor paso de lock
		this.l = l;
	}
    
    private BigInteger suma = new BigInteger("0");

    public BigInteger getSuma(){
        return suma;
    }

    public void sumar(int n){
    	this.l.lock(); // @Ejercicio2
        try {
	        BigInteger bn = new BigInteger(String.valueOf(n));
	        suma=suma.add(bn);
        //System.out.println("sumar " + String.valueOf(n) +" = "+ suma.toString());
        } finally {
        	this.l.unlock(); // @Ejercicio2
        }
    }
}
