package bloque3tarea4;

public class Producer extends Thread {

    private String name;
    private int numMessages;
    private Thermometer thermometer;

    public Producer(String name, int numMessages, Thermometer thermometer) {
        this.name = name;
        this.numMessages = numMessages;
        this.thermometer = thermometer;
    }
    
    private double generateRandomNumber() {
    	return Math.random() * 30;
    }
    
    public void goToSleep() throws InterruptedException {
    	sleep((int) (300 + 400 * Math.random()));
    }
    
    public void putIntoThermometer() {
    	thermometer.sendMessage(generateRandomNumber(), name);
    }
     
    public void run() {
    	for (int i = 1; i <= numMessages; i++) {
    		try {
    			goToSleep();
    			putIntoThermometer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    		
    	}
    }


}
