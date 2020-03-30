package ejemplo1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */
public class CestaCompra
{
    private String nombreCliente;
    private int[] carroCompra;

    public CestaCompra(String nombreCliente, int[] carroCompra)
    {
        this.nombreCliente = nombreCliente;
        this.carroCompra = carroCompra;
    }

    public String getNombreCliente()
    {
        return nombreCliente;
    }

    public int[] getCarroCompra()
    {
        return carroCompra;
    }
} 