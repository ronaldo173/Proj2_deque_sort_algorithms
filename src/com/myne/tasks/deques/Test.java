package com.myne.tasks.deques;

import java.io.IOError;

public class Test {
	public static void main(String[] args) {
		System.out.println(new Test().getInt());
	}

	@SuppressWarnings("finally")
	int getInt() {

		try {

			throw new Exception();
		} catch (Exception e) {
			throw new RuntimeException("1");
		} finally {
			System.out.println(1);
			throw new RuntimeException("2");

		}
	}

}
