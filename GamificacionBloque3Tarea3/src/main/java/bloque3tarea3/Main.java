package bloque3tarea3;

public class Main {
	
	private static int NUM_PRODUCERS_CREATED = 5;
	private static int NUM_PRODUCERS_MESSAGES = 15;
	private static int NUM_READERS_CREATED = 5;

	public static void main(String[] args) {
		Thermometer thermometer = new Thermometer();
  
		// More producers -> more fun
        Producer sensor;
        for (int i = 0; i < NUM_PRODUCERS_CREATED; i++)
        {
        	sensor = new Producer("Sensor" + i, NUM_PRODUCERS_MESSAGES, thermometer);
        	sensor.start();
        }
        
        // More consumers -> even more fun!
        Consumer display;
        for (int i = 0; i < NUM_READERS_CREATED; i++)
        {
        	display= new Consumer("Reader" + i, thermometer);
        	display.start();
        }
        
        
        }
}
