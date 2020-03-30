package ejemplo1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resultado {

	private int value;
	private Lock lock = new ReentrantLock();

	
	public int getValue() {
		try {
			lock.lock();
			return value;
		} finally {
			lock.unlock();
		}
	}
	
	public void addToValue(int number) {
		try {
			lock.lock();
			value += number;
		} finally {
			lock.unlock();
		}
	}


}
