package org.apache.jmeter.protocol.http.util;

import java.util.HashMap;

import org.w3c.dom.Document;

/**
 * Title:		Business Inference RuleML Editor<br>
 * Copyright:	Copyright (c) 2002<br>
 * Company:		Business Inference<br>
 * License:<br>
 * <br>
 * Stock license insert here.<br>
 * <br>
 * Description:<br>
 * <br>
 * The purpose of this class is to cache the DOM
 * Documents in memory and by-pass parsing. For
 * old systems or laptops, it's not practical to
 * parse the XML documents every time. Therefore
 * using a memory cache can reduce the CPU usage
 * .<p>
 * For now this is a simple version to test the
 * feasibility of caching. If it works, this
 * class will be replaced with an Apache commons
 * or something equivalent. If I was familiar
 * with Apache Commons Pool, I would probably
 * use it, but since I don't know the API, it
 * is quicker for Proof of Concept to just
 * write a dumb one. If the number documents
 * in the pool exceed several hundred, it will
 * take a long time for the lookup.
 * <p>
 * Author:	Peter Lin<br>
 * Version: 	0.1<br>
 * Created on:	Jun 17, 2003<br>
 * Last Modified:	4:59:07 PM<br>
 */

public class DOMPool {

	/**
	 * The cache is created with an initial size
	 * of 50. Running a webservice test on an
	 * old system will likely run into memory
	 * or CPU problems long before the HashMap
	 * is an issue.
	 */
	protected static HashMap MEMCACHE = new HashMap(50);

	/**
	 * return a document
	 * @param Object key
	 * @return Document
	 */	
	public static Document getDocument(Object key){
		return (Document)MEMCACHE.get(key);
	}

	/**
	 * add an object to the cache
	 * @param Object key
	 * @param Object data
	 */	
	public static void putDocument(Object key, Object data){
		MEMCACHE.put(key,data);
	}
	
}
