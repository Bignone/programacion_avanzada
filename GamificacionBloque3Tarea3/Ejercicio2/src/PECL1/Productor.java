/*
 * La clase Productor define hilos que envían mensajes a un buzón de mensajes.
 * Los mensajes constan de un prefijo String y un sufijo que es un entero del 1 al 5
 * El prefijo, el número de mensajes a escribir y el buzón donde hacerlo,
 *  se reciben como parámetros en el constructor.
 * Entre mensaje y mensaje, esperan un tiempo aleatorio entre 0.5 y 1 seg.
 */
package PECL1;

public class Productor extends Thread {

    private String prefijo;
    private int numMensajes;
    private Termometro termometro;

    public Productor(String prefijo, int n, Termometro termometro) {
        this.prefijo = prefijo;
        numMensajes = n;
        this.termometro = termometro;
    }
    
    private double generateRandomNumber() {
    	return Math.random() * 30;
    }
    
    public void run() {
    	double num;
    	for (int i = 1; i <= numMensajes; i++) {
    		try {
				sleep((int) (200 + 600 * Math.random()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		termometro.sendMessage(generateRandomNumber(), prefijo);
    		
    	}
    }

//    public void run() {
//        int num = 0;
//        String numS = "";
//        for (int i = 1; i <= numMensajes; i++) {
//            try {
//                num = (int) (Math.random() * 30);
//                
//                numS = num + "";
//                sleep((int) (200 + 600 * Math.random()));
//            } catch (InterruptedException e) {
//            }
//            termometro.enviaMensaje(numS, prefijo);
//        }
//    }

}
