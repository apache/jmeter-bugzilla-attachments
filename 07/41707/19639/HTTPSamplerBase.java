Index: src/protocol/http/org/apache/jmeter/protocol/http/sampler/HTTPSamplerBase.java
===================================================================
--- src/protocol/http/org/apache/jmeter/protocol/http/sampler/HTTPSamplerBase.java	(revision 511863)
+++ src/protocol/http/org/apache/jmeter/protocol/http/sampler/HTTPSamplerBase.java	(working copy)
@@ -16,6 +16,7 @@
  */
 package org.apache.jmeter.protocol.http.sampler;
 
+import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.PrintStream;
@@ -52,6 +53,8 @@
 import org.apache.jorphan.logging.LoggingManager;
 import org.apache.jorphan.util.JOrphanUtils;
 import org.apache.log.Logger;
+import org.apache.xerces.parsers.DOMParser;
+import org.xml.sax.InputSource;
 
 /**
  * Common constants and methods for HTTP samplers
@@ -229,6 +232,10 @@
         }
     }
     
+    //variables used for xml-rpc call handling
+    public static DOMParser xmlRpcParser = null;
+    public static XmlRpcErrorHandler xmlRpcErrorHandler = null;
+    
     ////////////////////// Variables //////////////////////
     
     private boolean dynamicPath = false;// Set false if spaces are already encoded
@@ -396,6 +403,12 @@
 		this.getArguments().addArgument(arg);
 	}
 
+    public void addUnencodedArgument(String name, String value, String metadata) {
+        HTTPArgument arg = new HTTPArgument(name, value, metadata, false);
+        arg.setAlwaysEncoded(false);
+        this.getArguments().addArgument(arg);
+    }
+    
 	public void addArgument(String name, String value) {
 		this.getArguments().addArgument(new HTTPArgument(name, value));
 	}
@@ -618,40 +631,44 @@
 	 * 
 	 */
 	public void parseArguments(String queryString) {
-		String[] args = JOrphanUtils.split(queryString, QRY_SEP);
-		for (int i = 0; i < args.length; i++) {
-			// need to handle four cases: 
-            // - string contains name=value
-			// - string contains name=
-			// - string contains name
-			// - empty string
-            
-			String metaData; // records the existance of an equal sign
-            String name;
-            String value;
-            int length = args[i].length();
-            int endOfNameIndex = args[i].indexOf(ARG_VAL_SEP);
-            if (endOfNameIndex != -1) {// is there a separator?
-				// case of name=value, name=
-				metaData = ARG_VAL_SEP;
-                name = args[i].substring(0, endOfNameIndex);
-                value = args[i].substring(endOfNameIndex + 1, length);
-			} else {
-				metaData = "";
-                name=args[i];
-                value="";
-			}
-			if (name.length() > 0) {
-                // The browser has already done the encoding, so save the values as is 
-                HTTPArgument arg = new HTTPArgument(name, value, metaData, false);
-                // and make sure they stay that way:
-                arg.setAlwaysEncoded(false);
-                // Note that URL.encode()/decode() do not follow RFC3986 entirely
-				this.getArguments().addArgument(arg);
-				// TODO: this leaves the arguments in encoded form, which may be difficult to read
-                // if we can find proper coding methods, this could be tidied up 
-            }
-		}
+        if (isXmlRpc(queryString)) {
+            addUnencodedArgument("", queryString, "");
+        } else {      
+    		String[] args = JOrphanUtils.split(queryString, QRY_SEP);
+    		for (int i = 0; i < args.length; i++) {
+    			// need to handle four cases: 
+                // - string contains name=value
+    			// - string contains name=
+    			// - string contains name
+    			// - empty string
+                
+    			String metaData; // records the existance of an equal sign
+                String name;
+                String value;
+                int length = args[i].length();
+                int endOfNameIndex = args[i].indexOf(ARG_VAL_SEP);
+                if (endOfNameIndex != -1) {// is there a separator?
+    				// case of name=value, name=
+    				metaData = ARG_VAL_SEP;
+                    name = args[i].substring(0, endOfNameIndex);
+                    value = args[i].substring(endOfNameIndex + 1, length);
+    			} else {
+    				metaData = "";
+                    name=args[i];
+                    value="";
+    			}
+    			if (name.length() > 0) {
+                    // The browser has already done the encoding, so save the values as is 
+                    HTTPArgument arg = new HTTPArgument(name, value, metaData, false);
+                    // and make sure they stay that way:
+                    arg.setAlwaysEncoded(false);
+                    // Note that URL.encode()/decode() do not follow RFC3986 entirely
+    				this.getArguments().addArgument(arg);
+    				// TODO: this leaves the arguments in encoded form, which may be difficult to read
+                    // if we can find proper coding methods, this could be tidied up 
+                }
+    		}
+        }
 	}
 
 	public String toString() {
@@ -1009,5 +1026,33 @@
     public static boolean isSecure(URL url){
         return isSecure(url.getProtocol());
     }
+    
+    /**
+     * check for xmlrpc request using xsd from http://www.ibiblio.org/xml/books/xmljava/chapters/ch02s05.html (no official xsd exists)
+     * 
+     * @param queryString
+     * @return
+     */
+    private boolean isXmlRpc(String queryString) {
+      try {        
+        if (!queryString.startsWith("<?xml"))
+          return false;
+        
+        if (xmlRpcParser == null) {
+          xmlRpcParser = new DOMParser();
+          xmlRpcParser.setFeature("http://xml.org/sax/features/validation", true);
+          xmlRpcParser.setFeature("http://apache.org/xml/features/validation/schema", true);
+          xmlRpcParser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "xml-rpc.xsd");        
+          xmlRpcErrorHandler = new XmlRpcErrorHandler();
+          xmlRpcParser.setErrorHandler(xmlRpcErrorHandler);
+        }
+        xmlRpcParser.parse(new InputSource(new ByteArrayInputStream(queryString.getBytes())));
+        return xmlRpcErrorHandler.getParseErrors().length == 0;
+      } catch (Exception e) {
+        log.debug("isXmlRpc() " + e);
+      }      
+      return false;
+    }
+    
 }
 
