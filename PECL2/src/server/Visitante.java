package server;


/**
 * Clase Visitante de la exposición
 * @author Eduardo Bustos Miranda & César Munuera Pérez
 */
public class Visitante extends Thread
{
    Exposicion expo;
    
    public Visitante(int id, Exposicion expo)
    {
        super.setName(String.valueOf(id));
        this.expo=expo;
    }
    
    public void run()
    {
        try
        {
            sleep((long) ((int)(2000) +(1500*Math.random())));
        } catch (InterruptedException e){ }
        expo.entrar(this); //Entra en la exposición, si hay hueco; y sino espera en la cola
        expo.mirar(this); //Está un tiempo dentro de la exposición
        expo.salir(this); //Sale de la exposición
    }
}
