package com.myne.tasks.sortings;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestSortings {

	public static void main(String[] args) {
		int n = 5 + (int) (Math.random() * (25 - 5));
		System.out.println("Elements: " + n);

		Integer[] arr = new Integer[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * 50);
		}

		System.out.println("Before sort:");
		System.out.println(Arrays.toString(arr));
		// FewMySorts.insertionSort(arr);
		// FewMySorts.mergeSort(arr);
		// FewMySorts.quickSort(arr);

		if (args.length != 1) {
			System.out.println("Error with arguments");
			return;
		}

		switch (args[0]) {
		case "insertionSort":
			FewMySorts.insertionSort(arr);
			break;
		case "mergeSort":
			FewMySorts.mergeSort(arr);
			break;
		case "quickSort":
			FewMySorts.quickSort(arr);
			break;

		default:
			System.out.println("Введено неверное название метода");
		}

		System.out.println("\nAfter sort with: " + args[0]);
		System.out.println(Arrays.toString(arr));
	}

}
