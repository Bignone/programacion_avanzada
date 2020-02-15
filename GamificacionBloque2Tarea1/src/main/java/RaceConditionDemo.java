import counter.CounterNoRaceCondition;
import counter.CounterRaceCondition;

public class RaceConditionDemo{
    public static void main(String[] args) {
    	System.out.println("Race contidion example");
    	System.out.println("===============================================");
    	CounterRaceCondition counter = new CounterRaceCondition();
        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");
        t1.start();
        t2.start();
        t3.start();
        System.out.println("===============================================");
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n");
        System.out.println("Race contidion fixed example");
    	System.out.println("===============================================");
    	CounterNoRaceCondition counter2 = new CounterNoRaceCondition();
        Thread t4 = new Thread(counter2, "Thread-1");
        Thread t5 = new Thread(counter2, "Thread-2");
        Thread t6 = new Thread(counter2, "Thread-3");
        t4.start();
        t5.start();
        t6.start();
        System.out.println("===============================================");
        
    }    
}