/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.apache.jmeter.protocol.http.control;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.jmeter.junit.JMeterTestCase;
import org.apache.jmeter.protocol.http.control.CacheManager.CacheEntry;
import org.apache.jmeter.protocol.http.util.HTTPConstantsInterface;
import org.apache.jmeter.samplers.SampleResult;

public class TestCacheManager extends JMeterTestCase {
	
	private class URLConnectionStub extends URLConnection {
		
		protected URLConnectionStub(URL url) {
			super(url);
		}
		
		private URLConnectionStub(URLConnection urlConnection) {
			super(urlConnection.getURL());
		}
		
		@Override
		public void connect() throws IOException {
		}
		
		@Override
		public String getHeaderField(String name) {
			if (HTTPConstantsInterface.LAST_MODIFIED.equals(name)) {
				return currentTimeInGMT;
			} else if (HTTPConstantsInterface.ETAG.equals(name)) {
				return EXPECTED_ETAG;
			}
			return super.getHeaderField(name);
		}
		
	}
	
	private static final String LOCAL_HOST = "http://localhost/";
	private static final String EXPECTED_ETAG = "0xCAFEBABEDEADBEEF";
	private CacheManager cacheManager;
	private String currentTimeInGMT;
	private URL url;
	private URI uri;
	private URLConnectionStub urlConnection;
	private HttpMethod httpMethod;
	private HttpURLConnection httpUrlConnection;
	

	public TestCacheManager(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
		this.cacheManager = new CacheManager();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
		simpleDateFormat.applyPattern("EEE, dd MMM yyyy HH:mm:ss z");
		this.currentTimeInGMT = simpleDateFormat.format(new Date());
		this.uri = new URI(LOCAL_HOST, false);
		this.url = new URL(LOCAL_HOST);
		this.urlConnection =  new URLConnectionStub(this.url.openConnection());
		this.httpMethod = new PostMethod();
		this.httpUrlConnection = new HttpURLConnection(this.httpMethod, this.url);
	}

	protected void tearDown() throws Exception {
		this.httpUrlConnection = null;
		this.httpMethod = null;
		this.urlConnection = null;
		this.url = null;
		this.uri = null;
		this.cacheManager = null;
		this.currentTimeInGMT = null;
		super.tearDown();
	}

	public void testGetClearEachIteration() throws Exception {
		assertFalse("Should default not to clear after each iteration.", this.cacheManager.getClearEachIteration());
		this.cacheManager.setClearEachIteration(true);
		assertTrue("Should be settable to clear after each iteration.", this.cacheManager.getClearEachIteration());
		this.cacheManager.setClearEachIteration(false);
		assertFalse("Should be settable not to clear after each iteration.", this.cacheManager.getClearEachIteration());
	}

	public void testSaveDetailsWithEmptySampleResultGivesNoCacheEntry() throws Exception {
		saveDetailsWithConnectionAndSampleResultWithResponseCode("");
		assertTrue("Saving details with empty SampleResult should not make cache entry.", getThreadCache().isEmpty());
	}

	public void testSaveDetailsURLConnectionWithSampleResultWithResponseCode200GivesCacheEntry() throws Exception {
		saveDetailsWithConnectionAndSampleResultWithResponseCode("200");
		CacheManager.CacheEntry cacheEntry = getThreadCacheEntry(this.url.toString());
		assertNotNull("Saving details with SampleResult & connection with 200 response should make cache entry.", cacheEntry);
		assertNotNull("Saving details with SampleResult & connection with 200 response should make cache entry with an etag.", cacheEntry.getEtag());
		assertNotNull("Saving details with SampleResult & connection with 200 response should make cache entry with last modified date.", cacheEntry.getLastModified());
	}

	public void testSaveDetailsHttpMethodWithSampleResultWithResponseCode200GivesCacheEntry() throws Exception {
		saveDetailsWithHttpMethodAndSampleResultWithResponseCode("200");
		CacheManager.CacheEntry cacheEntry = getThreadCacheEntry(this.httpMethod.getURI().toString());
		assertNotNull("Saving SampleResult with HttpMethod & 200 response should make cache entry.", cacheEntry);
		assertNull("Saving details with SampleResult & HttpMethod with 200 response should make cache entry with no etag.", cacheEntry.getEtag());
		assertNull("Saving details with SampleResult & HttpMethod with 200 response should make cache entry with no last modified date.", cacheEntry.getLastModified());
	}

	public void testSaveDetailsURLConnectionWithSampleResultWithResponseCode404GivesNoCacheEntry() throws Exception {
		saveDetailsWithConnectionAndSampleResultWithResponseCode("404");
		assertNull("Saving details with SampleResult & connection with 404 response should not make cache entry.", getThreadCacheEntry(url.toString()));
	}

	public void testSaveDetailsHttpMethodWithSampleResultWithResponseCode404GivesNoCacheEntry() throws Exception {
		saveDetailsWithHttpMethodAndSampleResultWithResponseCode("404");
		assertNull("Saving SampleResult with HttpMethod & 404 response should not make cache entry.", getThreadCacheEntry(this.httpMethod.getPath()));
	}

	public void testSetHeadersHttpMethodWithSampleResultWithResponseCode200GivesCacheEntry() throws Exception {
		this.httpMethod.setURI(this.uri);
		this.httpMethod.addRequestHeader(new Header(HTTPConstantsInterface.IF_MODIFIED_SINCE, this.currentTimeInGMT, false));
		this.httpMethod.addRequestHeader(new Header(HTTPConstantsInterface.ETAG, EXPECTED_ETAG, false));
		saveDetailsWithHttpMethodAndSampleResultWithResponseCode("200");
		setFieldInCacheEntry(getThreadCacheEntry(this.httpMethod.getURI().toString()), "etag", EXPECTED_ETAG);
		setHeadersWithUrlAndHttpMethod();
		checkRequestHeader(HTTPConstantsInterface.IF_NONE_MATCH, EXPECTED_ETAG);
		checkRequestHeader(HTTPConstantsInterface.IF_MODIFIED_SINCE, this.currentTimeInGMT);
	}

