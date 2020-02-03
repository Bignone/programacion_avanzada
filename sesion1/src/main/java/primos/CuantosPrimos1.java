package primos;

import java.util.*;

public class CuantosPrimos1 {

	public static void main(String[] x){
        long t0 = (new Date()).getTime(); //t0=instante de inicio de los cálculos
        Primos p1 = new Primos(1,2000000);
        Primos p2 = new Primos(2000001,4000000);
        Primos p3 = new Primos(4000001,6000000);
        Primos p4 = new Primos(6000001,8000000);
        Primos p5 = new Primos(8000001,10000000);

        p1.calcular();
        p2.calcular();
        p3.calcular();
        p4.calcular();
        p5.calcular();

        int n = p1.cuantos() + p2.cuantos() + p3.cuantos() + p4.cuantos() + p5.cuantos();
        long tExec = p1.getExecutionTime() + p2.getExecutionTime() + p3.getExecutionTime() + p4.getExecutionTime() + p5.getExecutionTime(); // @Ejercicio1
        System.out.println("Suma de los tiempos de ejecución: " + tExec); // @Ejercicio1
        
        long t1 = (new Date()).getTime(); //t1=instante de final de los cálculos
        System.out.println("Número de primos menores que 10.000.000: "+ n +" calculado en "+ (t1-t0) +" miliseg.");

	}

}
