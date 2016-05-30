package com.myne.tasks.sortings;

import java.util.Comparator;

public class FewMySorts {

	/**
	 * Вставками
	 */
	public static <E extends Comparable<E>> void insertionSort(E[] array) {
		int size = array.length;

		for (int outerLoopIdx = 1; outerLoopIdx < size; ++outerLoopIdx) {
			for (int innerLoopIdx = outerLoopIdx; innerLoopIdx > 0; --innerLoopIdx) {
				if (array[innerLoopIdx - 1].compareTo(array[innerLoopIdx]) > 0) {
					E temp = array[innerLoopIdx - 1];
					array[innerLoopIdx - 1] = array[innerLoopIdx];
					array[innerLoopIdx] = temp;
				}
			}
		}
	}

	/**
	 * Слиянием
	 */
	public static <E extends Comparable<E>> void mergeSort(E[] array) {
		Comparator<? super E> comp = new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.compareTo(o2);
			}
		};
		mergeSort(array, 0, array.length - 1, comp);
	}

	/**
	 * Быстрая сортировка
	 */
	public static <E extends Comparable<E>> void quickSort(E[] array) {
		sort(array, 0, array.length - 1);

	}

	
	
	// quicksort the subarray from a[lo] to a[hi]
	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo)
			return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		Comparable v = a[lo];
		while (true) {

			// find item on lo to swap
			while (less(a[++i], v))
				if (i == hi)
					break;

			// find item on hi to swap
			while (less(v, a[--j]))
				if (j == lo)
					break; // redundant since a[lo] acts as sentinel

			// check if pointers cross
			if (i >= j)
				break;

			exch(a, i, j);
		}
		// put partitioning item v at a[j]
		exch(a, lo, j);

		// now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}

	/***************************************************************************
	 * Helper sorting functions.
	 ***************************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	
	/**
	 * MERGE HELPER
	 */
	private static <E> void mergeSort(E[] a, int from, int to, Comparator<? super E> comp) {
		if (from == to)
			return;
		int mid = (from + to) / 2;
		// Sort the first and the second half
		mergeSort(a, from, mid, comp);
		mergeSort(a, mid + 1, to, comp);
		merge(a, from, mid, to, comp);
	}

	/**
	 * Вспомогательный метод для слияния
	 */
	private static <E> void merge(E[] a, int from, int mid, int to, Comparator<? super E> comp) {
		int n = to - from + 1;
		Object[] values = new Object[n];

		int fromValue = from;

		int middleValue = mid + 1;

		int index = 0;

		while (fromValue <= mid && middleValue <= to) {
			if (comp.compare(a[fromValue], a[middleValue]) < 0) {
				values[index] = a[fromValue];
				fromValue++;
			} else {
				values[index] = a[middleValue];
				middleValue++;
			}
			index++;
		}

		while (fromValue <= mid) {
			values[index] = a[fromValue];
			fromValue++;
			index++;
		}
		while (middleValue <= to) {
			values[index] = a[middleValue];
			middleValue++;
			index++;
		}

		for (index = 0; index < n; index++)
			a[from + index] = (E) values[index];
	}

}