	public void testSetHeadersHttpMethodWithSampleResultWithResponseCode404GivesNoCacheEntry() throws Exception {
		this.httpMethod.setURI(this.uri);
		saveDetailsWithHttpMethodAndSampleResultWithResponseCode("404");
		setHeadersWithUrlAndHttpMethod();
		assertNull("Saving SampleResult with HttpMethod & 404 response should not make cache entry.", getThreadCacheEntry(this.httpMethod.getPath()));
	}

	public void testSetHeadersHttpURLConnectionWithSampleResultWithResponseCode200GivesCacheEntry() throws Exception {
		saveDetailsWithConnectionAndSampleResultWithResponseCode("200");
		CacheManager.CacheEntry cacheEntry = getThreadCacheEntry(httpUrlConnection.getURL().toString());
		setFieldInCacheEntry(cacheEntry, "etag", EXPECTED_ETAG);
		setFieldInCacheEntry(cacheEntry, "lastModified", this.currentTimeInGMT);
		setHeadersWithHttpUrlConnectionAndUrl();
		Map<String, List<String>> properties = this.httpUrlConnection.getRequestProperties();
		checkProperty(properties, HTTPConstantsInterface.IF_NONE_MATCH, EXPECTED_ETAG);
		checkProperty(properties, HTTPConstantsInterface.IF_MODIFIED_SINCE, this.currentTimeInGMT);
	}

	public void testSetHeadersHttpURLConnectionWithSampleResultWithResponseCode404GivesNoCacheEntry() throws Exception {
		saveDetailsWithConnectionAndSampleResultWithResponseCode("404");
		setHeadersWithHttpUrlConnectionAndUrl();
		assertNull("Saving SampleResult with HttpMethod & 404 response should not make cache entry.", getThreadCacheEntry(this.url.toString()));
	}

	public void testClearCache() throws Exception {
		assertTrue("ThreadCache should be empty initially.", getThreadCache().isEmpty());
		saveDetailsWithHttpMethodAndSampleResultWithResponseCode("200");
		assertFalse("ThreadCache should be populated after saving details for HttpMethod with SampleResult with response code 200.", getThreadCache().isEmpty());
		this.cacheManager.clear();
		assertTrue("ThreadCache should be emptied by call to clear.", getThreadCache().isEmpty());
	}

	private void checkRequestHeader(String requestHeader, String expectedValue) {
		Header header = this.httpMethod.getRequestHeader(requestHeader);
		assertEquals("Wrong name in header for " + requestHeader, requestHeader, header.getName());
		assertEquals("Wrong value for header " + header, expectedValue, header.getValue());
	}

	private static void checkProperty(Map<String, List<String>> properties, String property, String expectedPropertyValue) {
		assertNotNull("Properties should not be null. Expected to find within it property = " + property + " with expected value = " + expectedPropertyValue, properties);
		List<String> listOfPropertyValues = properties.get(property);
		assertNotNull("No property entry found for property " + property, listOfPropertyValues);
		assertEquals("Did not find single property for property " + property, 1, listOfPropertyValues.size());
		assertEquals("Unexpected value for property " + property, expectedPropertyValue, listOfPropertyValues.get(0));
	}

	private SampleResult getSampleResultWithSpecifiedResponseCode(String code) {
		SampleResult sampleResult = new SampleResult();
		sampleResult.setResponseCode(code);
		return sampleResult;
	}

	@SuppressWarnings("unchecked")
	private Map<String, CacheManager.CacheEntry> getThreadCache() throws Exception {
		Field threadLocalfield = CacheManager.class.getDeclaredField("threadCache");
		threadLocalfield.setAccessible(true);
		ThreadLocal<Map<String, CacheEntry>> threadLocal = (ThreadLocal<Map<String, CacheManager.CacheEntry>>) threadLocalfield.get(this.cacheManager);
		return (Map<String, CacheManager.CacheEntry>) threadLocal.get();
	}

	private CacheManager.CacheEntry getThreadCacheEntry(String url) throws Exception {
		return (CacheManager.CacheEntry) getThreadCache().get(url);
	}

	private void saveDetailsWithHttpMethodAndSampleResultWithResponseCode(String responseCode) throws Exception {
		SampleResult sampleResult = getSampleResultWithSpecifiedResponseCode(responseCode);
		this.cacheManager.saveDetails(this.httpMethod, sampleResult);
	}

	private void saveDetailsWithConnectionAndSampleResultWithResponseCode(String responseCode) {
		SampleResult sampleResult = getSampleResultWithSpecifiedResponseCode(responseCode);
		this.cacheManager.saveDetails(this.urlConnection, sampleResult);
	}

	private void setFieldInCacheEntry(CacheManager.CacheEntry cacheEntry, String fieldName, String expectedValue) throws Exception {
		Field field = CacheManager.CacheEntry.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(cacheEntry, expectedValue);
	}

	private void setHeadersWithHttpUrlConnectionAndUrl() {
		this.cacheManager.setHeaders(this.httpUrlConnection, this.url);
	}

	private void setHeadersWithUrlAndHttpMethod() {
		this.cacheManager.setHeaders(this.url, this.httpMethod);
	}
}
