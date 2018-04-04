package net.lht.redis;

import java.util.concurrent.locks.ReentrantLock;

public class LocalTester {
	
	private ReentrantLock lock;
	
	private long count = 0L;

	public LocalTester() {
		lock = new ReentrantLock();
	}

	public long test() {
		lock.lock();
		count++;
		lock.unlock();
		return count;
	}
	
	public void reset() {
		count = 0L;
	}
	
	public long getResult() {
		return count;
	}
	
}
