package bloque3tarea2;


public class Consumer extends Thread {
	
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
    	System.out.println("Consumer " + name + " readed value: "+ thermometer.receiveMessage());
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


