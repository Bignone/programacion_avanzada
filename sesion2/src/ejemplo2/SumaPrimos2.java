// SumaPrimos2 calcula la suma de los primos entre 1 y 10.000.000
// dividiendo la tarea en cinco hilos
package ejemplo2;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SumaPrimos2 {
    public static void main(String[] x){
    	
    	Lock l = new ReentrantLock(); // @Ejercicio2
        
        Resultado suma = new Resultado(l); // @Ejercicio2: paso del arg lock
        long t0 = (new Date()).getTime(); //t0=instante de inicio de los cálculos
        Calculador p1 = new Calculador(1, 2000000, suma);
        Calculador p2 = new Calculador(2000001, 4000000, suma);
        Calculador p3 = new Calculador(4000001, 6000000, suma);
        Calculador p4 = new Calculador(6000001, 8000000, suma);
        Calculador p5 = new Calculador(8000001, 10000000, suma);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        try {
            p1.join();
            p2.join();
            p3.join();
            p4.join();
            p5.join(); //esperamos a que terminen todos
        } catch (InterruptedException e){}
        long t1 = (new Date()).getTime(); //t1=instante de final de los cálculos
        System.out.println("La suma de los números primos hasta 10000000 es: "+suma.getSuma()+" calculado en "+(t1-t0)+" miliseg.");
    }
}

