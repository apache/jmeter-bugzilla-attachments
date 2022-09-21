/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package test.jmeter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.net.URL;

import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.protocol.http.control.AuthManager;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.Cookie;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.protocol.http.sampler.HTTPSampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jmeter.config.Argument;

import org.apache.log.Logger;


public class AjpSampler extends HTTPSamplerBase {

    transient private static Logger log= LoggingManager.getLoggerForClass(); 

    /**
     *  Translates integer codes to request header names    
     */
    public static final String []headerTransArray = {
        "accept",
        "accept-charset",
        "accept-encoding",
        "accept-language",
        "authorization",
        "connection",
        "content-type",
        "content-length",
        "cookie",
        "cookie2",
        "host",
        "pragma",
        "referer",
        "user-agent"
    };

    /**
     * Base value for translated headers
     */
    static final int AJP_HEADER_BASE = 0xA000;

    static final int MAX_SEND_SIZE = 8*1024 - 4 - 4;

    private Socket channel = null;
    private int lastPort = -1;
    private String lastHost = null;
    private String localName = null;
    private String localAddress = null;
    private byte [] inbuf = new byte[8*1024];
    private byte [] outbuf = new byte[8*1024];
    private ByteArrayOutputStream responseData = new ByteArrayOutputStream();
    private int inpos = 0;
    private int outpos = 0;
    private InputStream body = null;

    public AjpSampler() {
    }

    protected HTTPSampleResult sample(URL url, 
				       String method,
				       boolean frd,
				       int fd) {
	HTTPSampleResult res = new HTTPSampleResult();
	res.setMonitor(false);
	res.setSampleLabel(url.toExternalForm());
	res.sampleStart();
	try {
	    setupConnection(url, method, res);
	    execute(method, res);
	    res.sampleEnd();
	    res.setResponseData(responseData.toByteArray());
	    return res;
	} catch(IOException iex) {
	    res.sampleEnd();
	    HTTPSampleResult err = errorResult(iex, res);
	    lastPort = -1; // force reopen on next sample
	    channel = null;
	    return err;
	}
    }

    public void threadFinished() {
	if(channel != null) {
	    try {
		channel.close();
	    } catch(IOException iex) {
		log.debug("Error closing channel",iex);
	    }
	}
	channel = null;
	body = null;
    }

    private void setupConnection(URL url, 
				 String method,
				 HTTPSampleResult res) throws IOException {

	String host = url.getHost();
	int port = url.getPort();
	if(port <= 0 || port == url.getDefaultPort()) {
	    port = 8009;
	}
	String scheme = url.getProtocol();
	if(channel == null || !host.equals(lastHost) || port != lastPort) {
	    if(channel != null) {
		channel.close();
	    }
	    channel = new Socket(host, port);
	    int timeout = JMeterUtils.getPropDefault("httpclient.timeout",0);
	    if(timeout > 0) {
		channel.setSoTimeout(timeout);
	    }
	    localAddress = channel.getLocalAddress().getHostAddress();
	    localName = channel.getLocalAddress().getHostName();
	    lastHost = host;
	    lastPort = port;
	}
	res.setURL(url);
	res.setHTTPMethod(method);
	outpos = 4;
	setByte((byte)2);
	if(method.equals(POST))
	    setByte((byte)4);
	else
	    setByte((byte)2);
	if(JMeterUtils.getPropDefault("httpclient.version","1.1").equals("1.0")) {
	    setString("HTTP/1.0");
	} else {
	    setString("HTTP/1.1");
	}
	setString(url.getFile());
	setString(localAddress);
	setString(localName);
	setString(host);
	setInt(url.getDefaultPort());
	setByte("https".equalsIgnoreCase(scheme) ? (byte)1 : (byte)0);
	setInt(getHeaderSize(method, url));
	String hdr = setConnectionHeaders(url, host, method);
	res.setRequestHeaders(hdr);
	setConnectionCookies(url, getCookieManager());
	setByte((byte)0xff); // Attributes not supported
    }

