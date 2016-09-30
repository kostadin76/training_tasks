package com.sap.test.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class ExpectedExceptionTryCatchTest {

	@Test
	public void testExceptionMessage(){
		try{
			List<Object> list = new ArrayList<Object>();
//			list.add(new Object());
			list.get(0);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch(IndexOutOfBoundsException iooe){
			assertThat(iooe.getMessage(), is("Index: 0, Size: 0"));
		}
	}
}
