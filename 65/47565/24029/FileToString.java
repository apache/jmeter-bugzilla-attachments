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

package org.apache.jmeter.functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.apache.log.Logger;

/**
 * FileToString Function to read a complete file into a String.
 *
 * Parameters:
 * - file name
 * - variable name (optional - defaults to FileToString_)
 *
 * Returns:
 * - the whole text from a file
 * - or **ERR** if an error occurs
 * - value is also saved in the variable for later re-use.
 *
 *
 * Notes:
 * - JMeter instantiates a copy of each function for every reference in a
 * Sampler or elsewhere; each instance will open its own copy of the the file
 * - the file name is resolved at file (re-)open time
 * - the output variable name is resolved every time the function is invoked
 *
 */
public class FileToString extends AbstractFunction implements TestListener {
    private static final Logger log = LoggingManager.getLoggerForClass();

    private static final List desc = new LinkedList();

    private static final String KEY = "__FileToString";//$NON-NLS-1$

    // Function name (only 1 _)

    static final String ERR_IND = "**ERR**";//$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("string_from_file_file_name"));//$NON-NLS-1$
        desc.add(JMeterUtils.getResString("function_name_paropt"));//$NON-NLS-1$
    }

    private static final int MIN_PARAM_COUNT = 1;

    private static final int MAX_PARAM_COUNT = 2;

    private static final int PARAM_NAME = 2;

    private String myValue;

    private String myName;

    private Object[] values;

    private BufferedReader myBread = null; // Buffered reader

    private FileReader fis; // keep this round to close it

    private String fileName; // needed for error messages

    public FileToString() {
        init();
        if (log.isDebugEnabled()) {
            log.debug("++++++++ Construct " + this);
        }
    }

    private void init(){
        myValue = ERR_IND;
        myName = "FileToString_";//$NON-NLS-1$
    }

    private void closeFile() {
        if (myBread == null) {
            return;
        }
        String tn = Thread.currentThread().getName();
        log.info(tn + " closing file " + fileName);//$NON-NLS-1$
        try {
            myBread.close();
            fis.close();
        } catch (IOException e) {
            log.error("closeFile() error: " + e.toString());//$NON-NLS-1$
        }
    }

    private static final int COUNT_UNUSED = -2;

    private int myStart = COUNT_UNUSED;

    private int myCurrent = COUNT_UNUSED;

    private int myEnd = COUNT_UNUSED;

    private void openFile() {        
    	String tn = Thread.currentThread().getName();
        fileName = ((CompoundVariable) values[0]).execute();
        
        log.info(tn + " opening file " + fileName);//$NON-NLS-1$
        
        try {
            fis = new FileReader(fileName);
            myBread = new BufferedReader(fis);
        } catch (Exception e) {
            log.error("openFile() error: " + e.toString());//$NON-NLS-1$
            myBread = null;
            myValue = "***Fehler beim laden der Datei***";
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.jmeter.functions.Function#execute(SampleResult, Sampler)
     */
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
    	
    	
    	log.info("["+this+"] - START Funktionsaufruf -");
    	
        if (values.length >= PARAM_NAME) {
            myName = ((CompoundVariable) values[PARAM_NAME - 1]).execute().trim();
        }

        myValue = ERR_IND;

        
        openFile();
        
        if (null != myBread) { // Did we open the file?
            
        	myValue = ""; // na dann einfach den String leeren und jede zeile anhaengen
        	String line = null;
        	
        	try {
        		while (( line = myBread.readLine()) != null) {
        			 
        		    log.info("["+this+"] - " + line);

        			myValue += line + "\n";
        		} 
            } catch (IOException e) {
                String tn = Thread.currentThread().getName();
                log.error(tn + " error reading file " + e.toString());//$NON-NLS-1$
            }
        } 
        else { // File was not opened successfully
                String tn = Thread.currentThread().getName();
                log.info(tn + " Fehler beim einlesen einer Date (FileToString-Fkt).");
                throw new JMeterStopThreadException("End of sequence");
        }
        
        closeFile();
        
        if (myName.length() > 0) {
            JMeterVariables vars = getVariables();
            if (vars != null) {// Can be null if called from Config item testEnded() method
                vars.put(myName, myValue);
            }
        }

        if (log.isDebugEnabled()) {
            String tn = Thread.currentThread().getName();
            log.debug(tn + " name:" //$NON-NLS-1$
                    + myName + " value:" + myValue);//$NON-NLS-1$
        }
    	
        log.info("["+this+"] - ENDE Funktionsaufruf -");
    	
        return myValue;
    }

    /*
     * (non-Javadoc) Parameters: - file name - variable name (optional) - start
     * index (optional) - end index or count (optional)
     *
     * @see org.apache.jmeter.functions.Function#setParameters(Collection)
     */
    public synchronized void setParameters(Collection parameters) throws InvalidVariableException {

        log.debug(this + "::StringFromFile.setParameters()");//$NON-NLS-1$
        checkParameterCount(parameters, MIN_PARAM_COUNT, MAX_PARAM_COUNT);
        values = parameters.toArray();

        StringBuffer sb = new StringBuffer(40);
        sb.append("setParameters(");//$NON-NLS-1$
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(((CompoundVariable) values[i]).getRawParameters());
        }
        sb.append(")");//$NON-NLS-1$
        log.info(sb.toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.jmeter.functions.Function#getReferenceKey()
     */
    public String getReferenceKey() {
        return KEY;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.jmeter.functions.Function#getArgumentDesc()
     */
    public List getArgumentDesc() {
        return desc;
    }

    public void testStarted() {
        //
    }

    public void testStarted(String host) {
        //
    }

    public void testEnded() {
        this.testEnded(""); //$NON-NLS-1$
    }

    public void testEnded(String host) {
        closeFile();
    }

    public void testIterationStart(LoopIterationEvent event) {
        //
    }
}