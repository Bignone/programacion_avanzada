package ejemplo1;
import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

public class Exposicion
{
    int aforo;
    ListaThreads colaEspera, dentro;
    Semaphore semaforo;
    
    public Exposicion(int aforo, JTextField tfEsperan, JTextField tfDentro)
    {
        this.aforo=aforo;
        semaforo=new Semaphore(aforo,true);
        colaEspera=new ListaThreads(tfEsperan);
        dentro=new ListaThreads(tfDentro);
    }
    
    public void entrar(Visitante v)
    {
        colaEspera.meter(v);
        try
        {
            semaforo.acquire();
        } catch(InterruptedException e){ }
        colaEspera.sacar(v);
        dentro.meter(v);
    }

    public void salir(Visitante v)
    {
        dentro.sacar(v);
        semaforo.release();
    }
    
    public void mirar(Visitante v)
    {
        try
        {
            Thread.sleep(2000+(int)(3000*Math.random()));
        } catch (InterruptedException e){ }
    }
}
