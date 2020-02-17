package ejemplo2;
import java.awt.Color;
import javax.swing.JButton;

// Pintor es un hilo que hace que el botón cambie de color siguiendo la secuencia:
// se pinta de negro durante un tiempo aleatorio que varía entre un mínimo de 0.5 y un máximo de 2 segundos.
// A continuación obtiene un color al azar entre rojo, verde, azul y amarillo y pinta con ese color
// durante un tiempo aleatorio entre 1 y 3 segundos, y vuelve a empezar el ciclo.
public class Pintor extends Thread
{
    private JButton b;
    private Paleta p;
    private Color negro = Color.black;
    private Paso paso;
    
    public Pintor(JButton b, Paleta p, Paso paso)
    {
        this.b=b;
        this.p=p;
        this.paso=paso;
    }
    
    public void run()
    {
        while (true)
        {
            b.setBackground(negro);             //Pinta el botón de negro
            try
            {
                sleep(500 + (int)(1500*Math.random()));  //Espera entre 0.5 y 2 seg.
            } catch (InterruptedException e){}
            paso.mirar();              //Mira a ver si tiene que detenerse
            Color c=p.tomaColor();     //Toma un color cualquiera de la paleta
            b.setBackground(c);        //y pinta con él el botón
            try
            {
                sleep(1000 + (int)(2000*Math.random()));  //Espera entre 1 y 3 seg.
            } catch (InterruptedException e){}
            paso.mirar();             //Mira a ver si tiene que detenerse
            p.dejaColor(c);           // Libera el color utilizado
        }
    }
}