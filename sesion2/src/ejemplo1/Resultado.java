// Un objeto de la clase Resultado es un BigInteger con un método sumar(int) 
package ejemplo1;
import java.math.BigInteger;

public class Resultado {
    
    private BigInteger suma = new BigInteger("0");

    public BigInteger getSuma(){
        return suma;
    }

    public void sumar(int n){
        BigInteger bn = new BigInteger(String.valueOf(n));
        suma=suma.add(bn);
        //System.out.println("sumar " + String.valueOf(n) +" = "+ suma.toString());
    }
}
