package com.myne.tasks.deques;

import java.lang.reflect.Constructor;

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
		 * элементами, вычмслить сумму элементов дека, используя итератор, а
		 * затем проверить, есть ли в загруженной реализации дополнительный
		 * реализованный метод size
		 */
		Deque<Integer> deque = null;
		int maxDeq = 0;
		System.out.println(args[0]);
		if (args.length == 2) {
			maxDeq = Integer.parseInt(args[1]);
			Constructor<ArrayDeque> constructor = clasArrDeq.getConstructor(Integer.class);
			deque = constructor.newInstance(new Object[] { maxDeq });
		}
		else{
			deque  = new ListDeque<>();
		}
		
		//заполнить его несколькими случайными элементами
		for (int i = 0; i < n; i++) {
			int random = (int) (Math.random() * 101); //ot 0 do 100
			deque.addLast(random);
		}
		
		System.out.println("\n");
		for (Integer integer : deque) {
			System.out.print(integer + " ");
		}
		

	}

}
