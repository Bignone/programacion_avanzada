package counter;


public class CounterNoRaceCondition  implements Runnable{
	    private int c = 0;

	    public  void increment() {
	        try {
	            Thread.sleep(10);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        c++;
	    }

	    public  void decrement() {    
	        c--;        
	    }

	    public  int getValue() {
	        return c;
	    }
	    
	    @Override
	    public void run() {
	        synchronized(this){ //@Solution: syncronize block of code
	            // incrementing
	            this.increment();
	            System.out.println("Value for Thread After increment " 
	             + Thread.currentThread().getName() + " " + this.getValue());
	            //decrementing
	            this.decrement();
	            System.out.println("Value for Thread at last " + Thread.currentThread().getName() 
	                + " " + this.getValue());
	        }        
	    }
	}
