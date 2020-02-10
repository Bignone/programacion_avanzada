//Calculador sirve para sumar los números primos que hay en un intervalo (x,y)
package ejemplo1;

public class Calculador extends Thread {
    
    int desde, hasta;
    Resultado r;
    
    public Calculador(int x, int y, Resultado r){
        desde=x;
        hasta=y;
        this.r=r;
    }
    
    public void run(){
        for (int i=desde; i<=hasta; i++)
        {
            if(esPrimo(i))
            {
                r.sumar(i);
            }
        }
    }
    
    private boolean esPrimo(int n){
        int raiz = (int) Math.sqrt((double) n);
        boolean primo = true;
        int i=2;
        while(primo && i<=raiz)
        {
            if (n % i == 0)
                primo=false;
            i++;
        }
        return primo;
    }
}
