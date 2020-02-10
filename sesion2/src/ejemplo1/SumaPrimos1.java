// SumaPrimos1 calcula la suma de los primos entre 1 y 10.000.000 en un solo hilo
package ejemplo1;
import java.util.*;

public class SumaPrimos1 {
    public static void main(String[] x){
        
        Resultado suma = new Resultado();
        long t0 = (new Date()).getTime(); //t0=instante de inicio de los cÃ¡lculos
        Calculador p1 = new Calculador(1,10000000,suma);
        p1.start();
        try {
            p1.join();
        } catch (InterruptedException e){}
        long t1 = (new Date()).getTime(); //t1=instante de final de los cÃ¡lculos
        System.out.println("La suma de los nÃºmeros primos hasta 10000000 es: "+suma.getSuma()+" calculado en "+(t1-t0)+" miliseg.");
    }
    
    /**
    // @Ejercicio1: Comparar los resultados de los ejemplos 1 y 2. 
     * ¿Qué ha pasado? Encontrar la explicación a lo que ocurre en ambos programas 
     * El programa 1 realiza las operaciones en un sólo hilo (thread) por lo que las operaciones
     * no se solapan
     * El programa 2 utiliza varios hilos (threads) por lo que los hilos escriben simultáneamente
     * en la variable compartida pisando lo esrito por otros. Por eso el cálculo es incorrecto.
     * Para solucionar esto hace falta poner un lock para que los diferentes hilos no accedan a la variable
     * compartida a la vez.
     */
}

