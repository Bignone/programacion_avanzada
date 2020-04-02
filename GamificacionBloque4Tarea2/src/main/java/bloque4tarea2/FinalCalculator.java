package bloque4tarea2;

import java.math.BigInteger;

public class FinalCalculator extends Thread {   
    
	Result result;
    BigInteger partial = new BigInteger("0");

    public FinalCalculator(Result result) {
        this.result = result;
        System.out.println("Created final calculator " + Thread.currentThread().getName());
    }
    

    @Override
    public void run() {
        result.sumPartials();
		System.out.println("La suma de los primos es: "+result.getValue()+" sumando " + result.getNumPartials() + " resutados parciales");
    }

} 

 