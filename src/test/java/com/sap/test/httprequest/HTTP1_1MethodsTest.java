package com.sap.test.httprequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.sap.test.httpparser.HttpParser;
import com.sap.test.httpparser.Status;

@RunWith(Parameterized.class)
public class HTTP1_1MethodsTest {

	@Parameters(name = "Method {0}: results in httpRequest.parse({0}) to {1}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "OPTIONS", Status.NOT_IMPLEMENTED },
																					{ "PUT", Status.NOT_IMPLEMENTED },
																					{ "DELETE", Status.NOT_IMPLEMENTED },
																					{ "TRACE", Status.NOT_IMPLEMENTED },
																					{ "CONNECT", Status.NOT_IMPLEMENTED }});
	}

	@Parameter
	public String fInput;

	@Parameter(value = 1)
	public int fExpected;
	
	private String httpRequestAsString;
	private HttpParser httpRequest;

	@Test
	public void testOtherHTTP1_1Methods() throws IOException{
		httpRequestAsString = fInput + " / HTTP/1.1\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(fExpected));
	}
}
