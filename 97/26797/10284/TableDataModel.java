/*
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
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
package org.apache.jmeter.visualizers;


import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import java.text.SimpleDateFormat;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;


/**
 * This class implements the TableModel for the information kept by the
 * GraphModel.
 *
 * @author     <a href="mailto:alf@i100.no">Alf Hogemark</a>Hogemark
 * Created      March 10, 2002
 * @version    $Revision: 1.12 $ Last updated: $Date: 2004/01/07 00:28:21 $
 */
public class TableDataModel extends GraphModel implements TableModel
{
    transient private static Logger log = LoggingManager.getLoggerForClass();

    List urlList = new ArrayList();

    List timestampList = new ArrayList();
    
    protected final SimpleDateFormat timestampFormat
    		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor for the TableDataModel object.
     */
    public TableDataModel()
    {
        super();
    }

    /**
     * Gets the GuiClass attribute of the TableModel object.
     *
     * @return    the GuiClass value
     */
    public Class getGuiClass()
    {
        return TableVisualizer.class;
    }

    public void clear()
    {
        super.clear();
        urlList.clear();
    }

    /**
     * Gets the ClassLabel attribute of the GraphModel object.
     *
     * @return    the ClassLabel value
     */
    public String getClassLabel()
    {
        return JMeterUtils.getResString("view_results_in_table");
    }

    public Sample addNewSample(
        long time,
        long timeStamp,
        boolean success,
        String url)
    {
        Sample s = super.addNewSample(time, timeStamp, success);

        urlList.add(url);
        timestampList.add(new Date(timeStamp));
        
        return s;
    }

    public Sample addSample(SampleResult e)
    {
        Sample s = addNewSample(e.getTime(), e.getTimeStamp(), e.isSuccessful(),
                (String) e.getSampleLabel());

        fireDataChanged();

        return s;
    }

    // Implementation of the TableModel interface
    public int getRowCount()
    {
        return getSampleCount();
    }

    public int getColumnCount()
    {
        return 5;
    }

    public String getColumnName(int columnIndex)
    {
        switch (columnIndex)
        {
        case 0:
            return "SampleNo";

        case 1:
            return JMeterUtils.getResString("url");

        case 2:
            return "Sample - ms";

        case 3:
            return JMeterUtils.getResString("Success?");

        case 4:
            return "Timestamp";

        default:
            return null;
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0)
        {
            return Integer.class;
        }
        else if (columnIndex == 1)
        {
            return String.class;
        }
        else if (columnIndex == 2)
        {
            return Long.class;
        }
        else if (columnIndex == 3)
        {
            return Boolean.class;
        }
        else if (columnIndex == 4)
        {
            return String.class;
        }
        else
        {
            return null;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (columnIndex == 0)
        {
            if ((rowIndex >= 0) && (rowIndex < getSampleCount()))
            {
                return new Integer(rowIndex + 1);
            }
        }
        else if (columnIndex == 1)
        {
            log.debug("rowIndex = " + rowIndex);
            if ((rowIndex >= 0) && (rowIndex < urlList.size()))
            {
                log.debug(" url = " + urlList.get(rowIndex));
                return urlList.get(rowIndex);
            }
        }
        else if (columnIndex == 2)
        {
            if ((rowIndex >= 0) && (rowIndex < getSampleCount()))
            {
                return new Long(((Sample) getSamples().get(rowIndex)).data);
            }
        }
        else if (columnIndex == 3)
        {
            if ((rowIndex >= 0) && (rowIndex < urlList.size()))
            {
                return JOrphanUtils.valueOf(
                    !((Sample) getSamples().get(rowIndex)).error);
            }
        }
        else if (columnIndex == 4)
        {
            if ((rowIndex >= 0) && (rowIndex < timestampList.size()))
            {
                return timestampFormat.format((Date) timestampList.get(rowIndex));
            }
        }   
        return null;
    }

    /**
     * Dummy implementation.
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {}

    /**
     * Dummy implementation.
     */
    public void addTableModelListener(TableModelListener l)
    {}

    /**
     * Dummy implementation.
     */
    public void removeTableModelListener(TableModelListener l)
    {}
}

