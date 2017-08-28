package org.module1;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * volatile keyword : 
 * 
 */
public class BasicThreadTest {

	public static void main(String args[]) {
		BasicThreadRunner basicThreadRunner = new BasicThreadRunner();
		//basicThreadRunner.run();
		basicThreadRunner.start();

		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();

		basicThreadRunner.shutdown();

		scanner.close();

	}

}

class BasicThreadRunner extends Thread {

	private volatile boolean isRunning = true;

	@Override
	public void run() {
		int i = 1;
		while (isRunning) {
				System.out.println(Thread.currentThread().getName() + " prints : " + i++);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	public void shutdown() {
		System.out.println(Thread.currentThread().getName() + "'s shutdown() is being called ");
		this.isRunning = false;
	}
}