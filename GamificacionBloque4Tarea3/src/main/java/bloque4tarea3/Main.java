package bloque4tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
	
	private static int EXECUTOR_POOL_SIZE = 10;
	private static int CALCULATION_LIMIT = 10000000;
	private static int NUM_CALCULATION_TASKS = CALCULATION_LIMIT / 100000;


    public static void main(String[] args) {       
        
        ArrayList<CalculatorTask> calculationTasks = new ArrayList<>();
        List<Step> steps = Step.createSteps(NUM_CALCULATION_TASKS, CALCULATION_LIMIT);
		for (int i = 0; i < NUM_CALCULATION_TASKS; i++) {
			Step step = steps.get(i);
			calculationTasks.add(new CalculatorTask(step.getFrom(), step.getTo()));
			
		}
		
        ExecutorService executorPool = Executors.newFixedThreadPool(EXECUTOR_POOL_SIZE);
        
        for (CalculatorTask calcTask: calculationTasks) {
        	executorPool.execute(calcTask);
        }

        executorPool.shutdown();

        try {

            executorPool.awaitTermination(1, TimeUnit.HOURS);

        } catch (InterruptedException ex) {

        }
    }

} 