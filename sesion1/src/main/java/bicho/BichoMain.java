package bicho;


public class BichoMain { //@Ejercicio7

	public static void main(String[] args) {

        Bicho adan = new Bicho(1, "ADAN");
        
        adan.start();

        try{
        	adan.join();
        } catch (InterruptedException e){
        	
        }


	}

}
