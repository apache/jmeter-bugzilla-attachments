// $Header: /home/cvs/jakarta-jmeter/src/core/org/apache/jmeter/control/gui/WhileControllerGui.java,v 1.4 2005/07/12 20:50:34 mstover1 Exp $
/*
 * Copyright 2003-2004 The Apache Software Foundation.
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

package org.apache.jmeter.control.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.control.WhileController;
import org.apache.jmeter.control.WhileControllerCount;
import org.apache.jmeter.gui.util.FocusRequester;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledTextField;

public class WhileControllerCountGui extends AbstractControllerGui implements ActionListener {

    private static final String CONDITION_LABEL = "while_controller_label";

    /**
     * A field allowing the user to specify the condition (not yet used).
     */
    private JTextField theCondition;

    /** The name of the condition field component. */
    private static final String CONDITION = "While_Condition";

    private JLabeledTextField refNameField;

    /**
     * Create a new LoopControlPanel as a standalone component.
     */
    public WhileControllerCountGui() {
        init();
    }

    /**
     * A newly created component can be initialized with the contents of a Test
     * Element object by calling this method. The component is responsible for
     * querying the Test Element object for the relevant information to display
     * in its GUI.
     *
     * @param element
     *            the TestElement to configure
     */
    public void configure(TestElement element) {
        super.configure(element);
        //refNameField.setText("Reference name");// to be corrected to user resource
        if (element instanceof WhileControllerCount) {
            theCondition.setText(((WhileControllerCount) element).getCondition());
            refNameField.setText(((WhileControllerCount) element).getRefName());

        }

    }

    /**
     * Implements JMeterGUIComponent.createTestElement()
     */
    public TestElement createTestElement() {
        WhileControllerCount controller = new WhileControllerCount();
        modifyTestElement(controller);
        return controller;
    }

    /**
     * Implements JMeterGUIComponent.modifyTestElement(TestElement)
     */
    public void modifyTestElement(TestElement controller) {
        configureTestElement(controller);
        if (controller instanceof WhileControllerCount) {
            ////
            ((WhileControllerCount) controller).setRefName(refNameField.getText());            
            ///

            if (theCondition.getText().length() > 0) {
                ((WhileControllerCount) controller).setCondition(theCondition.getText());
            } else {
                ((WhileControllerCount) controller).setCondition("");
            }
        }
    }

    /**
     * Invoked when an action occurs. This implementation assumes that the
     * target component is the infinite loops checkbox.
     *
     * @param event
     *            the event that has occurred
     */
    public void actionPerformed(ActionEvent event) {
        new FocusRequester(theCondition);
    }

    public String getLabelResource() {
        return "while_controller_title";
    }

    /**
     * Initialize the GUI components and layout for this component.
     */
    private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createConditionPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

    }

    /**
     * Create a GUI panel containing the condition. TODO make use of the field
     *
     * @return a GUI panel containing the condition components
     */
    private JPanel createConditionPanel() {
        JPanel conditionPanel = new JPanel(new BorderLayout(8, 0));

        refNameField = new JLabeledTextField(JMeterUtils.getResString("ref_name_field"));
        conditionPanel.add(refNameField, BorderLayout.SOUTH);

        // Condition LABEL

        JLabel conditionLabel = new JLabel(JMeterUtils.getResString(CONDITION_LABEL));

        conditionPanel.add(conditionLabel, BorderLayout.WEST);

        // TEXT FIELD
        theCondition = new JTextField(""); // This means exit if last sample
                                            // failed
        theCondition.setName(CONDITION);
        conditionLabel.setLabelFor(theCondition);
        conditionPanel.add(theCondition, BorderLayout.CENTER);
        theCondition.addActionListener(this);


        conditionPanel.add(Box.createHorizontalStrut(conditionLabel.getPreferredSize().width
              + theCondition.getPreferredSize().width), BorderLayout.NORTH);

        return conditionPanel;
    }
}