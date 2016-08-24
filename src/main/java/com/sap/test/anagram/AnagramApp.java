package com.sap.test.anagram;

import java.io.IOException;

public class AnagramApp {

	public static void main(String[] args) throws IOException {
//		String anagram = "Java Source and Support";
		String anagram = "Jako";
		new AnagramBuilder(anagram).doAnagram(anagram.length());
	}
}
