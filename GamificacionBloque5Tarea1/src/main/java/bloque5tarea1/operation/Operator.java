package bloque5tarea1.operation;

public class Operator {
	
	public static double operation(String numbers) {
		String[] stringNumbers = numbers.trim().split(",");
		if (stringNumbers.length != 2) {
			throw new ArithmeticException("Operation expression must be <op1>,<op2>");
		}
		double op1 = Double.parseDouble(stringNumbers[0]);
		double op2 = Double.parseDouble(stringNumbers[1]);
		
		return op1 * op2;
	}
	
	public static String operationString(String numbers) {
		String res;
		try {
			res = String.valueOf(operation(numbers));
		} catch (Exception e) {
			res = "Error: " + e.getMessage();
		}
		
		return res;
	}

}
