package com.myne.tasks.deques;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Deque<E> {
	Object[] elements;
	int head;
	int tail;
	private static final int MIN_INITIAL_CAPACITY = 8;
	private static int MAX_INITIAL_CAPACITY;

	public ArrayDeque() {
		elements = new Object[MIN_INITIAL_CAPACITY];
	}

	public ArrayDeque(Integer maxDecSize) {
		this();
		MAX_INITIAL_CAPACITY = maxDecSize;
	}

	@Override
	public void addFirst(E elem) {
		if (elem == null)
			throw new NullPointerException();
		elements[head = (head - 1) & (elements.length - 1)] = elem;
		if (head == tail)
			doubleCapacity();

	}

	@Override
	public void addLast(E e) {
		if (e == null)
			throw new NullPointerException();
		elements[tail] = e;
		if ((tail = (tail + 1) & (elements.length - 1)) == head)
			doubleCapacity();

	}

	@Override
	public E first() {
		E result = (E) elements[head];
		if (result == null)
			throw new NoSuchElementException();
		return result;
	}

	@Override
	public E last() {
		E result = (E) elements[(tail - 1) & (elements.length - 1)];
		if (result == null)
			throw new NoSuchElementException();
		return result;
	}

	@Override
	public E removeFirst() {
		return pollFirst();
	}

	@Override
	public E removeLast() {
		return pollLast();
	}

	/**
	 * Doubles the capacity of this deque. Call only when full, i.e., when head
	 * and tail have wrapped around to become equal.
	 */
	private void doubleCapacity() {
		assert head == tail;
		int p = head;
		int n = elements.length;
		int r = n - p; // number of elements to the right of p
		int newCapacity = n << 1;

		// проверка максимальный размер дека
		if (newCapacity > MAX_INITIAL_CAPACITY)
			throw new IllegalStateException("Sorry, deque too big");
		Object[] a = new Object[newCapacity];
		System.arraycopy(elements, p, a, 0, r);
		System.arraycopy(elements, 0, a, r, p);
		elements = a;
		head = 0;
		tail = n;
	}

	private E pollFirst() {
		int h = head;
		@SuppressWarnings("unchecked")
		E result = (E) elements[h];
		// Element is null if deque empty
		if (result == null)
			return null;
		elements[h] = null; // Must null out slot
		head = (h + 1) & (elements.length - 1);
		return result;
	}

	private E pollLast() {
		int t = (tail - 1) & (elements.length - 1);
		@SuppressWarnings("unchecked")
		E result = (E) elements[t];
		if (result == null)
			return null;
		elements[t] = null;
		tail = t;
		return result;
	}
	
	
	

	@Override
	public String toString() {
		return "ArrayDeque [elements=" + Arrays.toString(elements) + "]";
	}

	public Iterator<E> iterator() {
		return new DeqIterator();
	}

	private void checkInvariants() {
		assert elements[tail] == null;
		assert head == tail ? elements[head] == null
				: (elements[head] != null && elements[(tail - 1) & (elements.length - 1)] != null);
		assert elements[(head - 1) & (elements.length - 1)] == null;
	}

	private boolean delete(int i) {
		checkInvariants();
		final Object[] elements = this.elements;
		final int mask = elements.length - 1;
		final int h = head;
		final int t = tail;
		final int front = (i - h) & mask;
		final int back = (t - i) & mask;

		if (front >= ((t - h) & mask))
			throw new ConcurrentModificationException();
		if (front < back) {
			if (h <= i) {
				System.arraycopy(elements, h, elements, h + 1, front);
			} else { // Wrap around
				System.arraycopy(elements, 0, elements, 1, i);
				elements[0] = elements[mask];
				System.arraycopy(elements, h, elements, h + 1, mask - h);
			}
			elements[h] = null;
			head = (h + 1) & mask;
			return false;
		} else {
			if (i < t) { // Copy the null tail as well
				System.arraycopy(elements, i + 1, elements, i, back);
				tail = t - 1;
			} else { // Wrap around
				System.arraycopy(elements, i + 1, elements, i, mask - i);
				elements[mask] = elements[0];
				System.arraycopy(elements, 1, elements, 0, t);
				tail = (t - 1) & mask;
			}
			return true;
		}
	}

	private class DeqIterator implements Iterator<E> {
		/**
		 * Index of element to be returned by subsequent call to next.
		 */
		private int cursor = head;

		/**
		 * Tail recorded at construction (also in remove), to stop iterator and
		 * also to check for comodification.
		 */
		private int fence = tail;

		/**
		 * Index of element returned by most recent call to next. Reset to -1 if
		 * element is deleted by a call to remove.
		 */
		private int lastRet = -1;

		public boolean hasNext() {
			return cursor != fence;
		}

		public E next() {
			if (cursor == fence)
				throw new NoSuchElementException();
			@SuppressWarnings("unchecked")
			E result = (E) elements[cursor];
			// This check doesn't catch all possible comodifications,
			// but does catch the ones that corrupt traversal
			if (tail != fence || result == null)
				throw new ConcurrentModificationException();
			lastRet = cursor;
			cursor = (cursor + 1) & (elements.length - 1);
			return result;
		}

		public void remove() {
			if (lastRet < 0)
				throw new IllegalStateException();
			if (delete(lastRet)) { // if left-shifted, undo increment in next()
				cursor = (cursor - 1) & (elements.length - 1);
				fence = tail;
			}
			lastRet = -1;
		}

	}

}
