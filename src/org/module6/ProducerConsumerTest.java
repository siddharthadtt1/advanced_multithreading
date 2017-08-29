package org.module6;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * ArrayBlockingQueue : It is a bounded & blocking queue that stores the elements in the array.It supports fairness policy
 * 					 which means that resource will be allocated to thread which is waiting for the longest time. 
 * 
 */
public class ProducerConsumerTest {

	private final static int MAX_SIZE_BLOCKING_QUEUE = 10;

	public static void main(String[] args) {
		BlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(MAX_SIZE_BLOCKING_QUEUE);

		// producer threads
		Thread producerThread1 = new Thread(new ProducerBlockingQueueTest(arrayBlockingQueue));
		Thread producerThread2 = new Thread(new ProducerBlockingQueueTest(arrayBlockingQueue));

		// consumer threads
		Thread consumerThread = new Thread(new ConsumerBlockingQueueTest(arrayBlockingQueue));

		// starting producer threads
		producerThread1.start();
		producerThread2.start();

		// starting consumer thread
		consumerThread.start();

	}

}

class ProducerBlockingQueueTest implements Runnable {

	private BlockingQueue<Integer> arrayBlockingQueue;

	public ProducerBlockingQueueTest(BlockingQueue<Integer> arrayBlockingQueue) {
		this.arrayBlockingQueue = arrayBlockingQueue;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 20; i++) {
				produce();
				TimeUnit.SECONDS.sleep(3);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void produce() throws InterruptedException {
		Random random = new Random();
		int randomNumber = random.nextInt(100);
		System.out.println("Thread : " + Thread.currentThread().getName() + " is attempting insert : " + randomNumber);
		arrayBlockingQueue.put(randomNumber);
	}

}

class ConsumerBlockingQueueTest implements Runnable {

	private BlockingQueue<Integer> arrayBlockingQueue;

	public ConsumerBlockingQueueTest(BlockingQueue<Integer> arrayBlockingQueue) {
		this.arrayBlockingQueue = arrayBlockingQueue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				consume();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void consume() throws InterruptedException {
		System.out.println("Thread : " + Thread.currentThread().getName() + " fetching element : "
				+ arrayBlockingQueue.take() + ", current size of queue : " + arrayBlockingQueue.size());
	}

}