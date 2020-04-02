package bloque4tarea1;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;



public class Result {   

    private final AtomicReference<BigInteger> value = new AtomicReference<>();
    
    public Result() {
    	value.set(BigInteger.ZERO);
    }

 
    public BigInteger getValue() {
    	return value.get();
    }
    
    public void increment(long val) {
    	 while(true) {
    		 BigInteger existingValue = value.get();
    		 BigInteger newValue = existingValue.add(BigInteger.valueOf(val));
             if(value.compareAndSet(existingValue, newValue)) {
                 return;
             }
    	 }
    }
}