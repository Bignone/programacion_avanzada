package ejercicio2;

public class ProgPrincipal
{
    public static void main (String[] a)
    {
        for (int i=0; i<10; i++)
        {
            Tenista x = new Tenista(i);
            x.start();
        }
    }
}
