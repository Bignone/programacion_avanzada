package bicho;

import java.util.Date;

public class BichoMain { //@Ejercicio7

	public static void main(String[] args) {

        long t0 = (new Date()).getTime();
        Bicho adan = new Bicho(1, "ADAN");
        
        System.out.println("NACE ADAN, generacion: 0");
        adan.start();

        try{
        	adan.join();
        } catch (InterruptedException e){
        	
        }
        long t1 = (new Date()).getTime();
        System.out.println("MUERE ADAN, generacion: 0, life: " + (t1-t0));


	}

}
