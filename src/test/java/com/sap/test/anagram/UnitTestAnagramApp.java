package com.sap.test.anagram;

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
	
	@Test
	public void testOneLetterWordRotation(){
		AnagramBuilder ab = new AnagramBuilder("J");
		ab.rotate(1);
		assertArrayEquals("J".toCharArray(), ab.getCurrentAnagram());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullWordRotation(){
		AnagramBuilder ab = new AnagramBuilder(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyWordRotation(){
		AnagramBuilder ab = new AnagramBuilder("");
	}
	
	@Test
	public void testDoAnagram(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.doAnagram(4);
		assertArrayEquals("okaJ".toCharArray(), ab.getAnagramedCharArray());
	}
	
}
