package primos;

import java.util.Date;

public class Primos {
    private int x,y,n=0;
    private long executionTime; // @Ejercicio1

    public Primos(int x, int y){
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

    public void calcular(){
    	long t0 = (new Date()).getTime();// @Ejercicio1
        for (int i=x; i<=y; i++)
        {
            if(esPrimo(i))
            {
                n++;
            }
        }
        long t1 = (new Date()).getTime();// @Ejercicio1
        this.executionTime = (t1-t0);// @Ejercicio1
        System.out.println("Tiempo en calcular los primos: "+ (t1-t0) +" miliseg.");
    }

    public int cuantos(){
        return n;
    }
    
    public long getExecutionTime() { // @Ejercicio1
    	return this.executionTime;
    }
}
