package org.module11;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableTest {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Integer> futureTask = executor.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Random random = new Random();
				int sleepDuration = random.nextInt(1000);
				if (sleepDuration > 500) {
					throw new IOException("Exception thrown");
				}
				System.out.println("Thread : " + Thread.currentThread().getName() + " is starting to process");
				TimeUnit.MILLISECONDS.sleep(sleepDuration);
				System.out.println(
						"Thread : " + Thread.currentThread().getName() + " is sleeping for : " + sleepDuration + " ms");
				return sleepDuration;
			}
		});

		try {
			System.out.println("Sleep duration : " + futureTask.get());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println(e.getMessage());
		}

		executor.shutdown();

		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
