// $Header: /home/cvs/jakarta-jmeter/src/protocol/http/org/apache/jmeter/protocol/http/sampler/SoapSampler.java,v 1.11 2004/02/12 00:29:49 sebb Exp $
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.Header;

/**
 * Sampler to handle SOAP Requests.
 *
 * @author Jordi Salvat i Alabart
 * @version $Id: SoapSampler.java,v 1.11 2004/02/12 00:29:49 sebb Exp $
 */
public class SoapSampler extends HTTPSampler
{
    private static Logger log = LoggingManager.getLoggerForClass();
    public static final String XML_DATA = "HTTPSamper.xml_data";
    public static final String URL_DATA = "SoapSampler.URL_DATA";

    public void setXmlData(String data)
    {
        setProperty(XML_DATA, data);
    }

    public String getXmlData()
    {
        return getPropertyAsString(XML_DATA);
    }

    public String getURLData()
    {
        return getPropertyAsString(URL_DATA);
    }

    public void setURLData(String url)
    {
        setProperty(URL_DATA, url);
    }

    /**
     * Set the HTTP request headers in preparation to open the connection
     * and sending the POST data.
     *
     * @param connection       <code>URLConnection</code> to set headers on
     * @exception IOException  if an I/O exception occurs
     */
    public void setPostHeaders(URLConnection connection) throws IOException
    {
        ((HttpURLConnection) connection).setRequestMethod("POST");
        connection.setRequestProperty(
            "Content-Length",
            "" + getXmlData().length());
        // my first attempt at fixing the bug failed, due to user
        // error on my part. HeaderManager does not use the normal
        // setProperty, and getPropertyAsString methods. Instead,
        // it uses it's own String array and Header object.
        if (getHeaderManager() != null){
        		// headerManager was set, so let's set the connection
        		// to use it.
        		HeaderManager mngr = getHeaderManager();
        		int headerSize = mngr.size();
        		// we set all the header properties
        		for (int idx=0; idx < headerSize; idx++){
        			Header hd = mngr.getHeader(idx);
					connection.setRequestProperty(hd.getName(),hd.getValue());
        		}
        } else {
        	// otherwise we use "text/xml" as the default
			connection.setRequestProperty("Content-type", "text/xml");
        }
        connection.setDoOutput(true);
    }

    /**
     * Send POST data from <code>Entry</code> to the open connection.
     *
     * @param connection      <code>URLConnection</code> of where POST data
     *                        should be sent
     * @exception IOException if an I/O exception occurs
     */
    public void sendPostData(URLConnection connection) throws IOException
    {
        PrintWriter out = new PrintWriter(connection.getOutputStream());
        out.print(getXmlData());
        out.close();
    }
    
    /* (non-Javadoc)
     * @see Sampler#sample(Entry)
     */
    public SampleResult sample(Entry e)
    {
        try
        {
            URL url = new URL(getURLData());
            setDomain(url.getHost());
            setPort(url.getPort());
            setProtocol(url.getProtocol());
            setMethod(POST);
            if (url.getQuery() != null && url.getQuery().compareTo("") != 0)
            {
                setPath(url.getPath() + "?" + url.getQuery());
            }
            else
            {
                setPath(url.getPath());
            }
            // make sure the Post header is set
            URLConnection conn = url.openConnection();
            setPostHeaders(conn);
        }
        catch (MalformedURLException e1)
        {
            log.error("Bad url: " + getURLData(), e1);
        }
        catch (IOException e1){
			log.error("Bad url: " + getURLData(), e1);
        }
        return super.sample(e);
    }

    public String toString()
    {
        try
        {
            String xml = getXmlData();
            if (xml.length() > 100)
            {
                xml = xml.substring(0, 100);
            }
            return this.getUrl().toString() + "\nXML Data: " + xml;
        }
        catch (MalformedURLException e)
        {
            return "";
        }
    }
}
