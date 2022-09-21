--- ViewResultsFullVisualizer.java	2005-04-24 20:47:59.240912400 -0700
+++ ViewResultsFullVisualizer.java.new	2005-04-24 20:57:34.069037400 -0700
@@ -18,6 +18,7 @@
 package org.apache.jmeter.visualizers;
 import javax.swing.tree.DefaultMutableTreeNode;
 import org.w3c.dom.*;
+import org.xml.sax.ErrorHandler;
 import org.xml.sax.InputSource;
 import org.xml.sax.SAXException;
 import org.xml.sax.SAXParseException;
@@ -40,6 +41,7 @@
 import javax.swing.JCheckBox;
 import javax.swing.JEditorPane;
 import javax.swing.JLabel;
+import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JRadioButton;
 import javax.swing.JScrollPane;
@@ -66,7 +68,7 @@
 import javax.swing.tree.DefaultTreeModel;
 import javax.swing.tree.TreeSelectionModel;
 import javax.xml.parsers.*;
-
+import org.apache.jmeter.assertions.AssertionResult;
 import org.apache.jmeter.samplers.Clearable;
 import org.apache.jmeter.samplers.SampleResult;
 import org.apache.jmeter.util.JMeterUtils;
@@ -101,6 +103,8 @@
     private static final String XML_COMMAND = "xml"; // $NON-NLS-1$
     private static final String TEXT_COMMAND = "text"; // $NON-NLS-1$
     private boolean textMode = true;
+   //set default command to Text
+    private String command = TEXT_COMMAND; 
     
     // Keep copies of the two editors needed
     private static EditorKit customisedEditor = new LocalHTMLEditorKit();
@@ -109,7 +113,7 @@
 
     private DefaultMutableTreeNode root;
     private DefaultTreeModel treeModel;
-
+    
     private JTextPane stats;
     private JEditorPane results;
     private JScrollPane resultsScrollPane;
@@ -330,13 +334,15 @@
                     if ((SampleResult.TEXT).equals(res.getDataType())) // equals(null) is OK
                     {
 	                    String response = getResponseAsString(res);
-	                    if (textMode)
+	                    if (command.equals(TEXT_COMMAND))
 	                    {
 	                        showTextResponse(response);
 	                    }
-	                    else
+	                    else if(command.equals(HTML_COMMAND))
 	                    {
 	                        showRenderedResponse(response,res);
+	                    } else if(command.equals(XML_COMMAND)) {
+	                        showRenderXMLResponse(response);
 	                    }
                     }
                     else
@@ -388,43 +394,46 @@
         //there is duplicate Document class. Therefore I needed to declare the specific
         //class that I want
         org.w3c.dom.Document document = null;
+       
+        SAXErrorHandler saxErrorHandler = new SAXErrorHandler();
 		try {
 
 			DocumentBuilderFactory parserFactory = DocumentBuilderFactory
 					.newInstance();
 			parserFactory.setValidating(false);
 			parserFactory.setNamespaceAware(false);
-
+			
 			// create a parser:
 			DocumentBuilder parser = parserFactory.newDocumentBuilder();
 			
+			parser.setErrorHandler(saxErrorHandler);
 			document = parser.parse(new InputSource(new StringReader(response)));
-					
+				
 			
 			JPanel domTreePanel = new DOMTreePanel(document);
-			
 			document.normalize();
 			resultsScrollPane.setViewportView(domTreePanel);
 			
 		} 
 		  //TODO need to think of someway to notify the exception eror to user, when xml structure is not valid.
-		  //For right now I just logged it.
-		  catch (SAXParseException e) {
-		    log.error(e.getLocalizedMessage());
-			
-		} catch (SAXException e) {
-		    log.error(e.getLocalizedMessage());
+		  //For right now I just display a error message.
+		  catch (SAXParseException e) 
+		  {
+		      showErrorMessageDialog(saxErrorHandler.getErrorMessage(),saxErrorHandler.getMessageType());
+		      log.debug(saxErrorHandler.getErrorMessage());
+		  } catch (SAXException e) {
+		      showErrorMessageDialog(e.getMessage(),JOptionPane.ERROR_MESSAGE);
+		      log.debug(e.getMessage());
 		} catch (IOException e) {
-			log.error(e.getLocalizedMessage());
+		    showErrorMessageDialog(e.getMessage(),JOptionPane.ERROR_MESSAGE);
+			log.debug(e.getMessage());
 		} catch (ParserConfigurationException e) {
-			log.error(e.getLocalizedMessage());
+		    showErrorMessageDialog(e.getMessage(),JOptionPane.ERROR_MESSAGE);
+		    log.debug(e.getMessage());
 		}
 		textButton.setEnabled(true);
         htmlButton.setEnabled(true);
         xmlButton.setEnabled(true);
-        
-        
-        
     }
 
     private static String getResponseAsString(SampleResult res)
