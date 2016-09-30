package com.sap.test.exceptions;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ExpectedExceptionRuleMatcherTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void shouldThrow() {
		TestThing testThing = new TestThing();
		thrown.expect(NotFoundException.class);
		thrown.expectMessage(startsWith("some Message"));
		thrown.expect(hasProperty("response", hasProperty("status", is(404))));
		thrown.expect(hasProperty("cause", nullValue()));
		try {
			testThing.chuck();
		} catch (NotFoundException exc) {
			System.out.println(exc);
			throw exc;
		}
	}

	private class TestThing {
		public void chuck() {
			Response response = Response.status(Status.NOT_FOUND).entity("Resource not found").build();
			throw new NotFoundException("some Message", response);
		}
	}
}
