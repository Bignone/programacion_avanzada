package bloque4tarea2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Result {   

    private BigInteger value = new BigInteger("0");
    private List<BigInteger> partialResults = new ArrayList<>();
    
 
    public BigInteger getValue() {
    	return value;
    }
    
    public int getNumPartials() {
    	return partialResults.size();
    }
    
    public synchronized void addPartialResult(BigInteger bigint) {
    	partialResults.add(bigint);
    }
    
    public void sumPartials() {
    	for (BigInteger bigInt: partialResults) {
    		value = value.add(bigInt);
    	}
    }
  
}