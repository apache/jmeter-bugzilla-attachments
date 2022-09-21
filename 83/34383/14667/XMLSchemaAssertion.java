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
package org.apache.jmeter.assertions;
import java.io.Serializable;
import java.io.StringReader;
import javax.xml.parsers.*;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * XMLSchemaAssertion.java
 * Validate XML Schema
 * @author <a href="mailto:d.maung@mdl.com">Dave Maung</a>
 *
 */
public class XMLSchemaAssertion extends AbstractTestElement implements
        Serializable, Assertion 
{
    public static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    public static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    public static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    private static final Logger log = LoggingManager.getLoggerForClass();
    public static final String XSD_FILENAME_KEY = "xmlschema_assertion_filename";
    private StringBuffer failureMessage = new StringBuffer();

  /**
   * getResult
   * 
   */
    public AssertionResult getResult(SampleResult response) 
    {
        AssertionResult result = new AssertionResult();
        if (response.getResponseData() == null) {
            return setResultForNull(result);
        }
        result.setFailure(false);
        String resultData = new String(
                getResultBody(response.getResponseData()));
        
        String xsdFileName = getXsdFileName();
        setSchemaResult(result, resultData, xsdFileName);
        return result;
    }

    protected AssertionResult setResultForNull(AssertionResult result) 
    {
        result.setError(false);
        result.setFailure(true);
        result.setFailureMessage("Response was null");
        return result;
    }

    /**
     * Return the body of the http return.
     */
    private byte[] getResultBody(byte[] resultData) 
    {
        for (int i = 0; i < (resultData.length - 1); i++) {
            if (resultData[i] == '\n' && resultData[i + 1] == '\n') {
                return getByteArraySlice(resultData, (i + 2),
                        resultData.length - 1);
            }
        }
        return resultData;
    }

    /**
     * Return a slice of a byte array
     */
    private byte[] getByteArraySlice(byte[] array, int begin, int end) 
    {
        byte[] slice = new byte[(end - begin + 1)];
        int count = 0;
        for (int i = begin; i <= end; i++) 
        {
            slice[count] = array[i];
            count++;
        }

        return slice;
    }

    public void setXsdFileName(String xmlSchemaFileName)
            throws IllegalArgumentException 
    {
        setProperty(XSD_FILENAME_KEY, xmlSchemaFileName);
    }

    public String getXsdFileName() 
    {
        return getPropertyAsString(XSD_FILENAME_KEY);
    }

    /**
     * set Schema result
     * @param result
     * @param xmlStr
     * @param xsdFileName
     */
    public void setSchemaResult(AssertionResult result, String xmlStr,
            String xsdFileName) 
    {
        try 
        {
            boolean toReturn = true;

            Document doc = null;
            DocumentBuilderFactory parserFactory = DocumentBuilderFactory
                    .newInstance();
            parserFactory.setValidating(true);
            parserFactory.setNamespaceAware(true);
            parserFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
            if (xsdFileName != null && xsdFileName.length() > 0) {
                parserFactory.setAttribute(JAXP_SCHEMA_SOURCE, xsdFileName);
            } else
            {
                throw new Exception(xsdFileName + "is invalid");
            }
            // create a parser:
            DocumentBuilder parser = parserFactory.newDocumentBuilder();
            parser.setErrorHandler(new SAXErrorHandler(result));
         

            doc = parser.parse(new InputSource(new StringReader(xmlStr)));
            //if everything went fine then xml schema validation is valid
        } catch (Exception e) 
        {
            result.setError(false);
            result.setFailureMessage(e.getMessage());
            result.setFailure(true);

        }

    }

    /**
     * SAXErrorHandler class
     */
    private class SAXErrorHandler implements ErrorHandler 
    {
        private AssertionResult result;

        public SAXErrorHandler(AssertionResult result)
        {
            this.result = result;
        }

        public void error(SAXParseException exception)

        throws SAXParseException 
        {

            throw exception;
        }

        public void fatalError(SAXParseException exception)
                throws SAXParseException 
        {

            throw exception;
        }

        public void warning(SAXParseException exception)
                throws SAXParseException 
        {

            throw exception;
        }
    }

}