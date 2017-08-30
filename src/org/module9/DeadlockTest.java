package org.module9;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockTest {

	public static void main(String[] args) {

		DeadlockProcessor deadlockProcessor = new DeadlockProcessor();

		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10000; i++) {
					deadlockProcessor.firstThread();
				}
			}
		});

		Thread threadB = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10000; i++) {
					deadlockProcessor.secondThread();
				}
			}
		});

		threadA.start();
		threadB.start();

		try {
			threadA.join();
			threadB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		deadlockProcessor.finished();

	}

}

class DeadlockProcessor {

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private DeadlockAccount accountA = new DeadlockAccount();
	private DeadlockAccount accountB = new DeadlockAccount();

	private void acquireLock() throws InterruptedException {
		while (true) {
			boolean statusLock1 = false;
			boolean statusLock2 = false;

			try {
				statusLock1 = lock1.tryLock();
				statusLock2 = lock2.tryLock();
			} finally {
				if (statusLock1 && statusLock2) {
					return;
				}
				if (statusLock1) {
					lock1.unlock();
				}
				if (statusLock2) {
					lock2.unlock();
				}
			}
			Thread.sleep(1);
		}

	}

	public void firstThread() {
		try {
			// lock1.lock();
			// lock2.lock();
			acquireLock();
			Random random = new Random();
			int transferAmount = random.nextInt(1000);
			DeadlockAccount.transfer(accountA, accountB, transferAmount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock1.unlock();
			lock2.unlock();
		}
	}

	public void secondThread() {
		try {
			// lock2.lock();
			// lock1.lock();
			acquireLock();
			Random random = new Random();
			int transferAmount = random.nextInt(1000);
			DeadlockAccount.transfer(accountB, accountA, transferAmount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock1.unlock();
			lock2.unlock();
		}
	}

	public void finished() {
		System.out.println("Account A : " + accountA.getAmount());
		System.out.println("Account B : " + accountB.getAmount());
		System.out.println("Total amount : "
				+ (accountA.getAmount() + accountB.getAmount()));
	}

}

class DeadlockAccount {

	private int amount = 10000;

	public int getAmount() {
		return amount;
	}

	public void withdraw(int withdrawnAmount) {
		this.amount = this.amount - withdrawnAmount;
	}

	public void deposit(int depositAmount) {
		this.amount = this.amount + depositAmount;
	}

	public static void transfer(DeadlockAccount acc1, DeadlockAccount acc2,
			int transferAmount) {
		acc1.withdraw(transferAmount);
		acc2.deposit(transferAmount);
	}

}