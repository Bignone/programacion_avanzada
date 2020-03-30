/*
 * La clase Productor define hilos que env�an mensajes a un buz�n de mensajes.
 * Los mensajes constan de un prefijo String y un sufijo que es un entero del 1 al 5
 * El prefijo, el n�mero de mensajes a escribir y el buz�n donde hacerlo,
 *  se reciben como par�metros en el constructor.
 * Entre mensaje y mensaje, esperan un tiempo aleatorio entre 0.5 y 1 seg.
 */

package ejemplo1;

public class Productor extends Thread
{
    private String prefijo;
    private int numMensajes;
    private Buzon miBuzon;

    public Productor(String prefijo, int n, Buzon buzon)
    {
        this.prefijo=prefijo;
        numMensajes=n;
        miBuzon=buzon;
    }

    public void run()
    {
        int sentMessages = 0;
    	while (sentMessages < numMensajes)
        {
            try
            {
                sleep((int)(500+500*Math.random()));
            
	            int randomInt = (int) (Math.random() * 20);
	            miBuzon.sendMessage(randomInt);
	            System.out.println(prefijo + " introduce el valor " + randomInt + " ("+(sentMessages+1)+"/"+numMensajes+")");
	            sentMessages++;
			} catch(InterruptedException e){ 
				e.printStackTrace();
			}
        }
    }


}
