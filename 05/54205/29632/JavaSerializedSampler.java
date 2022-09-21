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

package org.apache.jmeter.protocol.http.sampler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.io.IOUtils;
import org.apache.jmeter.protocol.http.control.CacheManager;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;

import com.thoughtworks.xstream.XStream;

/**
 * Commons HTTPClient based Java Serialization sampler
 */
public class JavaSerializedSampler extends HTTPSampler2 implements Interruptible { // Implemented by parent class
    private static final Logger log = LoggingManager.getLoggerForClass();

    private static final long serialVersionUID = 240L;
    
    public static final String URL_DATA = "JavaSerializedSampler.url";//$NON-NLS-1$
    
    public static final String XSTREAM_DATA="JavaSerializedSampler.xstream_data"; //$NON-NLS-1$
    
    private static final String ENCODING = "utf-8"; //$NON-NLS-1$ 

    public void setUrlData(String data){
        setProperty(URL_DATA,data);
    }
    
    public String getUrlData(){
        return getPropertyAsString(URL_DATA);
    }
    
    public void setXstreamData(String data){
        setProperty(XSTREAM_DATA,data);
    }
    
    public String getXstreamData(){
        return getPropertyAsString(XSTREAM_DATA);
    }
    
    protected void setPostHeaders(PostMethod post) {
        int length=0;// Take length from file
        if (getHeaderManager() != null) {
            // headerManager was set, so let's set the connection
            // to use it.
            HeaderManager mngr = getHeaderManager();
            int headerSize = mngr.size();
            for (int idx = 0; idx < headerSize; idx++) {
                Header hd = mngr.getHeader(idx);
                if (HTTPConstants.HEADER_CONTENT_LENGTH.equalsIgnoreCase(hd.getName())) {// Use this to override file length
                    length = Integer.parseInt(hd.getValue());
                }
                // All the other headers are set up by HTTPSampler2.setupConnection()
            }
        } 
    }
    
    /**
     * Send POST data from <code>Entry</code> to the open connection.
     *
     * @param post
     * @throws IOException if an I/O exception occurs
     */
    private String sendPostData(PostMethod post) {
        // Buffer to hold the post body, except file content
        StringBuilder postedBody = new StringBuilder(1000);
        XStream xstream = new XStream(new DomDriver());
        Object o= xstream.fromXML(getXstreamData());
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos=null;
        byte[] serialized_data = null;
        try{
            oos=new ObjectOutputStream(bao); 
            oos.writeObject(o);
            serialized_data = bao.toByteArray();
            postedBody.append(serialized_data);
            post.setRequestEntity(new ByteArrayRequestEntity(serialized_data));
        } catch(IOException e){
            
        }
        return postedBody.toString();
    }

    
    protected HTTPSampleResult sample(URL url, String method, boolean areFollowingRedirect, int frameDepth){
        String urlStr = url.toString();

        log.debug("Start : sample " + urlStr);

        PostMethod httpMethod;
        httpMethod = new PostMethod(urlStr);

        HTTPSampleResult res = new HTTPSampleResult();
        res.setMonitor(false);

        res.setSampleLabel(urlStr); // May be replaced later
        res.setHTTPMethod(HTTPConstants.POST);
        res.setURL(url);
        res.sampleStart(); // Count the retries as well in the time
        HttpClient client = null;
        InputStream instream = null;
        try {
            setPostHeaders(httpMethod);
            client = setupConnection(url, httpMethod, res);
            setSavedClient(client);
            res.setQueryString(sendPostData(httpMethod));
            int statusCode = client.executeMethod(httpMethod);
            // Some headers are set by executeMethod()
            res.setRequestHeaders(getConnectionHeaders(httpMethod));

            // Request sent. Now get the response:
            instream = httpMethod.getResponseBodyAsStream();

            if (instream != null) {// will be null for HEAD

                org.apache.commons.httpclient.Header responseHeader = httpMethod.getResponseHeader(HTTPConstants.HEADER_CONTENT_ENCODING);
                if (responseHeader != null && HTTPConstants.ENCODING_GZIP.equals(responseHeader.getValue())) {
                    instream = new GZIPInputStream(instream);
                }

                //int contentLength = httpMethod.getResponseContentLength();Not visible ...
                //TODO size ouststream according to actual content length
                ByteArrayOutputStream outstream = new ByteArrayOutputStream(4 * 1024);
                //contentLength > 0 ? contentLength : DEFAULT_INITIAL_BUFFER_SIZE);
                byte[] buffer = new byte[4096];
                int len;
                boolean first = true;// first response
                while ((len = instream.read(buffer)) > 0) {
                    if (first) { // save the latency
                        res.latencyEnd();
                        first = false;
                    }
                    outstream.write(buffer, 0, len);
                }

                res.setResponseData(outstream.toByteArray());
                outstream.close();

            }

            res.sampleEnd();
            // Done with the sampling proper.

            // Now collect the results into the HTTPSampleResult:

            res.setSampleLabel(httpMethod.getURI().toString());
            // Pick up Actual path (after redirects)

            res.setResponseCode(Integer.toString(statusCode));
            res.setSuccessful(isSuccessCode(statusCode));

            res.setResponseMessage(httpMethod.getStatusText());

            // Set up the defaults (may be overridden below)
            res.setDataEncoding(ENCODING);
            String ct = null;
            org.apache.commons.httpclient.Header h
                    = httpMethod.getResponseHeader(HTTPConstants.HEADER_CONTENT_TYPE);
            if (h != null)// Can be missing, e.g. on redirect
            {
                ct = h.getValue();
                res.setContentType(ct);// e.g. text/html; charset=ISO-8859-1
                res.setEncodingAndType(ct);
            }

            res.setResponseHeaders(getResponseHeaders(httpMethod));
            if (res.isRedirect()) {
                res.setRedirectLocation(httpMethod.getResponseHeader(HTTPConstants.HEADER_LOCATION).getValue());
            }

            // If we redirected automatically, the URL may have changed
            if (getAutoRedirects()) {
                res.setURL(new URL(httpMethod.getURI().toString()));
            }

            // Store any cookies received in the cookie manager:
            saveConnectionCookies(httpMethod, res.getURL(), getCookieManager());

            // Save cache information
            final CacheManager cacheManager = getCacheManager();
            if (cacheManager != null){
                cacheManager.saveDetails(httpMethod, res);
            }

            // Follow redirects and download page resources if appropriate:
            res = resultProcessing(areFollowingRedirect, frameDepth, res);

            log.debug("End : sample");
            httpMethod.releaseConnection();
            return res;
        } catch (IllegalArgumentException e)// e.g. some kinds of invalid URL
        {
            res.sampleEnd();
            errorResult(e, res);
            return res;
        } catch (IOException e) {
            res.sampleEnd();
            errorResult(e, res);
            return res;
        } finally {
            JOrphanUtils.closeQuietly(instream);
            setSavedClient(null);
            httpMethod.releaseConnection();
        }   
    }
    
    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL(getUrlData());
    }
}