    private int getHeaderSize(String method, URL url) {
	HeaderManager headers = getHeaderManager();
	CookieManager cookies = getCookieManager();
	AuthManager auth = getAuthManager();
	int hsz = 1; // Host always
	if(method.equals(POST)) {
	    String fn = getFilename();
	    if(fn != null && fn.trim().length() > 0) {
		hsz += 3;
	    } else {
		hsz += 2;
	    }
	}
	if(headers != null) {
	    hsz += headers.size();
	}
	if(cookies != null) {
	    hsz += cookies.getCookieCount();
	}
	if(auth != null) {
            String authHeader = auth.getAuthHeaderForURL(url);
	    if(authHeader != null) {
		++hsz;
	    }
	}
	return hsz;
    }


    private String setConnectionHeaders(URL url, String host, String method)
	throws IOException {
	HeaderManager headers = getHeaderManager();
	AuthManager auth = getAuthManager();
	StringBuffer hbuf = new StringBuffer();
	// Allow Headers to override Host setting
	hbuf.append("Host").append(": ").append(host).append('\n');
	setInt(0xA00b); //Host 
	setString(host);
	if(headers != null) {
	    CollectionProperty coll = headers.getHeaders();
	    PropertyIterator i = coll.iterator();
	    while(i.hasNext()) {
		Header header = (Header)i.next().getObjectValue();
		String n = header.getName();
		String v = header.getValue();
		hbuf.append(n).append(": ").append(v).append('\n');
		int hc = translateHeader(n);
		if(hc > 0) {
		    setInt(hc+AJP_HEADER_BASE);
		} else {
		    setString(n);
		}
		setString(v);
	    }
	}
	if(method.equals(POST)) {
	    int cl = -1;
	    String fn = getFilename();
	    if(fn != null && fn.trim().length() > 0) {
		File input = new File(fn);
		cl = (int)input.length();
		body = new FileInputStream(input);
		setString("Content-disposition");
		setString("form-data; name=\""+encode(getFileField())+
			  "\"; filename=\"" + encode(fn) +"\"");
		String mt = getMimetype();
		hbuf.append("Content-Type").append(": ").append(mt).append('\n');
		setInt(0xA007); // content-type
		setString(mt);
	    } else {
		hbuf.append("Content-Type").append(": ").append("application/x-www-form-urlencoded").append('\n');
		setInt(0xA007); // content-type
		setString("application/x-www-form-urlencoded");
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		PropertyIterator args = getArguments().iterator();
		while(args.hasNext()) {
		    JMeterProperty arg = args.next();
		    if(first) {
			first = false;
		    } else {
			sb.append('&');
		    }
		    sb.append(arg.getName()).append('=').append(arg.getStringValue());
		}
		byte [] sbody = sb.toString().getBytes(); //FIXME - encoding
		cl = sbody.length;
		body = new ByteArrayInputStream(sbody);
	    }
	    hbuf.append("Content-Length").append(": ").append(String.valueOf(cl)).append('\n');
	    setInt(0xA008); // Content-length
	    setString(String.valueOf(cl));
	}
	if(auth != null) {
            String authHeader = auth.getAuthHeaderForURL(url);
	    if(authHeader != null) {
		setInt(0xA005); // Authorization
		setString(authHeader);
		hbuf.append("Authorization").append(": ").append(authHeader).append('\n');
	    }
	}
	return hbuf.toString();
    }

