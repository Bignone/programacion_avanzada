package bloque4tarea4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {
	
	private static int EXECUTOR_POOL_SIZE = 10;
	private static int CALCULATION_LIMIT = 10000000;
	private static int NUM_CALCULATION_TASKS = CALCULATION_LIMIT / 100000;


    public static void main(String[] args) {   
    	
    	BigInteger result = new BigInteger("0");
    	List<Future<BigInteger>> partialResults = new ArrayList<Future<BigInteger>>();
    	ExecutorService executorPool = Executors.newFixedThreadPool(EXECUTOR_POOL_SIZE);
        
        List<Step> steps = Step.createSteps(NUM_CALCULATION_TASKS, CALCULATION_LIMIT);
		for (int i = 0; i < NUM_CALCULATION_TASKS; i++) {
			Step step = steps.get(i);
			CalculatorTask calculationTask = new CalculatorTask(step.getFrom(), step.getTo());
			partialResults.add(executorPool.submit(calculationTask));
		}
		
        
        for (Future<BigInteger> futurePartialResult: partialResults) {
        	try {
        		result = result.add(futurePartialResult.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
        }
        
        System.out.println("El resultado final de la suma de los primos es: " + result);

        executorPool.shutdown();
    }

} 