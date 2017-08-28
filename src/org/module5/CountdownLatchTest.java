package org.module5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 	CountdownLatch : It is a concurrency construct in java.util.concurrent in which one or more threads wait for a set of 
 * 					instructions to complete.
 * 					It is initialized with a given count in the constructor.It has the method countDown() which decrements 
 * 					the countDownLatch value by 1. The main thread/ the thread waiting for this count to reach 0 can call 
 * 					one of the await() methods.
 * 					Calling await() blocks the thread until the countDownLatch value has reached to 0.
 * 
 */

public class CountdownLatchTest {

	public static void main(String[] args) {
		
		System.out.println("CountdownLatch test started.");
		
		CountDownLatch countDownLatch = new CountDownLatch(3);

		ExecutorService executorService = Executors.newFixedThreadPool(3);

		for (int i = 1; i <= 3; i++) {
			executorService.submit(new CountdownLatchRunnable(countDownLatch));
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(15, TimeUnit.SECONDS);
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("CountdownLatch test completed.");
	}

}

class CountdownLatchRunnable implements Runnable {

	private CountDownLatch countDownLatch;

	public CountdownLatchRunnable(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		System.out.println("Thread : " + Thread.currentThread().getName()
				+ " waiting for 3 secs");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		countDownLatch.countDown();
		System.out.println("Thread: " + Thread.currentThread().getName()
				+ " has decremented the countdown latch to : "
				+ countDownLatch.getCount());
	}

}