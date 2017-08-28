package org.module5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {

	public static void main(String[] args) {

		CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2, new Runnable() {
			@Override
			public void run() {
				System.out.println("Cyclic barrier 1 executed");
			}

		});

		CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2, new Runnable() {
			@Override
			public void run() {
				System.out.println("Cyclic barrier 2 executed");
			}

		});

		Thread thread1 = new Thread(
				new CyclicBarrierRunnable(cyclicBarrier1, cyclicBarrier2));
		Thread thread2 = new Thread(
				new CyclicBarrierRunnable(cyclicBarrier1, cyclicBarrier2));

		thread1.start();
		thread2.start();

	}

}

class CyclicBarrierRunnable implements Runnable {

	private CyclicBarrier cyclicBarrier1;
	private CyclicBarrier cyclicBarrier2;

	public CyclicBarrierRunnable(CyclicBarrier cyclicBarrier1,
			CyclicBarrier cyclicBarrier2) {
		this.cyclicBarrier1 = cyclicBarrier1;
		this.cyclicBarrier2 = cyclicBarrier2;
	}

	@Override
	public void run() {
		System.out.println("Thread : " + Thread.currentThread().getName()
				+ " is waiting to hit the cyclic barrier 1 ");
		try {
			TimeUnit.SECONDS.sleep(3);
			cyclicBarrier1.await();

			TimeUnit.SECONDS.sleep(3);

			System.out.println("Thread : " + Thread.currentThread().getName()
					+ " is waiting to hit the cyclic barrier 2 ");

			cyclicBarrier2.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}

	}

}