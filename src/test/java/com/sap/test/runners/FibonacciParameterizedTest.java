package com.sap.test.runners;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

	@RunWith(Parameterized.class)
	public class FibonacciParameterizedTest {

		@Parameters
		public static Collection<Object[]> data() {
			return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 } });
		}

		private int fInput;

		private int fExpected;

		public FibonacciParameterizedTest(int input, int expected) {
			fInput = input;
			fExpected = expected;
		}

		@Test
		public void test() {
			assertEquals(fExpected, Fibonacci0.compute(fInput));
		}
	}

 class Fibonacci0 {
	public static int compute(int n) {
		int result = 0;

		if (n <= 1) {
			result = n;
		} else {
			result = compute(n - 1) + compute(n - 2);
		}

		return result;
	}
}