package org.apache.jmeter.protocol.http.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.ConnectException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * Title:		Business Inference RuleML Editor<br>
 * Copyright:	Copyright (c) 2002<br>
 * Company:		Business Inference<br>
 * License:<br>
 * <br>
 * Stock license insert here.<br>
 * <br>
 * Description:<br>
 * For now I use DOM for WSDLHelper, but it would be
 * more efficient to use JAXB to generate an object
 * model for WSDL and use it to perform serialization
 * and deserialization. It also makes it easier to
 * traverse the WSDL to get necessary information.<br>
 * Author:	Peter Lin<br>
 * Version: 	0.1<br>
 * Created on:	Jun 3, 2003<br>
 * Last Modified:	6:18:12 AM<br>
 */

public class WSDLHelper {

	/**
	 * --------------------------------------------
	 * The members used by the class to do its work
	 * --------------------------------------------
	 */
	
	protected java.net.URL WSDLURL = null;
	protected HttpURLConnection CONN = null;
	protected org.w3c.dom.Document WSDLDOC = null;
	protected String SOAPBINDING = null;
	public String BINDNAME = null;
	protected Object[] SOAPOPS = null;
	protected HashMap ACTIONS = new HashMap();
	
	/**
	 * Default constructor takes a string URL
	 */
	public WSDLHelper(String url)
		throws MalformedURLException
			{
				try {
					WSDLURL = new URL(url);
				} catch (MalformedURLException exception){
					throw exception;
				}
			}

	/**
	 * Returns the URL
	 * @return
	 */
	public URL getURL(){
		return this.WSDLURL;
	}
	
	/**
	 * Returns the binding point for the webservice.
	 * Right now it naively assumes there's only one
	 * binding point with numerous soap operations.
	 * @return String
	 */
	public String getBinding(){
		try {
			NodeList services = this.WSDLDOC.getElementsByTagName("service");
			// the document should only have one service node
			// if it doesn't it may not work!
			Element node = (Element)services.item(0);
			NodeList ports = node.getElementsByTagName("port");
			for (int idx=0; idx < ports.getLength(); idx++){
				Element pnode = (Element)ports.item(idx);
				String portname = pnode.getAttribute("name");
				// got the port name. now check it against the
				// binding name.
				if (portname.equals(this.BINDNAME)){
					NodeList servlist = pnode.getElementsByTagName("soap:address");
					Element addr = (Element)servlist.item(0);
					this.SOAPBINDING = addr.getAttribute("location"); 
					return this.SOAPBINDING;
				}
			}
			return null;
		} catch (Exception exception){
			return null;
		}
	}
	
	/**
	 * Method is used internally to connect to the
	 * URL. It's protected; therefore external
	 * classes should use parse to get the resource
	 * at the given location.
	 * @throws IOException
	 */
	protected void connect()
		throws IOException
			{
				try {
					CONN = (HttpURLConnection)WSDLURL.openConnection();
				} catch (IOException exception){
					throw exception;
				}
			}

	/**
	 * We try to close the connection to make sure
	 * it doesn't hang around.
	 */
	protected void close(){
		try {
			CONN.getInputStream().close();
		} catch (Exception exception){
			// do nothing
		}
	}
	
	/**
	 * Method is used internally to parse the
	 * InputStream and build the document using
	 * javax.xml.parser API.
	 */
	protected void buildDocument()
		throws ParserConfigurationException, IOException, SAXException
			{
				try {
					DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docbuild = dbfactory.newDocumentBuilder();
					WSDLDOC = docbuild.parse(CONN.getInputStream());
				} catch (ParserConfigurationException exception){
					throw exception;
				} catch (IOException exception){
					throw exception;
				} catch (SAXException exception){
					throw exception;
				}
			}

	/**
	 * Call this method to retrieve the WSDL. This method
	 * must be called, otherwise a connection to the URL
	 * won't be made and the stream won't be parsed.
	 */
	public void parse()
		throws WSDLException
			{
				try {
					this.connect();
					this.buildDocument();
					SOAPOPS = this.getOperations();
					this.close();
				} catch (IOException exception){
					throw (new WSDLException(exception.getMessage()));
				} catch (Exception exception){
					throw (new WSDLException(exception.getMessage()));
				}
			}
	
