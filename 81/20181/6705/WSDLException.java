package org.apache.jmeter.protocol.http.util;

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
 * Author:	Peter Lin<br>
 * Version: 	0.1<br>
 * Created on:	Jun 3, 2003<br>
 * Last Modified:	6:37:53 AM<br>
 */

public class WSDLException extends Exception {

	/**
	 * 
	 */
	public WSDLException() {
		super();
	}

	/**
	 * @param message
	 */
	public WSDLException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public WSDLException(Exception exception) {
		super(exception.getMessage());
		exception.printStackTrace();
	}

	public static void main(String[] args) {
	}
}
