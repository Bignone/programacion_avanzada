package bloque4tarea2;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Main {
	
	private static int NUM_SUMERS = 5;
	private static int CALCULATION_LIMIT = 10000000;
	
	public static void main(String[] args) {
		
		Result result = new Result();
		FinalCalculator finalCalculator = new FinalCalculator(result);
		final CyclicBarrier endBarrier = new CyclicBarrier(NUM_SUMERS + 1, finalCalculator);
		
		List<Step> steps = Step.createSteps(NUM_SUMERS, CALCULATION_LIMIT);
		Calculator calculator;
		for (int i = 0; i < NUM_SUMERS; i++) {
			Step step = steps.get(i);
			calculator = new Calculator(step.getFrom(), step.getTo(), result, endBarrier);
			calculator.start();
			
		}
		try {
			endBarrier.await();
		} catch (Exception e) { }
	}
}