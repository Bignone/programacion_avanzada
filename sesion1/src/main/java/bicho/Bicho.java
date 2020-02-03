package bicho;

import java.util.Date;
import java.util.UUID;

public class Bicho extends Thread { //@Ejercicio7
	
	private int generation;
	private String name;
	
	public Bicho(int generation, String name) {
		
		this.generation = generation;
		this.name = name;
	}
	
	public String randomName() {
		return UUID.randomUUID().toString();
	}
	
	public void live() throws InterruptedException {
		String childName = randomName();
		
		long t0 = (new Date()).getTime();
		Thread.sleep((long) (Math.random()+500));
		
		if (this.generation <= 5) {
			System.out.println("NACE " + childName + ", generacion: " + generation);
			Bicho child = new Bicho(this.generation + 1, childName);
			child.start();
			child.join();
		}
		
		long t1 = (new Date()).getTime();
		
		if (this.generation < 5) {
			System.out.println("MUERE " + childName + ", generacion: " + generation + ", life: " + (t1-t0));
		}
	}
	
	public void run() {
		try {
			this.live();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
