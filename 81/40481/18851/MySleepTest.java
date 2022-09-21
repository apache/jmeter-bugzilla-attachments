package org.apache.jmeter.protocol.java.test;

import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class MySleepTest extends SleepTest
{
    public SampleResult runTest( JavaSamplerContext context)
    {
        SampleResult result = super.runTest( context);
        result.setContentType( SampleResult.TEXT);
        result.setResponseData( "Yeah".getBytes());
        return result;
    }
}
