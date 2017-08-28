package org.module2;

/*
 * Synchronization : Threads communicate by sharing access to fields & objects.This form of communication is extremely efficient
 * 					however it makes two kind of errors : thread interference & memory consistency errors..The tool to prevent 
 * 					this is called synchronization.
 * 					However synchronization gives rise to other problems like : thread contention where multiple thread try to 
 * 					access the same resource and cause the java runtime to execute one or more threads slowly giving rise to 
 * 					Thread deadlock , starvation & livelock.
 * 
 */
public class BasicSynchronizationTest {

	private int count = 0;

	public static void main(String[] args) {
		BasicSynchronizationTest app = new BasicSynchronizationTest();
		app.doWork();
	}

	private synchronized void increment() {
		count++;
	}

	private void doWork() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 1000; i++) {
					increment();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 1000; i++) {
					increment();
				}
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Count value : " + count);

	}

}