	/**
	 * get a list of the web methods as a string
	 * array.
	 * @return
	 */
	public String[] getWebMethods(){
		for (int idx=0; idx < SOAPOPS.length; idx++){
			// get the node
			Node act = (Node)SOAPOPS[idx];
			// get the soap:operation
			NodeList opers = ((Element)act).getElementsByTagName("soap:operation");
			// there should only be one soap:operation node per operation
			Element op = (Element)opers.item(0);
			String value = op.getAttribute("soapAction");
			String key = ((Element)act).getAttribute("name");
			this.ACTIONS.put(key,value);
		}
		Set keys = this.ACTIONS.keySet();
		String[] stringmeth = new String[keys.size()];
		Object[] stringKeys = keys.toArray();
		System.arraycopy(stringKeys,0,stringmeth,0,keys.size());
		return stringmeth;
	}

	/**
	 * Return the soap action matching the operation name
	 * @param key
	 * @return
	 */
	public String getSoapAction(String key){
		return (String)this.ACTIONS.get(key);
	}
	
	/**
	 * get the wsdl document
	 * @return
	 */	
	public Document getWSDLDocument(){
		return WSDLDOC;
	}

	/**
	 * Method will look at the binding nodes and see
	 * if the first child is a soap:binding. If it
	 * is, it adds it to an array.
	 * @return Node[]
	 */
	public Object[] getSOAPBindings(){
		ArrayList list = new ArrayList();
		NodeList bindings = WSDLDOC.getElementsByTagName("binding");
		for (int idx=0; idx < bindings.getLength(); idx++){
			Element nd = (Element)bindings.item(idx);
			NodeList slist = nd.getElementsByTagName("soap:binding");
			if (slist.getLength() > 0){
				Element soapbind = (Element)slist.item(0);
				this.BINDNAME = nd.getAttribute("name");
				list.add(nd);
			}
		}
		if (list.size() > 0){
			return list.toArray();
		} else {
			return new Object[0];
		}
	}

	/**
	 * Look at the bindings with soap operations and
	 * get the soap operations. Since WSDL may describe
	 * multiple bindings and each binding may have
	 * multiple soap operations, we iterate through
	 * the binding nodes with a first child that is
	 * a soap binding. If a WSDL doesn't use the same
	 * formatting convention, it is possible we may
	 * not get a list of all the soap operations.
	 * If that is the case, getSOAPBindings() will
	 * need to be changed. I should double check the
	 * WSDL spec to see what the official requirement
	 * is. Another option is to get all operation
	 * nodes and check to see if the first child is
	 * a soap:operation. The benefit of not getting
	 * all operation nodes is WSDL could contain
	 * duplicate operations that are not SOAP
	 * methods. If there are a large number of
	 * methods and half of them are HTTP operations,
	 * getting all operations could slow things
	 * down.
	 * @return Node[]
	 */
	public Object[] getOperations(){
		Object[] res = this.getSOAPBindings();
		ArrayList ops = new ArrayList();
		// first we iterate through the bindings
		for (int idx=0; idx < res.length; idx++){
			Element one = (Element)res[idx];
			NodeList opnodes = one.getElementsByTagName("operation");
			// now we iterate through the operations
			for (int idz=0; idz < opnodes.getLength(); idz++){
				// if the first child is soap:operation
				// we add it to the array
				Element child = (Element)opnodes.item(idz);
				NodeList soapnode = child.getElementsByTagName("soap:operation");
				if (soapnode.getLength() > 0){
					ops.add(child);
				}
			}
		}
		return ops.toArray();	
	}

	/**
	 * Simple test for the class uses bidbuy.wsdl from
	 * Apache's soap driver examples.
	 * @param args
	 */	
	public static void main(String[] args) {
		try {
			WSDLHelper help = new WSDLHelper("http://localhost/testWS/Service1.asmx?WSDL");
			long start = System.currentTimeMillis();
			help.parse();
			String[] methods = help.getWebMethods();
			System.out.println("el: " + (System.currentTimeMillis() - start));
			for (int idx=0; idx < methods.length; idx++){
				System.out.println("method name: " + methods[idx]);
			}
			System.out.println("service url: " + help.getBinding());
		} catch (Exception exception){
			System.out.println("main method catch:");
			exception.printStackTrace();
		}
	}
}
