package bloque4tarea1;

public class Calculator extends Thread {   
    
	int from, to;
    Result result;

    public Calculator(int from, int to, Result result) {
    	this.from = from;
        this.to = to;
        this.result = result;
        System.out.println("Created calculator from " + from + " to " + to);
    }
    

    @Override
    public void run() {

        for (int i=from; i<=to; i++) {

            if(isPrime(i)) {
                result.increment(i);
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

 