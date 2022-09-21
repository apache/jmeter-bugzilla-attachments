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

package org.apache.jmeter.protocol.java.test;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * The <code>JavaTest</code> class is a simple example class for a
 * JMeter Java protocol client.  The class implements the
 * <code>JavaSamplerClient</code> interface.
 * <p>
 * During each sample, this client will sleep for some amount of
 * time.  The amount of time to sleep is determined from the
 * two parameters SleepTime and SleepMask using the formula:
 * <pre>
 *     totalSleepTime = SleepTime + (System.currentTimeMillis() % SleepMask)
 * </pre>
 * Thus, the SleepMask provides a way to add a random component
 * to the sleep time.
 * 
 * This class was derived from SleepTest.
 * 
 * The additional functionality is the ability to define the precise values of
 * - responseCode
 * - responseMessage
 * - Label
 * - success/fail status
 * 
 * The elapsed time and end-time cannot be directly controlled.
 * 
 * @author ANO
 * @version $Id$
 */
public class JavaTest
    extends AbstractJavaSamplerClient
    implements Serializable
{
    /**
     * The default value of the SleepTime parameter, in milliseconds.
     */
    public static final long DEFAULT_SLEEP_TIME = 100;
	private static final String SLEEP_NAME="SleepTime";

    /**
     * The default value of the SleepMask parameter.
     */
    public static final long DEFAULT_SLEEP_MASK = 0xff;
	private static final String DEFAULT_MASK_STRING 
		= "0x" + (Long.toHexString(DEFAULT_SLEEP_MASK)).toUpperCase();
	private static final String MASK_NAME="SleepMask";

    /**
     * The base number of milliseconds to sleep during each sample.
     */
    private long sleepTime;

    /**
     * A mask to be applied to the current time in order to add a
     * random component to the sleep time.
     */
    private long sleepMask;

	/*
	 * Holds the label to be stored in the sample result 
	 * (can be overridden by the label parameter)
	 */
	private String label;
	private static final String LABEL_DEFAULT="JavaTest";
	private static final String LABEL_NAME="Label";

	/*
	 * Holds the responseMessage to be stored in the sample result
	 * (can be overridden by the relevant parameter)
	 */
	private String responseMessage;
	private static final String RESPONSEMESSAGE_DEFAULT="";
	private static final String RESPONSEMESSAGE_NAME="ResponseMessage";
	
	/*
	 * Holds the responseCode to be stored in the sample result
	 * (can be overridden by the relevant parameter)
	 */
	private String responseCode;
	private static final String RESPONSECODE_DEFAULT="";
	private static final String RESPONSECODE_NAME="ResponseCode";

    /*
     * Holds the samplerData (shown as RequestData in the Tree display)  
     */
	private String samplerData;
	private static final String SAMPLERDATA_NAME="SamplerData";
	private static final String SAMPLERDATA_DEFAULT="";
     
	/*
	 * Holds the samplerData (shown as RequestData in the Tree display)  
	 */
	private String resultData;
	private static final String RESULTDATA_NAME="ResultData";
	private static final String RESULTDATA_DEFAULT="";
     
	/*
	 * Holds the success status to be stored in the sample result
	 * (can be overridden by the relevant parameter)
	 */
	private boolean success;
	private static final String SUCCESS_DEFAULT="OK";
	private static final String SUCCESS_NAME="Status";

    /**
     * Default constructor for <code>JavaTest</code>.
     *
     * The Java Sampler uses the default constructor to instantiate
     * an instance of the client class.
     */
    public JavaTest()
    {
        getLogger().debug(whoAmI() + "\tConstruct");
    }

    /**
     * Do any initialization required by this client.  In this case,
     * initialization consists of getting the values of the SleepTime
     * and SleepMask parameters.  It is generally recommended to do
     * any initialization such as getting parameter values in the
     * setupTest method rather than the runTest method in order to
     * add as little overhead as possible to the test.
     * 
     * @param context  the context to run with. This provides access
     *                  to initialization parameters.
     */
    public void setupTest(JavaSamplerContext context)
    {
        getLogger().debug(whoAmI() + "\tsetupTest()");
        listParameters(context);

        sleepTime = context.getLongParameter("SleepTime", DEFAULT_SLEEP_TIME);
        sleepMask = context.getLongParameter("SleepMask", DEFAULT_SLEEP_MASK);
		responseMessage =context.getParameter(RESPONSEMESSAGE_NAME,RESPONSEMESSAGE_DEFAULT);
		responseCode    =context.getParameter(RESPONSECODE_NAME,RESPONSEMESSAGE_DEFAULT);
		success         =context.getParameter(SUCCESS_NAME,SUCCESS_DEFAULT).equalsIgnoreCase("OK");
		label           =context.getParameter(LABEL_NAME,LABEL_DEFAULT);
		samplerData     =context.getParameter(SAMPLERDATA_NAME,SAMPLERDATA_DEFAULT);
		resultData     =context.getParameter(RESULTDATA_NAME,RESULTDATA_DEFAULT);
    }


	/**
	 * Provide a list of parameters which this test supports.  Any
	 * parameter names and associated values returned by this method
	 * will appear in the GUI by default so the user doesn't have
	 * to remember the exact names.  The user can add other parameters
	 * which are not listed here.  If this method returns null then
	 * no parameters will be listed.  If the value for some parameter
	 * is null then that parameter will be listed in the GUI with
	 * an empty value.
	 * 
	 * @return  a specification of the parameters used by this
	 *           test which should be listed in the GUI, or null
	 *           if no parameters should be listed.
	 */
	public Arguments getDefaultParameters()
	{
		Arguments params = new Arguments();
		params.addArgument(SLEEP_NAME, String.valueOf(DEFAULT_SLEEP_TIME));
		params.addArgument(MASK_NAME,DEFAULT_MASK_STRING);
		params.addArgument(LABEL_NAME,LABEL_DEFAULT);
		params.addArgument(RESPONSECODE_NAME,RESPONSECODE_DEFAULT);
		params.addArgument(RESPONSEMESSAGE_NAME,RESPONSEMESSAGE_DEFAULT);
		params.addArgument(SUCCESS_NAME,SUCCESS_DEFAULT);
		params.addArgument(SAMPLERDATA_NAME,SAMPLERDATA_DEFAULT);
		params.addArgument(RESULTDATA_NAME,SAMPLERDATA_DEFAULT);
		return params;
	}

    /**
     * Perform a single sample.  In this case, this method will
     * simply sleep for some amount of time.
     * <p>
     * This method returns a <code>SampleResult</code> object.
     * <code>SampleResult</code> has many fields which can be
     * used.  At a minimum, the test should use
     * <code>SampleResult.setTime</code> to set the time that
     * the test required to execute.  It is also a good idea to
     * set the sampleLabel and the successful flag.
     * 
     * @see org.apache.jmeter.samplers.SampleResult#setTime(long)
     * @see org.apache.jmeter.samplers.SampleResult#setSuccessful(boolean)
     * @see org.apache.jmeter.samplers.SampleResult#setSampleLabel(String)
     * 
     * @param context  the context to run with. This provides access
     *                 to initialization parameters.
     * 
     * @return         a SampleResult giving the results of this
     *                 sample.
     */
    public SampleResult runTest(JavaSamplerContext context)
    {
        SampleResult results = new SampleResult();

        try
        {
            // Record sample start time.
            long start = System.currentTimeMillis();

			results.setResponseCode(responseCode);
			results.setResponseMessage(responseMessage);
			if (samplerData != null && samplerData.length() > 0){
				results.setSamplerData(samplerData);
			}
			
			if (resultData != null && resultData.length() > 0){
				results.setResponseData(resultData.getBytes());
				results.setDataType(SampleResult.TEXT);
			}

            // Generate a random value using the current time.
            long ct = start % getSleepMask();

            // Execute the sample.  In this case sleep for the
            // specified time.
            Thread.sleep(getSleepTime() + ct);

            // Record end time and populate the results.
            long end = System.currentTimeMillis();

            results.setTime(end - start);
			results.setSuccessful(success);
//            results.setSampleLabel(
//                "Sleep Test: time = " + (getSleepTime() + ct));
			results.setSampleLabel(label);
        }
        catch (Exception e)
        {
            getLogger().error("JavaTest: error during sample", e);
            results.setSuccessful(false);
        }

        if (getLogger().isDebugEnabled())
        {
            getLogger().debug(
                whoAmI() + "\trunTest()" + "\tTime:\t" + results.getTime());
            listParameters(context);
        }

        return results;
    }

    /**
     * Do any clean-up required by this test.  In this case no
     * clean-up is necessary, but some messages are logged for
     * debugging purposes.
     * 
     * @param context  the context to run with. This provides access
     *                  to initialization parameters.
     */
    public void teardownTest(JavaSamplerContext context)
    {
        getLogger().debug(whoAmI() + "\tteardownTest()");
        listParameters(context);
    }
    /**
     * Dump a list of the parameters in this context to the debug log.
     *
     * @param context  the context which contains the initialization
     *                  parameters.
     */
    private void listParameters(JavaSamplerContext context)
    {
        if (getLogger().isDebugEnabled())
        {
            Iterator argsIt = context.getParameterNamesIterator();
            while (argsIt.hasNext())
            {
                String name = (String) argsIt.next();
                getLogger().debug(name + "=" + context.getParameter(name));
            }
        }
    }

    /**
     * Generate a String identifier of this test for debugging
     * purposes.
     * 
     * @return  a String identifier for this test instance
     */
    private String whoAmI()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(Thread.currentThread().toString());
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        return sb.toString();
    }

    /**
     * Get the value of the sleepTime field.
     * 
     * @return the base number of milliseconds to sleep during
     *          each sample.
     */
    private long getSleepTime()
    {
        return sleepTime;
    }

    /**
     * Get the value of the sleepMask field.
     * 
     * @return a mask to be applied to the current time in order
     *          to add a random component to the sleep time.
     */
    private long getSleepMask()
    {
        return sleepMask;
    }
}
