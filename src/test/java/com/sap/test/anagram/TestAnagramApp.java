package com.sap.test.anagram;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class TestAnagramApp {
	@Test
	public void testAnagramRotationLowerBoundary(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.rotate(2);
		assertTrue(ab.currentAnagramEqualsTo("Jaok"));
	}
	
	@Test
	public void testAnagramRotationUpperBoundary(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.rotate(4);
		assertTrue(ab.currentAnagramEqualsTo("akoJ"));
	}

	@Test
	public void testAnagramRotation(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.rotate(3);
		assertTrue(ab.currentAnagramEqualsTo("Jkoa"));
	}
	
	@Test
	public void testOneLetterWordRotation(){
		AnagramBuilder ab = new AnagramBuilder("J");
		ab.rotate(1);
		assertTrue(ab.currentAnagramEqualsTo("J"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullWordRotation(){
		new AnagramBuilder(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyWordRotation(){
		new AnagramBuilder("");
	}
	
	@Test
	public void testDoAnagram(){
		AnagramBuilder ab = new AnagramBuilder("Jako");
		ab.doAnagram(4);
		assertTrue(ab.anagramEqualsTo("okaJ"));
	}
	
	@Test
	public void testMain() throws IOException{
		AnagramApp.main(null);
	}
	
}