    private String encode(String value)  {
        StringBuffer newValue = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            if (chars[i] == '\\')
            {
                newValue.append("\\\\");
            }
            else
            {
                newValue.append(chars[i]);
            }
        }
        return newValue.toString();
    }

    private void setConnectionCookies(URL url, CookieManager cookies) {
	if(cookies != null) {
	    CollectionProperty coll = cookies.getCookies();
	    PropertyIterator i = coll.iterator();
	    while(i.hasNext()) {
		JMeterProperty header = i.next();
		setInt(0xA009); // Cookie
		setString(header.getName()+"="+header.getStringValue());
	    }
	}
    }

    private int translateHeader(String n) {
	for(int i=0; i < headerTransArray.length; i++) {
	    if(headerTransArray[i].equalsIgnoreCase(n)) {
		return i+1;
	    }
	}
	return -1;
    }

    private void setByte(byte b) {
	outbuf[outpos++] = b;
    }

    private void setInt(int n) {
	outbuf[outpos++] = (byte)((n >> 8)&0xff);
	outbuf[outpos++] = (byte) (n&0xff);
    }

    private void setString(String s) {
	if( s == null ) {
	    setInt(0xFFFF);
	} else {
	    int len = s.length();
	    setInt(len);
	    for(int i=0; i < len; i++) {
		setByte((byte)s.charAt(i));
	    }
	    setByte((byte)0);
	}
    }

    private void send() throws IOException {
	OutputStream os = channel.getOutputStream();
	int len = outpos;
	outpos = 0;
	setInt(0x1234);
	setInt(len-4);
	os.write(outbuf, 0, len);
    }

    private void execute(String method, HTTPSampleResult res) 
	throws IOException {
	send();
	if(method.equals(POST)) {
	    sendPostBody();
	}
	handshake(res);
    }

    private void handshake(HTTPSampleResult res) throws IOException {
	responseData.reset();
	int msg = getMessage();
	while(msg != 5) {
	    if(msg == 3) {
		int len = getInt();
		responseData.write(inbuf, inpos, len); 
	    } else if(msg == 4) {
		parseHeaders(res);
	    } else if(msg == 6) {
		setNextBodyChunk();
		send();
	    }
	    msg = getMessage();
	}
    }


    private void sendPostBody() throws IOException {
	setNextBodyChunk();
	send();
    }

    private void setNextBodyChunk() throws IOException {

	int len = body.available();
	if(len < 0) {
	    len = 0;
	} else if(len > MAX_SEND_SIZE) {
	    len = MAX_SEND_SIZE;
	}
	outpos = 4;
	int nr = 0;
	if(len > 0) {
	    nr = body.read(outbuf, outpos+2, len);
	}
	setInt(nr);
	outpos += nr;
    }


    private void parseHeaders(HTTPSampleResult res) 
	throws IOException {
	int status = getInt();
	res.setResponseCode(Integer.toString(status));
	res.setSuccessful(200 <= status && status <= 399);
	String msg = getString();
	res.setResponseMessage(msg);
	int nh = getInt();
	StringBuffer sb = new StringBuffer();
	sb.append("HTTP/1.1" ).append(status).append(" ").append(msg).append('\n');
	for(int i=0; i < nh; i++) {
	    // Currently, no Tomcat version sends translated headers
	    String name;
	    int thn = peekInt();
	    if((thn & 0xff00) == AJP_HEADER_BASE) {
		name = headerTransArray[(thn&0xff)-1];
	    } else {
		name = getString();
	    }
	    String value = getString();
	    if("content-type".equalsIgnoreCase(name)) {
		res.setContentType(value);
		String de = value.toLowerCase();
		int cset = de.indexOf("charset=");
		if(cset >= 0) {
		    res.setDataEncoding(de.substring(cset+8));
		}
		if(de.startsWith("text/")) {
		    res.setDataType(HTTPSampleResult.TEXT);
		} else {
		    res.setDataType(HTTPSampleResult.BINARY);
		}
	    } else if("set-cookie".equalsIgnoreCase(name)) {
		CookieManager cookies = getCookieManager();
		if(cookies != null) {
		    cookies.addCookieFromHeader(value, res.getURL());
		}
	    }
	    sb.append(name).append(": ").append(value).append('\n');
	}
	res.setResponseHeaders(sb.toString());
    }


    private int getMessage() throws IOException {
	InputStream is = channel.getInputStream();
	inpos = 0;
	int nr = is.read(inbuf, inpos, 4);
        if(nr != 4) {
	    channel.close();
	    channel = null;
	    throw new IOException("Connection Closed: "+nr);
	}
	int mark = getInt();
	int len = getInt();
        int toRead = len;
        int cpos = inpos;
        while(toRead > 0) {
            nr = is.read(inbuf, cpos, toRead);
            cpos += nr;
            toRead -= nr;
        }
        return getByte();
    }

    private byte getByte() {
	return inbuf[inpos++];
    }

    private int getInt() {
	int res = (inbuf[inpos++]<<8)&0xff00;
	res += inbuf[inpos++]&0xff;
	return res;
    }

    private int peekInt() {
	int res = (inbuf[inpos]<<8)&0xff00;
	res += inbuf[inpos+1]&0xff;
	return res;
    }

    private String getString() throws IOException {
	int len = getInt();
	String s = new String(inbuf, inpos, len, "iso-8859-1");
	inpos+= len+1;
	return s;
    }


}







