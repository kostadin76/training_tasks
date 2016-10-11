package com.sap.test.anagram;

import java.util.Arrays;

public class AnagramBuilder {

	private int size;
	private int count;
	private char[] charArray;
	private char[] anagramedCharArray;

	public AnagramBuilder(String anagram) {
		// String input = "Java Source and Support";
		if(anagram == null || anagram.isEmpty()){
			throw new IllegalArgumentException();
		}
		size = anagram.length();
		count = 0;
		charArray = new char[size];
		for (int j = 0; j < size; j++) {
			charArray[j] = anagram.charAt(j);
		}
	}

	public void doAnagram(int newSize) {
		if (newSize == 1) { // if too small, return;
			return;
		}
		// for each position,
		for (int i = 0; i < newSize; i++) {
			doAnagram(newSize - 1); // anagram remaining
			if (newSize == 2) { // if innermost,
				display();
				anagramedCharArray = Arrays.copyOf(charArray, charArray.length);
			}
			rotate(newSize); // rotate word
		}
	}

	// rotate left all chars from position to end
	public void rotate(int newSize) {
		int i;
		int position = size - newSize;
		// save first letter
		char temp = charArray[position];
		// shift others left
		for (i = position + 1; i < size; i++) {
			charArray[i - 1] = charArray[i];
		}
		// put first on right
		charArray[i - 1] = temp;
	}

	public void display() {
		System.out.print(++count + " ");
		for (int i = 0; i < size; i++) {
			System.out.print(charArray[i]);
		}
		System.out.println();
	}
	
	public char[] getCurrentAnagram(){
		return charArray;
	}
	
	public char[] getAnagramedCharArray(){
		return anagramedCharArray;
	}
}
