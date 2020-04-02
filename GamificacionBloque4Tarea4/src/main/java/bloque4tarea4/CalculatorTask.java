package bloque4tarea4;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public class CalculatorTask implements Callable<BigInteger> {   
    
	int from, to;

    public CalculatorTask(int from, int to) {
    	this.from = from;
        this.to = to;
    }

    private boolean isPrime(int n) {
        int raiz = (int) Math.sqrt((double) n);
        boolean primo = true;
        int i=2;

        while(primo && i<=raiz) {
            if (n % i == 0)
                primo=false;
            i++;
        }
        return primo;
    }


	@Override
	public BigInteger call() throws Exception {
		BigInteger partial = new BigInteger("0");
		System.out.println("calculating from " + from + " to " + to);
		for (int i=from; i<=to; i++) {

            if(isPrime(i)) {
            	partial = partial.add(BigInteger.valueOf(i));
            }
        }
		System.out.println("calculated from " + from + " to " + to + " = (future) " + partial);
		return partial;
	}

} 

 