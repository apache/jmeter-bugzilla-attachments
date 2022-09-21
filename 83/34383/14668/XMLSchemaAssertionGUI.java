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

package org.apache.jmeter.assertions.gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import org.apache.jmeter.assertions.XMLSchemaAssertion;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * XMLSchemaAssertionGUI.java
 * @author <a href="mailto:d.maung@mdl.com">Dave Maung</a>
 *
 */

public class XMLSchemaAssertionGUI extends AbstractAssertionGui 
{
	 //class attributes
	  transient private static Logger log = LoggingManager.getLoggerForClass();
	  private JTextField xmlSchema;
	
	/**
     * The constructor.
     */
    public XMLSchemaAssertionGUI()
    {
        init();
    }

    /**
     * Returns the label to be shown within the JTree-Component.
     */
    public String getLabelResource()
    {
        return "xmlschema_assertion_title";
    }
    
    /**
     * create Test Element
     */
    public TestElement createTestElement()
    {
        XMLSchemaAssertion el = new XMLSchemaAssertion();
        modifyTestElement(el);
        return el;
    }

    /**
     * Modifies a given TestElement to mirror the data in the gui components.
     *
     * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
     */
    public void modifyTestElement(TestElement inElement) {

        log.debug("XMLSchemaAssertionGui.modifyTestElement() called");
        configureTestElement(inElement);
        ((XMLSchemaAssertion) inElement).setXsdFileName(xmlSchema.getText());
      }


    /**
     * Configures the associated test element.
     * @param el
     */
    public void configure(TestElement el)
    {
        super.configure(el);
    }


    /**
     * Inits the GUI.
     */
    private void init()
    {
    	 setLayout(new BorderLayout(0, 10));
    	    setBorder(makeBorder());

    	    add(makeTitlePanel(), BorderLayout.NORTH);

    	    JPanel mainPanel = new JPanel(new BorderLayout());

    	    // USER_INPUT
    	    VerticalPanel assertionPanel = new VerticalPanel();
    	    assertionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "XML Schema"));

    	    //doctype
    	    HorizontalPanel xmlSchemaPanel = new HorizontalPanel();
    	    
    	    xmlSchemaPanel.add(
    	            new JLabel(JMeterUtils.getResString("xmlschema_assertion_label")));

    	        xmlSchema = new JTextField(26);
    	        xmlSchemaPanel.add(xmlSchema);
    	        
    	        
    	    assertionPanel.add(xmlSchemaPanel);

    	    mainPanel.add(assertionPanel, BorderLayout.NORTH);
    	    add(mainPanel, BorderLayout.CENTER);
    }
    
  
    public void stateChanged(ChangeEvent e) {
        log.debug("XMLSchemaAssertionGui.stateChanged() called");
      }
    
}