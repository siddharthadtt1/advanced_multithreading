package org.module2;

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
