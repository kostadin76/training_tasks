package com.sap.test.httpparser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class HttpPraserRepliesTest {
	@Parameters(name = "Code {0}: results in verbose string: {1}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {{"100", "Continue"},
																			    {"101", "Switching Protocols"},
																			    {"200", "OK"},
																			    {"201", "Created"},
																			    {"202", "Accepted"},
																			    {"203", "Non-Authoritative Information"},
																			    {"204", "No Content"},
																			    {"205", "Reset Content"},
																			    {"206", "Partial Content"},
																			    {"300", "Multiple Choices"},
																			    {"301", "Moved Permanently"},
																			    {"302", "Found"},
																			    {"303", "See Other"},
																			    {"304", "Not Modified"},
																			    {"305", "Use Proxy"},
																			    {"306", "(Unused)"},
																			    {"307", "Temporary Redirect"},
																			    {"400", "Bad Request"},
																			    {"401", "Unauthorized"},
																			    {"402", "Payment Required"},
																			    {"403", "Forbidden"},
																			    {"404", "Not Found"},
																			    {"405", "Method Not Allowed"},
																			    {"406", "Not Acceptable"},
																			    {"407", "Proxy Authentication Required"},
																			    {"408", "Request Timeout"},
																			    {"409", "Conflict"},
																			    {"410", "Gone"},
																			    {"411", "Length Required"},
																			    {"412", "Precondition Failed"},
																			    {"413", "Request Entity Too Large"},
																			    {"414", "Request-URI Too Long"},
																			    {"415", "Unsupported Media Type"},
																			    {"416", "Requested Range Not Satisfiable"},
																			    {"417", "Expectation Failed"},
																			    {"500", "Internal Server Error"},
																			    {"501", "Not Implemented"},
																			    {"502", "Bad Gateway"},
																			    {"503", "Service Unavailable"},
																			    {"504", "Gateway Timeout"},
																			    {"505", "HTTP Version Not Supported"}
		    });
	}
	@Parameter
	public String fInput;

	@Parameter(value = 1)
	public String fExpected;
	
	@Test
	public void testStatusCodes() throws IOException{
		for(int i= 0; i<StatusCodes.HttpReplies.length; i++){
			if(fInput.equalsIgnoreCase(StatusCodes.HttpReplies[i][0])){
				String expected = fInput + " " + fExpected;
				assertTrue(expected.equalsIgnoreCase(HttpParser.getHttpReply(Integer.parseInt(StatusCodes.HttpReplies[i][0]))));
			}
		}
	}
}
