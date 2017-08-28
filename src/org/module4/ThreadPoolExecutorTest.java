package org.module4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
		for(int i = 1; i<= 5; i++){
			executorService.submit(new PoolExecutorRunnable());
		}
		
		executorService.shutdown();
		try {
			executorService.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}

class PoolExecutorRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("Starting processing of thread : " + Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Ending processing of thread : " + Thread.currentThread().getName());
	}

}