@@ -469,7 +478,7 @@
      */
     public void actionPerformed(ActionEvent e)
     {
-        String command = e.getActionCommand();
+        command = e.getActionCommand();
 
         if (command != null
             && (
@@ -482,8 +491,7 @@
             )
         {
 
-        //    textMode = command.equals(TEXT_COMMAND);
-
+            textMode = command.equals(TEXT_COMMAND);
             DefaultMutableTreeNode node =
                 (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
 
@@ -736,11 +744,13 @@
     
     
 
-    private static class LocalHTMLEditorKit extends HTMLEditorKit {
+    private static class LocalHTMLEditorKit extends HTMLEditorKit 
+    {
 
     	private static final ViewFactory defaultFactory = new LocalHTMLFactory();
     	
-    	public ViewFactory getViewFactory() {
+    	public ViewFactory getViewFactory() 
+    	{
     		return defaultFactory;
     	}
 
@@ -780,19 +790,20 @@
     * author <a href="mailto:d.maung@mdl.com">Dave Maung</a>
     * 
     */
-    public class XMLDefaultMutableTreeNode extends DefaultMutableTreeNode {
+    public class XMLDefaultMutableTreeNode extends DefaultMutableTreeNode 
+    {
 
     	boolean isRoot;
     	private Node xmlNode;
-    	public XMLDefaultMutableTreeNode(Node root) throws SAXException {
-    		
-    		
-    		super(root.getNodeName());
+    	public XMLDefaultMutableTreeNode(Node root) throws SAXException 
+    	{
+    	    super(root.getNodeName());
     		initRoot(root);
     		
     	}
     	
-    	public XMLDefaultMutableTreeNode(String name,Node xmlNode) {
+    	public XMLDefaultMutableTreeNode(String name,Node xmlNode) 
+    	{
     		super(name);
     		this.xmlNode = xmlNode;
     		
@@ -802,14 +813,16 @@
     	 * @param root
     	 * @throws SAXException
     	 */
-    	private void initRoot(Node root) throws SAXException {
+    	private void initRoot(Node root) throws SAXException 
+    	{
     		
     	
     		NodeList childNodes = root.getChildNodes();
     		if(childNodes == null) 
     			initAttributeNode(root, this);
     		
-    		for (int i = 0; i < childNodes.getLength(); i++) {
+    		for (int i = 0; i < childNodes.getLength(); i++) 
+    		{
     			Node childNode = childNodes.item(i);
     			initNode(childNode, this);
     		}
@@ -822,9 +835,11 @@
     	 * @throws SAXException
     	 */
     	private void initNode(Node node, XMLDefaultMutableTreeNode mTreeNode)
-    			throws SAXException {
+    			throws SAXException 
+    			{
 
-    		switch (node.getNodeType()) {
+    		switch (node.getNodeType())
+    		{
     		case Node.ELEMENT_NODE:
     			initElementNode(node, mTreeNode);
     			break;
@@ -864,7 +879,8 @@
 
     		mTreeNode.add(childTreeNode);
     		initAttributeNode(node, childTreeNode);
-    		for (int i = 0; i < childNodes.getLength(); i++) {
+    		for (int i = 0; i < childNodes.getLength(); i++) 
+    		{
     			Node childNode = childNodes.item(i);
     			initNode(childNode, childTreeNode);
     		}
@@ -879,7 +895,8 @@
     	private void initAttributeNode(Node node, DefaultMutableTreeNode mTreeNode)
     			throws SAXException {
     		NamedNodeMap nm = node.getAttributes();
-    		for (int i = 0; i < nm.getLength(); i++) {
+    		for (int i = 0; i < nm.getLength(); i++) 
+    		{
     			Attr nmNode = (Attr)nm.item(i);
     			String value = nmNode.getName() + " = \"" + nmNode.getValue() + "\"";
     			XMLDefaultMutableTreeNode attributeNode = new XMLDefaultMutableTreeNode(
@@ -896,7 +913,8 @@
     	 */
     	private void initCommentNode(Comment node, DefaultMutableTreeNode mTreeNode) throws SAXException{
     		String data = node.getData();
-    		if(data != null || data.length() > 0) {
+    		if(data != null || data.length() > 0)
+    		{
     			String value = "<!--" + node.getData() + "-->";
     			XMLDefaultMutableTreeNode commentNode = new XMLDefaultMutableTreeNode(value,node);
     			mTreeNode.add(commentNode);
@@ -908,9 +926,11 @@
     	 * @param mTreeNode
     	 * @throws SAXException
     	 */
-    	private void initCDATASectionNode(CDATASection node, DefaultMutableTreeNode mTreeNode) throws SAXException {
+    	private void initCDATASectionNode(CDATASection node, DefaultMutableTreeNode mTreeNode) throws SAXException 
+    	{
     		String data = node.getData();
-    		if(data != null || data.length() > 0) {
+    		if(data != null || data.length() > 0) 
+    		{
     			String value = "<!-[CDATA" + node.getData() + "]]>";
     			XMLDefaultMutableTreeNode commentNode = new XMLDefaultMutableTreeNode(value,node);
     			mTreeNode.add(commentNode);
@@ -922,9 +942,11 @@
     	 * @param mTreeNode
     	 * @throws SAXException
     	 */
-    	private void initTextNode(Text node, DefaultMutableTreeNode mTreeNode) throws SAXException {
+    	private void initTextNode(Text node, DefaultMutableTreeNode mTreeNode) throws SAXException 
+    	{
     		String text = node.getNodeValue().trim();
-    		if(text != null && text.length() > 0) {
+    		if(text != null && text.length() > 0) 
+    		{
     			XMLDefaultMutableTreeNode textNode = new XMLDefaultMutableTreeNode(node
     				.getNodeValue(),node);
     			mTreeNode.add(textNode);
@@ -937,7 +959,8 @@
     	 * get the xml node
     	 * @return
     	 */
-    	public Node getXMLNode() {
+    	public Node getXMLNode() 
+    	{
     		return xmlNode;
     	}
     	
@@ -958,7 +981,8 @@
     	private JTree domJTree;
     	public DOMTreePanel(org.w3c.dom.Document document) {
     		super(new GridLayout(1, 0));
-    		try {
+    		try 
+    		{
     		    
     		    Node firstElement = getFirstElement((org.w3c.dom.Document)document);
     			DefaultMutableTreeNode top = new XMLDefaultMutableTreeNode(
@@ -973,7 +997,8 @@
     			this.add(domJScrollPane);
     			this.setSize(800, 600);
     			this.setPreferredSize(new Dimension(800, 600));
-    		} catch (Exception e) {
+    		} catch (Exception e) 
+    		{
     			e.printStackTrace();
     		}
 
@@ -984,10 +1009,12 @@
     	 * @param root
     	 * @return
     	 */
-    	private Node getFirstElement(Node root) {
+    	private Node getFirstElement(Node root) 
+    	{
     		NodeList childNodes = root.getChildNodes();
     		Node toReturn = null;
-    		for (int i = 0; i < childNodes.getLength(); i++) {
+    		for (int i = 0; i < childNodes.getLength(); i++) 
+    		{
     			Node childNode = childNodes.item(i);
     			toReturn = childNode;
     			if (childNode.getNodeType() == Node.ELEMENT_NODE)
@@ -1001,4 +1028,91 @@
     	
     	
     }
+
+  private static void showErrorMessageDialog(String message,int messageType) {
+        JOptionPane.showMessageDialog(null, message, "Error", messageType); 
+   } 
+  
+  
+   //Helper method to construct SAX error details
+	private static String errorDetails(SAXParseException spe){
+		StringBuffer str = new StringBuffer(80);
+		int i;
+		i=spe.getLineNumber();
+		if (i != -1){
+			str.append("line=");
+			str.append(i);
+			str.append(" col=");
+			str.append(spe.getColumnNumber());
+			str.append(" ");
+		}
+		str.append(spe.getLocalizedMessage());
+		return str.toString();
+	}
+	
+	private class SAXErrorHandler implements ErrorHandler 
+    {
+        private String msg;
+        private int messageType;
+        public SAXErrorHandler()
+        {
+           msg = new String("");
+           
+        }
+
+		
+        public void error(SAXParseException exception)
+		        throws SAXParseException 
+        {
+            msg = "error: "+errorDetails(exception);
+			
+			log.debug(msg);
+			messageType = JOptionPane.ERROR_MESSAGE;
+            throw exception;
+        }
+
+		/*
+		 * Can be caused by:
+		 * - premature end of file
+		 * - non-whitespace content after trailer
+		*/
+        public void fatalError(SAXParseException exception)
+                throws SAXParseException 
+        {
+
+			msg="fatal: " + errorDetails(exception);
+			messageType = JOptionPane.ERROR_MESSAGE;			
+			log.debug(msg);
+			
+            throw exception;
+        }
+
+		/*
+		 * Not clear what can cause this
+         * ? conflicting versions perhaps
+		 */
+        public void warning(SAXParseException exception)
+                throws SAXParseException 
+        {
+             msg="warning: "+errorDetails(exception);
+			 log.debug(msg);
+			 messageType = JOptionPane.WARNING_MESSAGE;
+        }
+        /**
+         * get the JOptionPaneMessage Type
+         * @return
+         */
+        public int getMessageType() {
+            return messageType;
+        }
+        
+        /**
+         * get error message
+         * @return
+         */
+        public String getErrorMessage() {
+            return msg;
+        }
+    }
+    
 }
