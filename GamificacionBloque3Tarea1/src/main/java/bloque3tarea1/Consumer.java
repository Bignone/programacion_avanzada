package bloque3tarea1;


public class Consumer extends Thread {
	
    private static int READ_LIMIT_NUM = 50; // to not run for ever
    private int numreaded = 0;
	private Thermometer thermometer;
    private String name;

    public Consumer(String name, Thermometer thermometer) {
    	this.name = name;
    	this.thermometer = thermometer;
        
    }
    
    public void goToSleep() throws InterruptedException {
    	sleep((int) (300 + 400 * Math.random()));
    }
    
    public void readFromThermometer() {
    	System.out.println("Consumer " + name + " readed value (" + numreaded + " of limit "  + READ_LIMIT_NUM + "): "+ thermometer.receiveMessage());
    	numreaded++;
    }
    	
    	

    @Override
    public void run() {
        while (numreaded < READ_LIMIT_NUM) {
            try {
            	goToSleep();
            	readFromThermometer();
            } catch(InterruptedException e){ 
            	e.printStackTrace();
            }
             
        }
    }
}


