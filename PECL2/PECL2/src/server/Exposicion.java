package server;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;


/**
 * Clase reutilizada y completada de la sesión 5.
 */
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

    /**
     * Función auxiliar que cambia el estado de la variable parar. Sólo se usa
     * para el botón de "ProgPrincipal"
     */
    public void detener() {
        if (!parar) {
            parar = true;
            System.out.println("Boton detener");
        }
    }

    /**
     * Función auxiliar que cambia el estado de la variable parar. Sólo se usa
     * para el botón de "ProgPrincipal". En este caso, hemos usado locks para
     * avisar cuando ya han dado al botón de reanudar.
     */
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

    /**
     * A diferencia de la Sesión 5, lo que he añadio es un lock para que tome el
     * recurso cuando entre, y la condition para cuando se le de a reanudar. Al
     * final, el semáforo reduce una posición de su capacidad, para evitar que
     * exceda la capacidad.
     *
     * @param v
     */
    public void entrar(Visitante v) {
        colaEspera.meter(v);
        try {
            l.lock();
            while (parar) {
                try {
                    System.out.println("await " + v.getName());
                    c.await(); // espera a que le manden una seÃ±al
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

    /**
     * A diferencia de la Sesión 5, lo que he añadio es el release para indicar
     * que hay una plaza disponible nueva. También el lock para que tome el
     * recuso, por si hay que bloquear.
     *
     * @param v
     */
    public void salir(Visitante v) {
        dentro.sacar(v);
        semaforo.release();
        try {
            l.lock();
            while (parar) {
                try {
                    System.out.println("await " + v.getName());
                    c.await(); // espera a que le manden una seÃ±al
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Fuera del await " + v.getName());
        } finally {
            l.unlock();
        }
    }

    /**
     * A diferencia de la Sesión 5, lo que he añadio es el lock para que tome el
     * recuso, por si hay que bloquear.
     *
     * @param v
     */
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
                    c.await(); // espera a que le manden una seÃ±al
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
