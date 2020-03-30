package ejemplo1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

public class Exposicion {

    int aforo;
    ListaThreads colaEspera, dentro;
    Semaphore semaforo;
    private Lock l = new ReentrantLock();
    public Condition c = l.newCondition();
    private boolean parar = false;
    

    public Exposicion(int aforo, JTextField tfEsperan, JTextField tfDentro) {
        this.aforo = aforo;
        semaforo = new Semaphore(aforo, true);
        colaEspera = new ListaThreads(tfEsperan);
        dentro = new ListaThreads(tfDentro);
    }

    public void detener() {
        if (!parar) {
            parar = true;
            System.out.println("Boton detener");
        }
    }

    public void renuadar() {
        if (parar) {
            parar = false;
            try {
                l.lock();
                c.signalAll();
                System.out.println("Boton reanudar");
            } finally {
                l.unlock();
            }
        }
    }

    public void entrar(Visitante v) {
        colaEspera.meter(v);
        try {
            l.lock();
            while (parar) {
                try {
                    System.out.println("await " + v.getName());
                    c.await(); // espera a que le manden una señal
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Fuera del await " + v.getName());
        } finally {
            l.unlock();
        }      
        try {
            semaforo.acquire();
        } catch (InterruptedException e) {
        }
        colaEspera.sacar(v);
        dentro.meter(v);
    }

    public void salir(Visitante v) {
        dentro.sacar(v);
        semaforo.release();
        try {
            l.lock();
            while (parar) {
                try {
                    System.out.println("await " + v.getName());
                    c.await(); // espera a que le manden una señal
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Fuera del await " + v.getName());
        } finally {
            l.unlock();
        }
    }

    public void mirar(Visitante v) {
        try {
            Thread.sleep(2000 + (int) (3000 * Math.random()));
        } catch (InterruptedException e) {
        }
        try {
            l.lock();
            while (parar) {
                try {
                    System.out.println("await " + v.getName());
                    c.await(); // espera a que le manden una señal
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Fuera del await " + v.getName());
        } finally {
            l.unlock();
        }
    }
}
