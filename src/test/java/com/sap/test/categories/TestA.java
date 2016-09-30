package com.sap.test.categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestA {
	@Test
	public void a() {
		fail();
	}

	@Category(SlowTests.class)
	@Test
	public void b() {
	}

	private void fail() {
		System.out.println("This method shouldn't be called in categories test!");
	}
}
