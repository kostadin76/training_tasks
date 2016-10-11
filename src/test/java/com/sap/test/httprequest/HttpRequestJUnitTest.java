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
import com.sap.test.parameterized.Fibonacci;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpRequestJUnitTest {
	
	private String httpRequestAsString;
	private HttpParser httpRequest;
	
	@Test
	public void testEmptyStatusLine() throws IOException{
		httpRequestAsString = "";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(0));
	}
	
	@Test 
	public void testStstusLineStartingWithWhiteSpace() throws IOException{
		httpRequestAsString = " GET / HTTP/1.1\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.BAD_REQUEST));
	}

	@Test
	public void testIncompleteStatusLine() throws IOException{
		httpRequestAsString = "GET /\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.BAD_REQUEST));
	}
	
	@Test
	public void testInvalidHTTPProtocolString() throws IOException{
		httpRequestAsString = "GET / HTTP1.1\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.BAD_REQUEST));
	}
	
	@Test
	public void testInvalidHTTPProtocolNumber() throws IOException{
		httpRequestAsString = "GET / HTTP/S.1\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.BAD_REQUEST));
	}
	

	
	@Test
	public void testRequestWithoutHeaders() throws IOException{
		httpRequestAsString = "GET / HTTP/1.1\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.BAD_REQUEST));
	}
	
	@Test
	public void testValidHostHeader() throws IOException{
		httpRequestAsString = "GET / HTTP/1.1\r\n" +
													"Host: localhost:50000\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.OK));
	}
	
	@Test
	public void testOneRequestParameters() throws IOException{
		httpRequestAsString = "GET /?param1=value1 HTTP/1.1\r\n" +
				"Host: localhost:50000\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		httpRequest.parseRequest();
		assertTrue(httpRequest.getParams().size() == 1);
		assertEquals(httpRequest.getParam("param1"), "value1");
	}
	
	@Test
	public void testTwoRequestParameters() throws IOException{
		httpRequestAsString = "GET /?param1=value1&param2=value2 HTTP/1.1\r\n" +
				"Host: localhost:50000\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		httpRequest.parseRequest();
		assertTrue(httpRequest.getParams().size() == 2);
		assertEquals(httpRequest.getParam("param1"), "value1");
		assertEquals(httpRequest.getParam("param2"), "value2");
	}

	@Test
	public void testRequestHeaders() throws IOException{
		httpRequestAsString = "GET / HTTP/1.1\r\n" +
				"Host: localhost:50000\r\n" +
				"Cookie: JSESSIONID=123456";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		httpRequest.parseRequest();
		assertTrue(httpRequest.getHeaders().size() == 2);
		assertEquals(httpRequest.getHeader("Cookie"), "JSESSIONID=123456");
		assertEquals(httpRequest.getHeader("Host"), "localhost:50000");
	}
	
	@Test 
	public void testPostRequest() throws IOException{
		httpRequestAsString = "POST / HTTP/1.1\r\n"	+
				"Host: localhost:50000\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.NOT_IMPLEMENTED));
	}
	
	@Test
	public void testBadMethodName() throws IOException{
		httpRequestAsString = "GUEST / HTTP/1.1\r\n";
		httpRequest = new HttpParser(new ByteArrayInputStream(httpRequestAsString.getBytes()));
		assertThat(httpRequest.parseRequest(), is(Status.BAD_REQUEST));
	}
	

}
