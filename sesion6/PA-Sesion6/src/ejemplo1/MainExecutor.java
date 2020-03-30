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
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainExecutor
{
    private static final int numCajeras = 2;
    private static final int numClientes = 8;
    private static final int numProductos = 5;

    public static void main(String[] args)
    {       
        ArrayList<CestaCompra> cestas = new ArrayList<CestaCompra>();
        for(int i=0; i<numClientes; i++)
        {
            int[] productos = new int[numProductos];
            for(int j=0; j<numProductos; j++)
            {
                productos[j]=(int) (Math.random()*3+1); //Valores entre 1 y 4
            }
            cestas.add(new CestaCompra("Cliente "+(i+1), productos));
        }
        /*cestas.add(new CestaCompra("Cliente 1", new int[] { 2, 2, 1, 5, 2 }));
        cestas.add(new CestaCompra("Cliente 2", new int[] { 1, 1, 5, 1, 1 }));
        cestas.add(new CestaCompra("Cliente 3", new int[] { 5, 3, 1, 5, 2 }));
        cestas.add(new CestaCompra("Cliente 4", new int[] { 2, 4, 3, 2, 5 }));
        cestas.add(new CestaCompra("Cliente 5", new int[] { 1, 3, 2, 2, 3 }));
        cestas.add(new CestaCompra("Cliente 6", new int[] { 4, 2, 1, 3, 1 }));
        cestas.add(new CestaCompra("Cliente 7", new int[] { 3, 3, 2, 4, 7 }));
        cestas.add(new CestaCompra("Cliente 8", new int[] { 6, 1, 3, 1, 3 }));*/
       
        ExecutorService executor = Executors.newFixedThreadPool(numCajeras);

        for (int i = 0; i < cestas.size(); i++)
        {
            TareaCajera tarea = new TareaCajera(cestas.get(i));
            executor.execute(tarea);
        }

        executor.shutdown();  // Voy terminando el pool

        try {
            // Espero a que terminen de ejecutarse todas las tareas
            // para pasar a las siguientes instrucciones
            executor.awaitTermination(24, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
            
        }
    }
} 