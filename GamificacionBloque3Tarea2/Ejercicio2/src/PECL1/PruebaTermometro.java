/*
 * Programa que lanza cuatro lectores y un escritor.
 * que se comunican a través de un buzón de mensajes.
 * Debe comprobarse que no se pierden los mensajes ni se leen dos veces
 */
package PECL1;

public class PruebaTermometro
{
    public static void main(String[] s)
    {
        Termometro termometro = new Termometro();
        Productor Sensor = new Productor("Sensor",15,termometro);
        Consumidor Display = new Consumidor(termometro, "Display");
        Sensor.start();
        Display.start();
    }
}
