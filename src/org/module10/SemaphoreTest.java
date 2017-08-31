package org.module10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 1; i <= 200; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					Connection.getInstance().connect();
				}
			});
		}

		executor.shutdown();

		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

class Connection {

	private static Connection connection = new Connection();
	private Semaphore semaphore = new Semaphore(10, true);
	private int count = 0;

	private Connection() {
	}

	public static Connection getInstance() {
		return connection;
	}

	public void connect() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			doConnect();
		} finally {
			semaphore.release();
		}
	}

	private void doConnect() {
		synchronized (this) {
			count++;
			System.out.println("Current connection : " + count);
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		synchronized (this) {
			count--;
		}
	}

}
