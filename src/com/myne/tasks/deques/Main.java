package com.myne.tasks.deques;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws Exception {

		Class<ArrayDeque> clasArrDeq = ArrayDeque.class;
		Class<ListDeque> clasLinqDeq = ListDeque.class;

		if (args.length == 0) {
			System.out.println("No arguments! Must be name of Deque implementation \n"
					+ "ArrayDeque or ListDeque, if ArrayDeque expect MAX_SIZE");
			return;
		}

		int n = 5 + (int) (Math.random() * (25 - 5));
		System.out.println("Elements: " + n);

		/**
		 * Программа должна динамически загрузить класс с нужной реализацией,
		 * создать дек из целых чисел, заполнить его несколькими случайными
		 */
		Deque<Integer> deque = null;
		int maxDeq = 0;
		System.out.println(args[0]);
		if (args.length == 2) {
			maxDeq = Integer.parseInt(args[1]);
			Constructor<ArrayDeque> constructor = clasArrDeq.getConstructor(Integer.class);
			deque = constructor.newInstance(new Object[] { maxDeq });
		} else {
			deque = new ListDeque<>();
		}

		// заполнить его несколькими случайными элементами
		System.out.println(deque);

		for (int i = 0; i < n; i++) {
			int random = (int) (Math.random() * 101); // ot 0 do 100
			deque.addLast(random);
		}

		System.out.println("\n");
		int sum = 0;
		for (Integer integer : deque) {
			System.out.print(integer + " ");
			sum += integer;
		}
		System.out.println("\nSum is: " + sum);

		/**
		 * проверить, есть ли в загруженной реализации дополнительный
		 * реализованный метод size (вычисляющий число элементов в очереди).
		 * Если такой метод есть, то программа должна его вызвать и вывести
		 * общее число добавленных элементов; если нет - сообщить об отсутствии
		 * метода в реализации.
		 */

		try {
			Method method = deque.getClass().getMethod("size");
			Object size = method.invoke(deque);
			System.out.println("Size is: " + size);
		} catch (Exception e) {
			System.out.println("отсутствии метода в реализации.");
		}

	}

}
