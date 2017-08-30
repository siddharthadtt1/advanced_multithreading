package org.module7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LowLevelProducerConsumerTest {

	public static void main(String[] args) {

		LowLevelProcessor processor = new LowLevelProcessor();

		Thread producer = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					processor.produce();
				}
			}
		}, "producer");

		Thread consumer = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					processor.consume();
				}
			}
		}, "consumer");

		producer.start();
		consumer.start();

		try {
			producer.join();
			consumer.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(
				"Low level producer consumer has completed processing ..");

	}

}

class LowLevelProcessor {

	private List<Integer> list;
	private int MAX_SIZE_LIST = 4;
	private Object lock = new Object();

	public LowLevelProcessor() {
		list = new ArrayList<>();
	}

	public void produce() {
		try {
			TimeUnit.SECONDS.sleep(1);
			Random random = new Random();
			int listElement = random.nextInt(100);
			synchronized (lock) {
				while (list.size() > MAX_SIZE_LIST) {
					lock.wait();
				}
				list.add(listElement);
				System.out.println("Thread : "
						+ Thread.currentThread().getName() + " has inserted "
						+ listElement + " in the list");
				lock.notify();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void consume() {
		try {
			TimeUnit.SECONDS.sleep(2);
			synchronized (lock) {
				while (list.isEmpty()) {
					lock.wait();
				}
				int listElement = list.remove(0);
				System.out
						.println("Thread : " + Thread.currentThread().getName()
								+ " has removed element : " + listElement
								+ " from the list");
				lock.notify();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}