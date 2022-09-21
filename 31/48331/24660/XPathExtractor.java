// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XPathExtractor.java

package org.apache.jmeter.extractor;

import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.processor.PostProcessor;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.TidyException;
import org.apache.jmeter.util.XPathUtil;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterError;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XPathExtractor extends AbstractTestElement
    implements PostProcessor, Serializable
{

    public XPathExtractor()
    {    
    }

    private String concat(String s1, String s2)
    {
        return s1 + "_" + s2;
    }

    public void process()
    {
        JMeterContext context = getThreadContext();
        JMeterVariables vars = context.getVariables();
        String refName = getRefName();
        vars.put(refName, getDefaultValue());
        vars.put(concat(refName, "matchNr"), "0");
        vars.remove(concat(refName, "1"));
        SampleResult previousResult = context.getPreviousResult();
        try
        {
            Document d = parseResponse(previousResult);
            getValuesForXPath(d, getXPathQuery(), vars, refName);
        }
        catch(IOException e)
        {
            String errorMessage = "error on (" + getXPathQuery() + ")";
            log.error(errorMessage, e);
            throw new JMeterError(errorMessage, e);
        }
        catch(ParserConfigurationException e)
        {
            String errrorMessage = "error on (" + getXPathQuery() + ")";
            log.error(errrorMessage, e);
            throw new JMeterError(errrorMessage, e);
        }
        catch(SAXException e)
        {
            log.warn("error on (" + getXPathQuery() + ")" + e.getLocalizedMessage());
        }
        catch(TransformerException e)
        {
            log.warn("error on (" + getXPathQuery() + ")" + e.getLocalizedMessage());
        }
        catch(TidyException e)
        {
            AssertionResult ass = new AssertionResult("TidyException");
            ass.setFailure(true);
            ass.setFailureMessage(e.getMessage());
            previousResult.addAssertionResult(ass);
            previousResult.setSuccessful(false);
        }
    }

    public Object clone()
    {
        XPathExtractor cloned = (XPathExtractor)super.clone();
        return cloned;
    }

    public void setXPathQuery(String val)
    {
        setProperty("XPathExtractor.xpathQuery", val);
    }

    public String getXPathQuery()
    {
        return getPropertyAsString("XPathExtractor.xpathQuery");
    }

    public void setRefName(String refName)
    {
        setProperty("XPathExtractor.refname", refName);
    }

    public String getRefName()
    {
        return getPropertyAsString("XPathExtractor.refname");
    }

    public void setDefaultValue(String val)
    {
        setProperty("XPathExtractor.default", val);
    }

    public String getDefaultValue()
    {
        return getPropertyAsString("XPathExtractor.default");
    }

    public void setTolerant(boolean val)
    {
        setProperty(new BooleanProperty("XPathExtractor.tolerant", val));
    }

    public boolean isTolerant()
    {
        return getPropertyAsBoolean("XPathExtractor.tolerant");
    }

    public void setNameSpace(boolean val)
    {
        setProperty(new BooleanProperty("XPathExtractor.namespace", val));
    }

    public boolean useNameSpace()
    {
        return getPropertyAsBoolean("XPathExtractor.namespace");
    }

    public void setReportErrors(boolean val)
    {
        setProperty("XPathExtractor.report_errors", val, false);
    }

    public boolean reportErrors()
    {
        return getPropertyAsBoolean("XPathExtractor.report_errors", false);
    }

    public void setShowWarnings(boolean val)
    {
        setProperty("XPathExtractor.show_warnings", val, false);
    }

    public boolean showWarnings()
    {
        return getPropertyAsBoolean("XPathExtractor.show_warnings", false);
    }

    public void setQuiet(boolean val)
    {
        setProperty("XPathExtractor.quiet", val, true);
    }

    public boolean isQuiet()
    {
        return getPropertyAsBoolean("XPathExtractor.quiet", true);
    }

    private Document parseResponse(SampleResult result)
        throws UnsupportedEncodingException, IOException, ParserConfigurationException, SAXException, TidyException
    {
        String unicodeData = result.getResponseDataAsString();
        byte utf8data[] = unicodeData.getBytes("UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(utf8data);
        boolean isXML = JOrphanUtils.isXML(utf8data);
        return XPathUtil.makeDocument(in, false, false, useNameSpace(), isTolerant(), isQuiet(), showWarnings(), reportErrors(), isXML);
    }

    private void getValuesForXPath(Document d, String query, JMeterVariables vars, String refName)
        throws TransformerException
    {
        String val = null;
        XObject xObject = XPathAPI.eval(d, query);
        int objectType = xObject.getType();
        
        if(objectType == XObject.CLASS_NODESET)
        {   
            NodeList matches = xObject.nodelist();
            int length = matches.getLength();
            vars.put(concat(refName, "matchNr"), String.valueOf(length));
            for(int i = 0; i < length; i++)
            {
                Node match = matches.item(i);
                if(match instanceof Element)
                {   
                    /*
                    Node firstChild = match.getFirstChild();
                    if(firstChild != null){
                        val = firstChild.getNodeValue();
                    }else{
                        val = match.getNodeValue();
                    }
                    val = match.getNodeValue();
                    */
                    val = getXMLForNode(match);
                } else{
                    val = match.getNodeValue();
                } 
                 
                if(val == null)
                    continue;
                if(i == 0)
                    vars.put(refName, val);
                
                vars.put(concat(refName, String.valueOf(i + 1)), val);
            }

            vars.remove(concat(refName, String.valueOf(length + 1)));
            
            
        } else{
        
            if(objectType == -1 || objectType == 0 || objectType == 600)
            {
                log.warn("Unexpected object type: " + xObject.getTypeString() + " returned for: " + getXPathQuery());
            } else
            {
                val = xObject.toString();
                vars.put(concat(refName, "matchNr"), "1");
                vars.put(refName, val);
                vars.put(concat(refName, "1"), val);
                vars.remove(concat(refName, "2"));
            }
        }
    }

    private static final Logger log = LoggingManager.getLoggerForClass();
    private static final String MATCH_NR = "matchNr";
    private static final String XPATH_QUERY = "XPathExtractor.xpathQuery";
    private static final String REFNAME = "XPathExtractor.refname";
    private static final String DEFAULT = "XPathExtractor.default";
    private static final String TOLERANT = "XPathExtractor.tolerant";
    private static final String NAMESPACE = "XPathExtractor.namespace";
    private static final String QUIET = "XPathExtractor.quiet";
    private static final String REPORT_ERRORS = "XPathExtractor.report_errors";
    private static final String SHOW_WARNINGS = "XPathExtractor.show_warnings";
    
    private String getXMLForNode(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
