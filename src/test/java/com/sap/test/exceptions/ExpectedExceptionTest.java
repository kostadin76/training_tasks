package com.sap.test.exceptions;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ExpectedExceptionTest {

	@Test(expected = IndexOutOfBoundsException.class)
	public void throwExceptionTest() {
		List<Object> list = new ArrayList<Object>();
		list.get(0); // execution will never get past this line
	}
}
