/*
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001,2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by the
 * Apache Software Foundation (http://www.apache.org/)."
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 * "Apache JMeter" must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 * "Apache JMeter", nor may "Apache" appear in their name, without
 * prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.jmeter.protocol.http.gui;

import java.awt.event.ActionEvent;
//import java.io.File;
import java.util.Collections;
//import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
//import java.util.Map;

//import javax.swing.JFileChooser;
//import javax.swing.filechooser.FileFilter;

import org.apache.jmeter.gui.GuiPackage;
//import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.gui.action.Command;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.HashTreeTraverser;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.config.Argument;
//import org.apache.jmeter.config.Arguments;



/**
 * Converts all 1.8.1 scripts use_equals to true.
 * In Jmeter 1.8.1 this field was not shown but acted as true.
 * This causes problems in 1.9.1, because if the values are empty, then
 * use_equals are set to false. When this action is selected it will change all
 * HTTPSamplers.HTTPArguments.use_equals to true.
 * @author Chris Pesarchick
 * @version CVS $Revision: 1.4 $ $Date: 2003/09/08 19:12:54 $
 */
public class Convert18to19ScriptsCommand implements Command, HashTreeTraverser
{
    private static Set commandSet;
    static {
        HashSet commands = new HashSet();
        commands.add("convert18to19Script");
        Convert18to19ScriptsCommand.commandSet = Collections.unmodifiableSet(commands);
    }

    //private JFileChooser keyStoreChooser;

    /**
     * Handle the "sslmanager" action by displaying the "SSL CLient Manager"
     * dialog box.  The Dialog Box is NOT modal, because those should be avoided
     * if at all possible.
     */
    public void doAction(ActionEvent e)
    {
        if (e.getActionCommand().equals("convert18to19Script"))
        {
            this.convert();
        }
    }

    /**
     * Provide the list of Action names that are available in this command.
     */
    public Set getActionNames()
    {
        return Convert18to19ScriptsCommand.commandSet;
    }

    /**
     * Called by sslManager button. Raises sslManager dialog.  Currently the
     * sslManager box has the product image and the copyright notice.  The
     * dialog box is centered over the MainFrame.
     */
    private void convert()
    {
	GuiPackage gui = GuiPackage.getInstance();
        HashTree testTree = gui.getTreeModel().getTestPlan();
	testTree.traverse( this );
    }

    /**
     * The tree traverses itself depth-first, calling addNode for each object
     * it encounters as it goes.  This is a callback method, and should not be called except
     * by a HashTree during traversal.
     * 
     * This looks for HTTPSamplers and sets true to all HTTPArgument.setUseEquals.
     * True was the default value in 1.8.1.
     *
     * @param node The node currently encountered
     * @param subTree The HashTree under the node encountered.
     */
    public void addNode(Object node, HashTree subTree) 
    {
	JMeterTreeNode treeNode = (JMeterTreeNode)node;
	TestElement element = treeNode.createTestElement();
	if ( element instanceof HTTPSampler ) {
	    HTTPSampler sampler = (HTTPSampler) element;
	    PropertyIterator iter = sampler.getArguments().iterator();
	    HTTPArgument item = null;
	    while ( iter.hasNext( ) ) {
		try {
		    item = (HTTPArgument) iter.next().getObjectValue();
		}
		catch (ClassCastException e)
		{
		    item = new HTTPArgument((Argument) iter.next().getObjectValue());
		}
		item.setUseEquals( true );
	    }
	}
    }

    /**
     * Indicates traversal has moved up a step, and the visitor should remove the
     * top node from it's stack structure.  This is a callback method, and should not be called except
     * by a HashTree during traversal.
     */
    public void subtractNode() 
    {
    }
    /**
     * Process path is called when a leaf is reached.  If a visitor wishes to generate
     * Lists of path elements to each leaf, it should keep a Stack data structure of
     * nodes passed to it with addNode, and removing top items for every subtractNode()
     * call.  This is a callback method, and should not be called except
     * by a HashTree during traversal.
     */
    public void processPath() 
    {
    }
}
