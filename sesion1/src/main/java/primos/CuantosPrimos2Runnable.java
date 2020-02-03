package primos;

import java.util.Date;

public class CuantosPrimos2Runnable { //@Ejercicio4

	public static void main(String[] x){

        long t0 = (new Date()).getTime(); //t0=instante de inicio de los cálculos
        Thread p1 = new Thread(new PrimosRunnable(1,2000000));
        Thread p2 = new Thread(new PrimosRunnable(2000001,4000000));
        Thread p3 = new Thread(new PrimosRunnable(4000001,6000000));
        Thread p4 = new Thread(new PrimosRunnable(6000001,8000000));
        Thread p5 = new Thread(new PrimosRunnable(8000001,10000000));

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

        try{
         p1.join(); p2.join(); p3.join(); p4.join(); p5.join(); //esperamos a que terminen todos

        } catch (InterruptedException e){}

        long t1 = (new Date()).getTime(); //t1=instante de final de los cálculos
        System.out.println("Número de primos menores que 10.000.000: calculado en "+ (t1-t0) +" miliseg.");

 }

}
