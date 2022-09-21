/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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

package org.apache.jmeter.protocol.http.control.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.apache.jmeter.protocol.http.sampler.JavaSerializedSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.util.FilePanel;
import org.apache.jorphan.gui.JLabeledTextArea;
import org.apache.jorphan.gui.JLabeledTextField;
import org.apache.jorphan.util.JOrphanUtils;

public class JavaSerializedSamplerGui extends AbstractSamplerGui implements ActionListener {
    private static final long serialVersionUID = 240L;

    private JLabeledTextField urlField;
    private JLabeledTextArea javaSerial;
    
    private static final String[] EXTS = { "" }; // $NON-NLS-1$
    private FilePanel javaSerialFile = new FilePanel("",EXTS);

    public JavaSerializedSamplerGui() {
        init();
    }

    public String getLabelResource() {
        return "java_serialized_sampler_title"; //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    public TestElement createTestElement() {
        JavaSerializedSampler sampler = new JavaSerializedSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    /**
     * Modifies a given TestElement to mirror the data in the gui components.
     *
     * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
     */
    public void modifyTestElement(TestElement s) {
        this.configureTestElement(s);
        if (s instanceof JavaSerializedSampler) {
           JavaSerializedSampler sampler = (JavaSerializedSampler) s;
           sampler.setUrlData(urlField.getText());
           sampler.setXstreamData(javaSerial.getText());
        }
    }

    /**
     * Implements JMeterGUIComponent.clearGui
     */
    @Override
    public void clearGui() {
        super.clearGui();

        urlField.setText(""); //$NON-NLS-1$
        javaSerial.setText(""); //$NON-NLS-1$
        javaSerialFile.setFilename(""); //$NON-NLS-1$
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(makeBorder());

        add(makeTitlePanel(), BorderLayout.NORTH);

        urlField = new JLabeledTextField(JMeterUtils.getResString("url"), 10); //$NON-NLS-1$
        javaSerial = new JLabeledTextArea(JMeterUtils.getResString("xstream_serialized_object")); //$NON-NLS-1$
        
        javaSerialFile.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ev) {
                loadfile(javaSerialFile);
              }});
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel soapActionPanel = new JPanel();
        soapActionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        soapActionPanel.add(urlField, c);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        c.gridy = 1;
        c.weightx = 0;

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridy = 2;
        c.gridx = 0;

        mainPanel.add(soapActionPanel, BorderLayout.NORTH);
        mainPanel.add(javaSerial, BorderLayout.CENTER);
        mainPanel.add(javaSerialFile, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(TestElement el) {
        super.configure(el);
        JavaSerializedSampler sampler = (JavaSerializedSampler) el;
        urlField.setText(sampler.getUrlData());
        javaSerial.setText(sampler.getXstreamData());
    }

    
    public void actionPerformed(ActionEvent event) {
        final Object eventSource = event.getSource();
        if (eventSource == javaSerialFile) {
            
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }
    
    private void loadfile(FilePanel fp){
        String filename = fp.getFilename();
        ObjectInputStream ois=null;
        try{
            XStream xstream = new XStream(new DomDriver());
            FileInputStream fichier = new FileInputStream(filename);
            ois = new ObjectInputStream(fichier);
            Object o=ois.readObject();
            javaSerial.setText(xstream.toXML(o));
            ois.close();
        }    
        catch(IOException e){
            GuiPackage.showErrorMessage(
                    "Error loading results file - could not open file",
                    "Result file loader");
        }
        catch(ClassNotFoundException e){
            GuiPackage.showErrorMessage(
                    "Error loading results file - Class not found",
                    "Result file loader");
        }
        finally{
            JOrphanUtils.closeQuietly(ois);
        }
    }
}
