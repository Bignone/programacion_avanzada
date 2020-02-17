/*
 * Programa que lanza cuatro lectores y un escritor.
 * que se comunican a través de un buzón de mensajes.
 * Debe comprobarse que no se pierden los mensajes ni se leen dos veces
 */
package ejemplo1;

public class PruebaBuzon
{
    public static void main(String[] s)
    {
        Buzon buzonX = new Buzon();
        Resultado result = new Resultado();
        Productor producerA = new Productor("A", 80, buzonX);
        Productor producerB = new Productor("B", 80, buzonX);
        Productor producerC = new Productor("C", 80, buzonX);
        Consumidor consumerJose = new Consumidor("Jose", buzonX, result);
        Consumidor consumerAna = new Consumidor("Ana", buzonX, result);
        Consumidor consumerMaria = new Consumidor("Maria", buzonX, result);
        producerA.start();
        producerB.start();
        producerC.start();
        consumerJose.start();
        consumerAna.start();
        consumerMaria.start();
    }
}
