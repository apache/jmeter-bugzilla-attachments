/*
 * Copyright 2002-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *  
 */

package org.apache.jmeter.visualizers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.jmeter.samplers.Clearable;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.Calculator;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.gui.AbstractVisualizer;
import org.apache.jorphan.gui.ObjectTableModel;
import org.apache.jorphan.reflect.Functor;

/**
 * Simpler (lower memory) version of Aggregate Report (StatVisualizer).
 * Excludes the Median and 90% columns, which are expensive in memory terms
 */
public class SummaryReport extends AbstractVisualizer implements
							Clearable, ActionListener, ClipboardOwner, Transferable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String[] COLUMNS = { 
            JMeterUtils.getResString("sampler_label"),               //$NON-NLS-1$
			JMeterUtils.getResString("aggregate_report_count"),      //$NON-NLS-1$
            JMeterUtils.getResString("average"),                     //$NON-NLS-1$
			JMeterUtils.getResString("aggregate_report_min"),        //$NON-NLS-1$
            JMeterUtils.getResString("aggregate_report_max"),        //$NON-NLS-1$
			JMeterUtils.getResString("aggregate_report_error%"),     //$NON-NLS-1$
            JMeterUtils.getResString("aggregate_report_rate"),       //$NON-NLS-1$
			JMeterUtils.getResString("aggregate_report_bandwidth"),  //$NON-NLS-1$
            JMeterUtils.getResString("average_bytes"),               //$NON-NLS-1$
            };

	private final String TOTAL_ROW_LABEL 
        = JMeterUtils.getResString("aggregate_report_total_label");  //$NON-NLS-1$

	protected JTable myJTable;

	protected JScrollPane myScrollPane;

	transient private ObjectTableModel model;

	Map tableRows = Collections.synchronizedMap(new HashMap());

	//Used for clipboard
	transient private StringBuffer clipContents;
	
	public SummaryReport() {
		super();
		model = new ObjectTableModel(COLUMNS, 
                new Functor[] { 
                    new Functor("getLabel"),   //$NON-NLS-1$
                    new Functor("getCount"),  //$NON-NLS-1$
    				new Functor("getMeanAsNumber"),   //$NON-NLS-1$
                    new Functor("getMin"),  //$NON-NLS-1$
                    new Functor("getMax"),   //$NON-NLS-1$
                    new Functor("getErrorPercentageString"),   //$NON-NLS-1$
                    new Functor("getRateString"),  //$NON-NLS-1$
    				new Functor("getKBPerSecondString"),   //$NON-NLS-1$
                    new Functor("getPageSizeString"),   //$NON-NLS-1$
                },
                new Functor[] { null, null, null, null, null, null, null, null , null }, 
                new Class[] { String.class, Long.class, Long.class, Long.class,
                              Long.class, String.class, String.class, String.class, String.class });
		clear();
		init();
	}

	public String getLabelResource() {
		return "summary_report";  //$NON-NLS-1$
	}

	public void add(SampleResult res) {
		Calculator row = null;
		synchronized (tableRows) {
			row = (Calculator) tableRows.get(res.getSampleLabel());
			if (row == null) {
				row = new Calculator(res.getSampleLabel());
				tableRows.put(row.getLabel(), row);
				model.insertRow(row, model.getRowCount() - 1);
			}
		}
		row.addSample(res);
		Calculator tot = ((Calculator) tableRows.get(TOTAL_ROW_LABEL));
        tot.addSample(res);
		model.fireTableDataChanged();
	}

	/**
	 * Clears this visualizer and its model, and forces a repaint of the table.
	 */
	public void clear() {
		model.clearData();
		tableRows.clear();
		tableRows.put(TOTAL_ROW_LABEL, new Calculator(TOTAL_ROW_LABEL));
		model.addRow(tableRows.get(TOTAL_ROW_LABEL));
	}

	// overrides AbstractVisualizer
	// forces GUI update after sample file has been read
	public TestElement createTestElement() {
		TestElement t = super.createTestElement();

		// sleepTill = 0;
		return t;
	}

	/**
	 * Main visualizer setup.
	 */
	private void init() {
		this.setLayout(new BorderLayout());

		// MAIN PANEL
		JPanel mainPanel = new JPanel();
		Border margin = new EmptyBorder(10, 10, 5, 10);

		mainPanel.setBorder(margin);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		mainPanel.add(makeTitlePanel());

		myJTable = new JTable(model);
		myJTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		myScrollPane = new JScrollPane(myJTable);
		this.add(mainPanel, BorderLayout.NORTH);
		this.add(myScrollPane, BorderLayout.CENTER);
		
		/* By Tim - add a Paste button to capture the table contents */
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(margin);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton copyButton = new JButton(JMeterUtils.getResString("copy_to_clipboard"));
		copyButton.addActionListener(this);
		buttonPanel.add(copyButton);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		// Right now this is always going to be the Copy operation
		// Clipboard is accessed via Toolkit.getDefaultToolkit().getSystemClipboard()

		//I could generate the data inside getTransferData, but this means that if
		//the table is updated after the copy operation, a paste will got the new data
		//rather than what I had when I hit the copy button.  That is not right!
		clipContents = new StringBuffer();
		
		int rows = model.getRowCount();
		int cols = model.getColumnCount();
		
		for(int col = 0; col < cols; col++)
		{
			clipContents.append(model.getColumnName(col));
			if(col + 1 < cols) {clipContents.append("\t");}
		}
		clipContents.append("\n");
		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < cols; col++)
			{
				clipContents.append(model.getValueAt(row, col));
				if(col + 1 < cols) {clipContents.append("\t");}
			}
			clipContents.append("\n");
		}

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(this, this);
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		//We can lose the StringBuffer
		clipContents = null;
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if(flavor == DataFlavor.stringFlavor)
		{
			return (clipContents == null ? null : clipContents.toString());
		}
		throw new UnsupportedFlavorException(flavor);
	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] flavors = {DataFlavor.stringFlavor};
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		//I only deal in strings
		return (flavor == DataFlavor.stringFlavor);
	}
}
