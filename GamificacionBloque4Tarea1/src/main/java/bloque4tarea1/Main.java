package bloque4tarea1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
	
	private static int NUM_CALCULATORS = 10;
	private static int CALCULATION_LIMIT = 10000000;

	public static void main(String[] args) {

		Result result = new Result();

        long t0 = (new Date()).getTime(); 

        List<Step> steps = Step.createSteps(NUM_CALCULATORS, CALCULATION_LIMIT);
        ArrayList<Calculator> calculators = new ArrayList<>();
        Calculator calculator;
        for (int i = 0; i < NUM_CALCULATORS; i++) {
        	Step step = steps.get(i);
        	calculator = new Calculator(step.getFrom(), step.getTo(), result);
        	calculator.start();
        	calculators.add(calculator);
        	
        }
        
        for (Calculator calculatorStardet: calculators) {
        	try
            {
        		calculatorStardet.join();

            } catch (InterruptedException e){
            	
            }
        }
        

        long t1 = (new Date()).getTime(); 

        System.out.println("La suma de los primos hasta " +
        		CALCULATION_LIMIT + " es: "+result.getValue()+" calculado en "+
        		(t1-t0)+" miliseg.");

    }

}
