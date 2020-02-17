/*
 * La clase Consumidor define hilos que leen mensajes de un buz�n de mensajes
 * y los muestran por pantalla.
 * El buz�n y el n�mero de mensajes, los reciben como par�metros del constructor
 * antes de terminar.
 * Entre lectura y lectura, esperan un tiempo aleatorio entre 0.5 y 1 seg.
 */
package ejemplo1;

public class Consumidor extends Thread
{
    private Buzon miBuzon;
    private String prefix;
    private Resultado result;

    public Consumidor(String prefix, Buzon miBuzon, Resultado result)
    {
        this.prefix = prefix;
        this.miBuzon = miBuzon;
        this.result = result;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                sleep((int)(500+500*Math.random()));
                int receivedValue = miBuzon.receiveMessage();
                result.addToValue(receivedValue);
                System.out.println(prefix + " recibe el valor " + receivedValue);
                System.out.println(prefix + " muestra el resultado total: " + result.getValue());
                
            } catch(InterruptedException e){
            	System.out.println("Close consumer (Ctr + z): Exiting consumer...");
            }
                
        }
       
    }
}
