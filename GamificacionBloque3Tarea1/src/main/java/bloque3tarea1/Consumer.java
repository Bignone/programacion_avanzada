package bloque3tarea1;


public class Consumer extends Thread {
	
	private Thermometer thermometer;
    private String name;
    private double lastValueReaded = Thermometer.DEFAULT_MEMORY_VALUE;

    public Consumer(String name, Thermometer thermometer) {
    	this.name = name;
    	this.thermometer = thermometer;
        
    }
    
    public void goToSleep() throws InterruptedException {
    	sleep((int) (300 + 400 * Math.random()));
    }
    
    public void readFromThermometer() {
    	double valueFromThermometer = thermometer.receiveMessage();
    	if (valueFromThermometer != lastValueReaded) {
    		lastValueReaded = valueFromThermometer;
	    	System.out.println("Consumer " + name + " readed value "+ valueFromThermometer);
    	}
    }
    	
    	

    @Override
    public void run() {
        while (true) {
            try {
            	goToSleep();
            	readFromThermometer();
            } catch(InterruptedException e){ 
            	e.printStackTrace();
            }
             
        }
    }
}


