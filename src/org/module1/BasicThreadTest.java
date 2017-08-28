package org.module1;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * volatile keyword : Generally thread's make a copy of the local variables/ cache to optimize the code.
 * 		      Volatile keyword signifies that the thread calls the main memory to retrieve the variables values.
 * 		      The differences between volatile & synchronized are: 
 * 		      i).  Volatile works on both primitive as well as object type.Synchronized works on only object tyoe.
 * 		      ii). Volatile cannot lock the object which the synchronized can do.
 * 		      iii).Volatile cannot be used if we want to make read-update-write an atomic operation
 * 		      iv). Volatile object can be null whereas synchronized objects cannot be null, they will throw NullPointerExcetion.  
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
