package org.module7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WaitNotifyProducerConsumerTest {

	public static void main(String[] args) {
		List<Integer> storingList = new ArrayList<>();

		Thread producer = new Thread(new WaitNotifyProducerRunnable(storingList));
		Thread consumer = new Thread(new WaitNotifyConsumerRunnable(storingList));

		for (int i = 0; i < 5; i++) {
			producer.start();
		}
		consumer.start();

		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("The waitNotifyProducerConsumerTest has completed. ");

	}

}

class WaitNotifyProducerRunnable implements Runnable {

	private List<Integer> storingList;

	public WaitNotifyProducerRunnable(List<Integer> storingList) {
		this.storingList = storingList;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			try {
				produce();
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void produce() throws InterruptedException {
		Random random = new Random();
		int number = random.nextInt(100);
		synchronized (storingList) {
			while (storingList.size() > 5) {
				System.out
						.println("Thread : " + Thread.currentThread().getName() + " is waiting for the list to reduce");
				storingList.wait();
			}
			storingList.add(number);
			System.out.println("Thread : " + Thread.currentThread().getName() + " has added the element : " + number);
			storingList.notify();
		}
	}

}

class WaitNotifyConsumerRunnable implements Runnable {

	private List<Integer> storingList;

	public WaitNotifyConsumerRunnable(List<Integer> storingList) {
		this.storingList = storingList;
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(1);
			while (true) {
				consume();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void consume() throws InterruptedException {
		synchronized (storingList) {
			while (storingList.isEmpty()) {
				System.out.println(
						"Thread : " + Thread.currentThread().getName() + " is waiting for the list to fill up");
				storingList.wait();
			}
			int listElement = storingList.remove(0);
			System.out
					.println("Thread : " + Thread.currentThread().getName() + " is fetching element : " + listElement);
			storingList.notify();
		}
	}

}