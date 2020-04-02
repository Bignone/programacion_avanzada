package bloque4tarea2;

import java.math.BigInteger;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Calculator extends Thread {   
    
	private CyclicBarrier endBarrier;
	int from, to;
	Result result;
    BigInteger partial = new BigInteger("0");

    public Calculator(int from, int to, Result result, CyclicBarrier endBarrier) {
    	this.from = from;
        this.to = to;
        this.result = result;
        this.endBarrier = endBarrier;
        System.out.println("Created calculator " + Thread.currentThread().getName() + " from " + from + " to " + to);
    }
    

    @Override
    public void run() {
        try {
        	for (int i=from; i<=to; i++) {

                if(isPrime(i)) {
                	partial = partial.add(BigInteger.valueOf(i));
                }
            }
        	System.out.println("calculator " + Thread.currentThread().getName() + " add partial result " + partial);
            result.addPartialResult(partial);
			endBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
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

 