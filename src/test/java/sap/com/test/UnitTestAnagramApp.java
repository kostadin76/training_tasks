package sap.com.test;

import org.junit.Test;

import com.sap.test.anagram.AnagramBuilder;
import static org.junit.Assert.assertArrayEquals;

public class UnitTestAnagramApp {
	@Test
	public void testAnagramRotationLowerBoundary(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.rotate(2);
		assertArrayEquals("Jaok".toCharArray(), ab.getCurrentAnagram());
	}
	
	@Test
	public void testAnagramRotationUpperBoundary(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.rotate(4);
		assertArrayEquals("akoJ".toCharArray(), ab.getCurrentAnagram());
	}

	@Test
	public void testAnagramRotation(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.rotate(3);
		assertArrayEquals("Jkoa".toCharArray(), ab.getCurrentAnagram());
	}
}
