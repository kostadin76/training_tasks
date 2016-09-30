package com.sap.test.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

public class ExpectedExceptionRuleTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldTestExceptionMessage() {
		List<Object> list = new ArrayList<Object>();

		thrown.expect(IndexOutOfBoundsException.class);
		thrown.expectMessage("Index: 0, Size: 0");
		//The expectMessage also lets you use Matchers, which gives you a bit more flexibility in your tests. 
		thrown.expectMessage(JUnitMatchers.containsString("Size: 0"));
		list.get(0); // execution will never get past this line
	}
}
