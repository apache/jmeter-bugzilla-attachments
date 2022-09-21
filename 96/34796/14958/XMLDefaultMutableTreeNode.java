/*
 * Created on May 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.apache.jmeter.visualizers;

import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *A extended class of DefaultMutableTreeNode except that it also attached
 *XML node and convert XML document into DefaultMutableTreeNode
 * author <a href="mailto:d.maung@mdl.com">Dave Maung</a>
 * 
 */
 public class XMLDefaultMutableTreeNode extends DefaultMutableTreeNode 
 {
    private static final int LIMIT_STR_SIZE = 100;
 	boolean isRoot;
 	private Node xmlNode;
 	public XMLDefaultMutableTreeNode(Node root) throws SAXException 
 	{
 	    super(root.getNodeName());
 	    initAttributeNode(root, this);
 		initRoot(root);
 		
 	}
 	
 	public XMLDefaultMutableTreeNode(String name,Node xmlNode) 
 	{
 		super(name);
 		this.xmlNode = xmlNode;
 		
 	}
 	/**
 	 * init root
 	 * @param root
 	 * @throws SAXException
 	 */
 	private void initRoot(Node xmlRoot) throws SAXException {
 		
 	
 		NodeList childNodes = xmlRoot.getChildNodes();
 		for (int i = 0; i < childNodes.getLength(); i++) {
 			Node childNode = childNodes.item(i);
 			initNode(childNode, this);
 		}

 	}
 	/**
 	 * init node
 	 * @param node
 	 * @param mTreeNode
 	 * @throws SAXException
 	 */
 	private void initNode(Node node, XMLDefaultMutableTreeNode mTreeNode)
 			throws SAXException 
 			{

 		switch (node.getNodeType())
 		{
 		case Node.ELEMENT_NODE:
 			initElementNode(node, mTreeNode);
 			break;
 			
 		case Node.TEXT_NODE:
 			 initTextNode((Text)node, mTreeNode);
 			break;
 			

 		case Node.CDATA_SECTION_NODE:
 			initCDATASectionNode((CDATASection)node, mTreeNode);
 			break;
 		case Node.COMMENT_NODE:
 			initCommentNode((Comment)node,mTreeNode);
 			break;
 		
 		default:
 		    //if other node type, we will just skip it
 			break;

 		}

 	}
 	/**
 	 * init element node
 	 * @param node
 	 * @param mTreeNode
 	 * @throws SAXException
 	 */
 	private void initElementNode(Node node, DefaultMutableTreeNode mTreeNode)
 			throws SAXException {
 		String nodeName = node.getNodeName();
 		
 		NodeList childNodes = node.getChildNodes();
 		XMLDefaultMutableTreeNode childTreeNode = new XMLDefaultMutableTreeNode(nodeName
 				,node);

 		mTreeNode.add(childTreeNode);
 		initAttributeNode(node, childTreeNode);
 		for (int i = 0; i < childNodes.getLength(); i++) 
 		{
 			Node childNode = childNodes.item(i);
 			initNode(childNode, childTreeNode);
 		}

 	}
 	/**
 	 * init attribute node
 	 * @param node
 	 * @param mTreeNode
 	 * @throws SAXException
 	 */
 	private void initAttributeNode(Node node, DefaultMutableTreeNode mTreeNode)
 			throws SAXException {
 		NamedNodeMap nm = node.getAttributes();
 		for (int i = 0; i < nm.getLength(); i++) 
 		{
 			Attr nmNode = (Attr)nm.item(i);
 			String value = nmNode.getName() + " = \"" + nmNode.getValue() + "\"";
 			XMLDefaultMutableTreeNode attributeNode = new XMLDefaultMutableTreeNode(
 			       value,nmNode);
 			mTreeNode.add(attributeNode);

 		}
 	}
 	/**
 	 * init comment Node
 	 * @param node
 	 * @param mTreeNode
 	 * @throws SAXException
 	 */
 	private void initCommentNode(Comment node, DefaultMutableTreeNode mTreeNode) throws SAXException{
 		String data = node.getData();
 		if(data != null || data.length() > 0)
 		{
 			String value = "<!--" + node.getData() + "-->";
 			XMLDefaultMutableTreeNode commentNode = new XMLDefaultMutableTreeNode(value,node);
 			mTreeNode.add(commentNode);
 		}
 	}
 	/**
 	 * init CDATASection Node
 	 * @param node
 	 * @param mTreeNode
 	 * @throws SAXException
 	 */
 	private void initCDATASectionNode(CDATASection node, DefaultMutableTreeNode mTreeNode) throws SAXException 
 	{
 		String data = node.getData();
 		if(data != null || data.length() > 0) 
 		{
 			String value = "<!-[CDATA" + node.getData() + "]]>";
 			XMLDefaultMutableTreeNode commentNode = new XMLDefaultMutableTreeNode(value,node);
 			mTreeNode.add(commentNode);
 		}
 	}
 	/**
 	 * init the TextNode
 	 * @param node
 	 * @param mTreeNode
 	 * @throws SAXException
 	 */
 	private void initTextNode(Text node, DefaultMutableTreeNode mTreeNode) throws SAXException 
 	{
 		String text = node.getNodeValue().trim();
 		if(text != null && text.length() > 0) 
 		{
 			XMLDefaultMutableTreeNode textNode = new XMLDefaultMutableTreeNode(text,node);
 			mTreeNode.add(textNode);
 		}
 	}
 	
 	/**
 	 * get the xml node
 	 * @return
 	 */
 	public Node getXMLNode() 
 	{
 		return xmlNode;
 	}
}