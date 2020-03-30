/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo1;

/**
 *
 * @author Administrador
 */
public class TareaCajera extends Thread
{
    private CestaCompra cesta;

    public TareaCajera(CestaCompra cesta)
    {
        this.cesta = cesta;
    }

    public void run()
    {
        System.out.println("LA CAJERA " + currentThread().getName() + " COMIENZA A PROCESAR LA COMPRA DEL " + this.cesta.getNombreCliente());

        for (int i = 0; i < this.cesta.getCarroCompra().length; i++)
        {
            // Se procesa el pedido
            this.esperarXsegundos(cesta.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1) + " del " + this.cesta.getNombreCliente());
        }
        System.out.println("LA CAJERA " + currentThread().getName() + " HA TERMINADO DE PROCESAR LA COMPRA DEL " + this.cesta.getNombreCliente());
    }

    private void esperarXsegundos(int segundos)
    {
        try
        {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex)

        {
            Thread.currentThread().interrupt();
        }
    }
}