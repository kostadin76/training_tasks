package com.sap.test.runners;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.sap.test.assertions.AssertionTest;
import com.sap.test.exceptions.ExpectedExceptionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AssertionTest.class,
	ExpectedExceptionTest.class
})


public class FeatureTestSuite {
	
}
