package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	private final ReentrantLock mLock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
	private final Condition mPermitNumberIsNotZero;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private volatile int mPermits;

	/**
	 * Constructor initialize the data members.
	 */
    public SimpleSemaphore(int permits, boolean fair) {
		mLock = new ReentrantLock(fair);
		// TODO - you fill in here
		mPermitNumberIsNotZero = mLock.newCondition();
		mPermits = permits;
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
		// TODO - you fill in here
		final ReentrantLock lock = mLock;
		lock.lockInterruptibly();
		try {
			while (mPermits == 0)
				mPermitNumberIsNotZero.await();
			mPermits--;
		} finally {
			lock.unlock();
		}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
		// TODO - you fill in here
		final ReentrantLock lock = mLock;
		lock.lock();
		try {
			while (mPermits == 0)
				mPermitNumberIsNotZero.awaitUninterruptibly();
			mPermits--;
		} finally {
			lock.unlock();
		}
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
		// TODO - you fill in here
		final ReentrantLock lock = mLock;
		lock.lock();
		try {
			mPermits++;
			mPermitNumberIsNotZero.signalAll();
		} finally {
			lock.unlock();
		}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
		// TODO - you fill in here
		return mPermits; // You will change this value.
    }
}
