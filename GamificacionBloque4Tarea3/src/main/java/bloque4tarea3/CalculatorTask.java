package bloque4tarea3;

import java.math.BigInteger;

public class CalculatorTask extends Thread {   
    
	int from, to;
    BigInteger partial = new BigInteger("0");

    public CalculatorTask(int from, int to) {
    	this.from = from;
        this.to = to;
        System.out.println("Created calculator " + currentThread().getName() + " from " + from + " to " + to);
    }
    

    @Override
    public void run() {
        for (int i=from; i<=to; i++) {

		    if(isPrime(i)) {
		    	System.out.println("calculator " + currentThread().getName() + " found prime " + i);
		    }
		}
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

} 

 