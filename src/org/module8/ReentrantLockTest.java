package org.module8;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	public static void main(String[] args) {
		ReentrantLockProcessor processor = new ReentrantLockProcessor();

		Thread threadOne = new Thread(new Runnable() {

			@Override
			public void run() {
				processor.firstMethod();
			}
		}, "Thread 1");

		Thread threadTwo = new Thread(new Runnable() {

			@Override
			public void run() {
				processor.secondMethod();
			}
		}, "Thread 2");

		threadOne.start();
		threadTwo.start();

		try {
			threadOne.join();
			threadTwo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		processor.finished();

	}

}

class ReentrantLockProcessor {

	private Lock lock_1 = new ReentrantLock();

	private int count = 0;

	private void increment() {
		for (int i = 1; i <= 10000; i++) {
			count++;
		}
	}

	public void firstMethod() {
		try {
			lock_1.lock();
			increment();
		} finally {
			lock_1.unlock();
		}
	}

	public void secondMethod() {
		try {
			lock_1.lock();
			increment();
		} finally {
			lock_1.unlock();
		}
	}

	public void finished() {
		System.out.println("Count : " + count);
	}

}
