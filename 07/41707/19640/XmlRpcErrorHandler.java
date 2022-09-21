Index: src/protocol/http/org/apache/jmeter/protocol/http/sampler/XmlRpcErrorHandler.java
===================================================================
--- src/protocol/http/org/apache/jmeter/protocol/http/sampler/XmlRpcErrorHandler.java	(revision 0)
+++ src/protocol/http/org/apache/jmeter/protocol/http/sampler/XmlRpcErrorHandler.java	(revision 0)
@@ -0,0 +1,24 @@
+package org.apache.jmeter.protocol.http.sampler;
+
+import java.util.Stack;
+
+import org.xml.sax.SAXParseException;
+import org.xml.sax.helpers.DefaultHandler;
+
+public class XmlRpcErrorHandler extends DefaultHandler {
+
+  private Stack parseErrors = null;
+  
+  public SAXParseException[] getParseErrors() {    
+    if (parseErrors == null)
+      parseErrors = new Stack();
+    return (SAXParseException[])parseErrors.toArray(new SAXParseException[0]);
+  }
+  
+  public void error(SAXParseException e) {
+    if (parseErrors == null)
+      parseErrors = new Stack();
+    parseErrors.push(e);
+  }
+  
+}
