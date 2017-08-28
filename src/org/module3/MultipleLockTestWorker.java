package org.module3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MultipleLockTestWorker {

	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();

	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private Random random = new Random();

	// public synchronized void stageOne() {
	public void stageOne() {
		synchronized (lock1) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}

	// public synchronized void stageTwo() {
	public void stageTwo() {
		synchronized (lock2) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}

	public void process() {
		for (int i = 1; i <= 1000; i++) {
			stageOne();
			stageTwo();
		}
	}

	public void main() {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		Long start = System.currentTimeMillis();

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Long end = System.currentTimeMillis();

		System.out.println("Time taken : " + (end - start));
		System.out.println("List1 size : " + list1.size() + " , List2 size : " + list2.size());

	}

}
