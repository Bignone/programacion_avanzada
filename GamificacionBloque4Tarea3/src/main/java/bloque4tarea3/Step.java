package bloque4tarea3;

import java.util.ArrayList;
import java.util.List;

public class Step {
	
	private int to;
	private int from;

	public Step(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	public int getFrom() {
		return from;
	}
	
	public int getTo() {
		return to;
	}
	
	public static List<Step> createSteps(int nSteps, int to) {
		ArrayList<Step> steps = new ArrayList<>();
		
		if (nSteps > to || nSteps < 1)
			throw new ArithmeticException("nSteps must be < to and > 0");
		
		if (nSteps == 1) {
			steps.add(new Step(0, to));
		}
		else {
			int stepFrom;
			int stepTo;
			int stepSize = to / nSteps; 
			for (int i=0; i<nSteps-1; i++) {
				stepFrom = i*stepSize;
				stepTo = (i+1)*stepSize;
				steps.add(new Step(stepFrom, stepTo));
			}
			int lastStepFrom = (nSteps-1)*stepSize;
			int lastStepTo = to;
			steps.add(new Step(lastStepFrom, lastStepTo));
		}
		
		return steps;
		
	}

}
