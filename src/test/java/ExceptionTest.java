import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;

public class ExceptionTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldTestExceptionMessage() {
		List<Object> list = new ArrayList<Object>();

		thrown.expect(IndexOutOfBoundsException.class);
		thrown.expectMessage("Index: 0, Size: 0");
		list.get(0); // execution will never get past this line
	}

	@Test
	public void shouldThrow() {
		TestThing testThing = new TestThing();
		thrown.expect(NotFoundException.class);
		thrown.expectMessage(startsWith("some Message"));
		thrown.expect(hasProperty("response", hasProperty("status", is(404))));
		thrown.expect(hasProperty("cause", notNullValue()));
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
