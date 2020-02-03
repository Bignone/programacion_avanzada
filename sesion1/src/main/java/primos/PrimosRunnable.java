package primos;

import java.util.Date;

public class PrimosRunnable implements Runnable { //@Ejercicio4
    private int x,y,n=0;
    private long executionTime; // @Ejercicio2

    public PrimosRunnable(int x, int y){
        this.x=x;
        this.y=y;
    }

    private boolean esPrimo(int n){
        int raiz = (int) Math.sqrt((double) n);
        boolean primo = true;
        int i=2;
        while(primo && i<=raiz)
        {
            if (n % i == 0)
                primo=false;
            i++;
        }
        return primo;
    }

    public void run(){
    	long t0 = (new Date()).getTime();// @Ejercicio2
        for (int i=x; i<=y; i++)
        {
            if(esPrimo(i))
            {
                n++;
            }
        }
        long t1 = (new Date()).getTime();// @Ejercicio2
        this.executionTime = (t1-t0);// @Ejercicio2
    }

    public int cuantos(){
        return n;
    }
    
    public long getExecutionTime() { // @Ejercicio2
    	return this.executionTime;
    }

}
