import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.hamcrest.core.CombinableMatcher;
import org.junit.Test;

public class AssertionTest {
	@Test
	  public void testAssertArrayEquals() {
	    byte[] expected = "trial".getBytes();
	    byte[] actual = "trial".getBytes();
	    assertArrayEquals("failure - byte arrays not same", expected, actual);
	  }

	  @Test
	  public void testAssertEquals() {
	    assertEquals("failure - strings are not equal", "text", "text");
	  }

	  @Test
	  public void testAssertFalse() {
		  boolean isTrue = false;
	    assertFalse("failure - should be false", isTrue);
	  }

	  @Test
	  public void testAssertNotNull() {
		  Object obj = new Object();
	    assertNotNull("should not be null", obj);
	  }

	  @Test
	  public void testAssertNotSame() {
		  Object obj = new Object();
		  Object obj1 = new Object();
	    assertNotSame("should not be same Object", obj, obj1);
	  }

	  @Test
	  public void testAssertNull() {
	    assertNull("should be null", null);
	  }

	  @Test
	  public void testAssertSame() {
	    Integer aNumber = Integer.valueOf(768);
	    assertSame("should be same", aNumber, aNumber);
	  }

	  // JUnit Matchers assertThat
	  @Test
	  public void testAssertThatBothContainsString() {
	    assertThat("albumen", both(containsString("al")).and(containsString("bu")));
	  }

	  @Test
	  public void testAssertThatHasItems() {
	    assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
	  }

	  @Test
	  public void testAssertThatEveryItemContainsString() {
	    assertThat(Arrays.asList(new String[] { "fun", "ban", "net" }), everyItem(containsString("n")));
	  }

	  // Core Hamcrest Matchers with assertThat
	  @Test
	  public void testAssertThatHamcrestCoreMatchers() {
	    assertThat("good", allOf(equalTo("good"), startsWith("good")));
	    assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
	    assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
	    assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
	    assertThat(new Object(), not(sameInstance(new Object())));
	  }

	  @Test
	  public void testAssertTrue() {
	    assertTrue("failure - should be true", true);
	  }
}
