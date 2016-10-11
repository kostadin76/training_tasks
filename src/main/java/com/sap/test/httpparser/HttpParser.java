package com.sap.test.httpparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class HttpParser {


  private BufferedReader httpRequestReader;
  private String method, url;
  private Map<String, String> headers, params;
  private int[] ver;
  private String[] cmd;

  public HttpParser(InputStream is) {
    httpRequestReader = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
    initHttpReader();
  }
  
	private void initHttpReader() {
		method = "";
		url = "";
		headers = new HashMap<String, String>();
		params = new HashMap<String, String>();
		ver = new int[2];
	}

  public int parseRequest() throws IOException {
    String statusLine;
    int ret;
    ret = Status.OK; // default is OK now
    statusLine = httpRequestReader.readLine();
    if( (ret = invalidStatusLine(statusLine)) != 200){
    	return ret;
    }

    if (is_GET_Or_HEAD_Request()) {
      ret = process_GET_Or_HEAD_Request();
      if (is_HTTP_1_1_Request() && missing_Host_Header()) {
        ret = Status.BAD_REQUEST;
      }
    } else if (is_POST_Request()) {
      ret = Status.NOT_IMPLEMENTED; // not implemented
    } else if (is_HTTP_1_1_Request()) {
      if (cmd[0].equals("OPTIONS") ||
          cmd[0].equals("PUT") ||
          cmd[0].equals("DELETE") ||
          cmd[0].equals("TRACE") ||
          cmd[0].equals("CONNECT")) {
        ret = Status.NOT_IMPLEMENTED; // not implemented
      } else {
      	ret = Status.BAD_REQUEST;
      }
    } else {
      // method not understand, bad request
      ret = Status.BAD_REQUEST;
    }

    return ret;
  }

  public String getMethod() {
    return method;
  }

  public String getHeader(String key) {
   	return headers.get(key.toLowerCase());
  }

  public Map<String, String> getHeaders() {
    return deepCopyMap(headers);
  }

  public String getRequestURL() {
    return url;
  }

  public String getParam(String key) {
    return params.get(key);
  }

  public Map<String, String> getParams() {
    return deepCopyMap(params);
  }

  public String getVersion() {
    return ver[0] + "." + ver[1];
  }

  public int compareVersion(int major, int minor) {
    if (major < ver[0]){
    	return -1;
    } else if (major > ver[0]){
    	return 1;
    } else if (minor < ver[1]){
    	return -1;
    } else if (minor > ver[1]){
    	return 1;
    } else{
    	return 0;
    }
  }

  public static String getHttpReply(int codevalue) {
    String key, ret;
    int i;

    ret = null;
    key = "" + codevalue;
    for (i=0; i<StatusCodes.HttpReplies.length; i++) {
      if (StatusCodes.HttpReplies[i][0].equals(key)) {
        ret = codevalue + " " + StatusCodes.HttpReplies[i][1];
        break;
      }
    }
    return ret;
  }

  public static String getDateHeader() {
    SimpleDateFormat format;
    String ret;
    format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
    format.setTimeZone(TimeZone.getTimeZone("GMT"));
    ret = "Date: " + format.format(new Date()) + " GMT";
    return ret;
  }

	private int process_GET_Or_HEAD_Request() throws UnsupportedEncodingException, IOException {
		int idx = cmd[1].indexOf('?');
		if (idx < 0){
			url = cmd[1];
		}	else {
		  extractRequestParameters(idx);
		}
		parseHeaders();
		if (is_HTTP_1_1_Request() && headers.size() == 0){
			return Status.BAD_REQUEST;
		}
		return Status.OK;
	}

	private void extractRequestParameters(int idx) throws UnsupportedEncodingException {
		String[] prms;
		String[] temp;
		int i;
		url = URLDecoder.decode(cmd[1].substring(0, idx), "ISO-8859-1");
		prms = cmd[1].substring(idx+1).split("&");

		params = new Hashtable<String, String>();
		for (i=0; i<prms.length; i++) {
		  temp = prms[i].split("=");
		  if (temp.length == 2) {
		    // we use ISO-8859-1 as temporary charset and then
		    // String.getBytes("ISO-8859-1") to get the data
		    params.put(URLDecoder.decode(temp[0], "ISO-8859-1"), URLDecoder.decode(temp[1], "ISO-8859-1"));
		  }
		  else if(temp.length == 1 && prms[i].indexOf('=') == prms[i].length()-1) {
		    // handle empty string separately
		    params.put(URLDecoder.decode(temp[0], "ISO-8859-1"), "");
		  }
		}
	}

	private int validateHTTPProtocol() {
		String[] temp;
		if (cmd[2].indexOf("HTTP/") == 0 && cmd[2].indexOf('.') > 5) {
			temp = cmd[2].substring(5).split("\\.");
			try {
				ver[0] = Integer.parseInt(temp[0]);
				ver[1] = Integer.parseInt(temp[1]);
			} catch (NumberFormatException nfe) {
				return Status.BAD_REQUEST;
			}
		} else
			return Status.BAD_REQUEST;
		return Status.OK;
	}

	private int invalidStatusLine(String statusLine) {
		if (statusLine == null || statusLine.length() == 0){
    	return 0;
    }
    if (Character.isWhitespace(statusLine.charAt(0))) {
      // starting whitespace, return bad request
      return Status.BAD_REQUEST;
    }

    cmd = statusLine.split("\\s");
    if (cmd.length != 3) {
      return Status.BAD_REQUEST;
    }
    
    if( validateHTTPProtocol() != 200){
    	return Status.BAD_REQUEST;
    }
		method = cmd[0];
		return Status.OK;
	}

  private void parseHeaders() throws IOException {
    String line;
    int idx;

    // that fucking rfc822 allows multiple lines, we don't care now
    line = httpRequestReader.readLine();
    while (!"".equals(line) && line != null) {
      idx = line.indexOf(':');
      if (idx < 0) {
        headers.clear();
        break;
      }
      else {
        headers.put(line.substring(0, idx).toLowerCase(), line.substring(idx+1).trim());
      }
      line = httpRequestReader.readLine();
    }
  }
  
  private Map<String, String> deepCopyMap(Map<String, String> original){
      Map<String, String> copy = new HashMap<String, String>(original.size());
      for(Map.Entry<String, String> entry : original.entrySet()) {
          copy.put(entry.getKey(), entry.getValue());
      }
      return copy;
  }

	private boolean missing_Host_Header() {
		return getHeader("host") == null;
	}

	private boolean is_HTTP_1_1_Request() {
		return ver[0] == 1 && ver[1] >= 1;
	}

	private boolean is_POST_Request() {
		return cmd[0].equals("POST");
	}

	private boolean is_GET_Or_HEAD_Request() {
		return cmd[0].equals("GET") || cmd[0].equals("HEAD");
	}
}