package org.apache.jmeter.protocol.http.sampler;

import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.apache.jorphan.io.TextFile;

import org.apache.soap.util.xml.*;
import org.apache.jmeter.gui.JMeterFileFilter;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.soap.*;
import org.apache.soap.messaging.*;
import org.apache.soap.transport.*;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.xml.sax.*;
import org.apache.jorphan.io.TextFile;

/**
 * Sampler to handle Web Service requests. It uses Apache
 * soap drivers to perform the XML generation, connection
 * soap encoding and other soap functions.
 *
 * @author Peter Lin
 * @version $Id: 
 */
public class WebServiceSampler extends HTTPSampler
{
	public static final String XML_DATA = "HTTPSamper.xml_data";
	public static final String SOAP_ACTION = "Soap.Action";
	public static final String XML_DATA_FILE = "WebServiceSampler.xml_data_file";
	public static final String XML_PATH_LOC = "WebServiceSampler.xml_path_loc";
	public String SOAPACTION = null;
	transient SampleResult RESULT = null;
	protected Document XMLMSG = null;
	private int FILE_COUNT = -1;
	private File[] FILE_LIST = null;
	private Random RANDOM = new Random();

	/**
	 * Set the path where XML messages are
	 * stored for random selection.
	 */
	public void setXmlPathLoc(String path){
		setProperty(XML_PATH_LOC,path);
	}
	
	/**
	 * Get the path where XML messages are
	 * stored. this is the directory where
	 * JMeter will randomly select a file.
	 */
	public String getXmlPathLoc(){
		return getPropertyAsString(XML_PATH_LOC);
	}
	
	/**
	 * it's kinda obvious, but we state it anyways.
	 * Set the xml file with a string path.
	 * @param String filename
	 */
	public void setXmlFile(String filename)
	{
		setProperty(XML_DATA_FILE,filename);
	}
    
    /**
     * Get the file location of the xml file.
     * @return String file path.
     */
	public String getXmlFile()
	{
		return getPropertyAsString(XML_DATA_FILE);
	}

	/**
	 * Method uses jorphan TextFile class to load
	 * the contents of the file. I wonder if we should
	 * cache the DOM Document to save on parsing
	 * the message. Parsing XML is CPU intensive, so
	 * it could restrict the number of threads a
	 * test plan can run effectively. To cache the
	 * documents, it may be good to have an external
	 * class to provide caching that is efficient.
	 * We could just use a HashMap, but for large
	 * tests, it will be slow. Ideally, the cache
	 * would be indexed, so that large tests will
	 * run efficiently.
	 * @return String contents of the file
	 */
	private String retrieveRuntimeXmlData()
	{
		String file = getRandomFileName();
		if(file.length() > 0)
		{
			TextFile contents = new TextFile(file);
			if(contents.exists())
			{
				return contents.getText();
			}
		}
		return getXmlData();
	}

	/**
	 * Method is used internally to check if a random
	 * file should be used for the message. Messages
	 * must be valid. This is one way to load test
	 * with different messages. The limitation of this
	 * approach is parsing XML takes CPU resources,
	 * so it could affect JMeter GUI responsiveness.
	 */
	protected String getRandomFileName(){
		if (this.getXmlPathLoc() != null){
			File src = new File(this.getXmlPathLoc());
			if (src.isDirectory() && src.list() != null) {
				FILE_LIST = src.listFiles(new JMeterFileFilter(new String[]{".xml"}));
				this.FILE_COUNT = FILE_LIST.length;
				File one = FILE_LIST[RANDOM.nextInt(FILE_COUNT)];
				// return the absolutePath of the file
				return one.getAbsolutePath();
			} else {
				return getXmlFile();
			}
		} else {
			return getXmlFile();
		}
	}
	
	/**
	 * set the XML data
	 * @param String data
	 */
	public void setXmlData(String data)
	{
		setProperty(XML_DATA,data);
	}

	/**
	 * get the XML data as a string
	 * @return String data
	 */
	public String getXmlData()
	{
		return getPropertyAsString(XML_DATA);
	}

	/**
	 * set the soap action which should be in
	 * the form of an URN
	 * @param String data
	 */
	public void setSoapAction(String data){
		setProperty(SOAP_ACTION,data);
	}

	/**
	 * return the soap action string
	 * @return
	 */
	public String getSoapAction() {
		return getPropertyAsString(SOAP_ACTION);
	}

	/**
	 * This method uses Apache soap util to create
	 * the proper DOM elements.
	 * @return Element
	 */	
	public org.w3c.dom.Element createDocument(){
		String xmlData = retrieveRuntimeXmlData();
		if (xmlData != null && xmlData.length() > 0){	
			try {
				DocumentBuilder xdb = XMLParserUtils.getXMLDocBuilder();
				Document doc = xdb.parse(new InputSource(new StringReader(xmlData)));
				return doc.getDocumentElement();
			} catch (Exception ex){
				// ex.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * sample(Entry e) simply calls sample().
	 * @param Entry e
	 */
	public SampleResult sample(Entry e) {
		return sample();
	}

	/**
	 * 
	 */
	public SampleResult sample() {
		RESULT = new SampleResult();
		sampleWithApache();
		return RESULT;
	}

	/**
	 * Sample the URL using Apache SOAP driver.
	 */
	public void sampleWithApache() {
		try {
			Envelope msgEnv = Envelope.unmarshall(createDocument());

			// send the message
			Message msg = new Message ();
			long start = System.currentTimeMillis();
			msg.send(this.getUrl(), this.getSoapAction(), msgEnv);
			RESULT.setTime(System.currentTimeMillis() - start);

			SOAPTransport st = msg.getSOAPTransport ();
			BufferedReader br = st.receive();
			StringBuffer buf = new StringBuffer();
			String line;
			while((line = br.readLine()) != null){
				buf.append(line);
			}
			RESULT.setResponseMessage(buf.toString());
			RESULT.setSuccessful(true);
			// reponse code doesn't really apply, since
			// the soap driver doesn't provide a
			// response code
		} catch (Exception exception){
			// exception.printStackTrace();
			RESULT.setSuccessful(false);
		}
	}

	/**
	 * We override this to prevent the wrong encoding
	 * and provide no implementation. We want to
	 * reuse the other parts of HTTPSampler, but not
	 * the connection. The connection is handled by
	 * the Apache SOAP driver.
	 */
	public void addEncodedArgument(String name, String value, String metaData)	{
	}

	/**
	 * We override this to prevent the wrong encoding
	 * and provide no implementation. We want to
	 * reuse the other parts of HTTPSampler, but not
	 * the connection. The connection is handled by
	 * the Apache SOAP driver.
	 */
	protected HttpURLConnection setupConnection(URL u, String method)
		throws IOException
	{
		return null;
	}
	
	/**
	 * We override this to prevent the wrong encoding
	 * and provide no implementation. We want to
	 * reuse the other parts of HTTPSampler, but not
	 * the connection. The connection is handled by
	 * the Apache SOAP driver.
	 */
	protected long connect() throws IOException
	{
		return -1;
	}

}

