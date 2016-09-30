package com.sap.test.categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category({ SlowTests.class, FastTests.class })
public class TestB {
	@Test
	public void c() {
	}
}
