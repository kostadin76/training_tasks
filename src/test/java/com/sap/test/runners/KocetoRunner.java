package com.sap.test.runners;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.sap.test.assertions.AssertionTest;



public class KocetoRunner {
	public static void main(String[] args){
		Result  result = JUnitCore.runClasses(AssertionTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	}
}
