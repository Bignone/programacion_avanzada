package bloque3tarea1;

public class Main {
	
	private static int NUM_PRODUCERS_MESSAGES = 15;

	public static void main(String[] args) {
		Thermometer thermometer = new Thermometer();
  
        Producer sensor = new Producer("Sensor", NUM_PRODUCERS_MESSAGES, thermometer);
        sensor.start();
        
        Consumer display = new Consumer("Reader", thermometer);
        display.start();
        
        }
}
