package org.apache.jmeter.visualizers;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.EditorKit;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.gui.GuiUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;


/**
 * Implement ResultsRender for XPath tester
 */
public class RenderAsHtml2 implements ResultRenderer {

    private static final String TEXT_HTML = "text/html";

    private JScrollPane xmlWithXPathPane;

    private JEditorPane xmlDataField;

    private static final EditorKit defaultHtmlEditor = JEditorPane.createEditorKitForContentType(TEXT_HTML);

    private JTabbedPane rightSide;

    private SampleResult sampleResult = null;

    private JScrollPane xmlDataPane;

    private Logger logger = LoggingManager.getLoggerForClass();

    /** {@inheritDoc} */
    public void clearData() {
        this.xmlDataField.setText(""); // $NON-NLS-1$
    }

    /** {@inheritDoc} */
    public void init() {
        // Create the panels for the xpath tab
        xmlWithXPathPane = createXpathExtractorPanel();
    }

    
    /*================= internal business =================*/
    

    /** {@inheritDoc} */
    public void renderResult(SampleResult sampleResult) {
        
        xmlDataField.setEditorKitForContentType(TEXT_HTML, defaultHtmlEditor);

        xmlDataField.setContentType(TEXT_HTML);

        
        /*
         * Get round problems parsing <META http-equiv='content-type'
         * content='text/html; charset=utf-8'> See
         * http://issues.apache.org/bugzilla/show_bug.cgi?id=23315
         *
         * Is this due to a bug in Java?
         */
        xmlDataField.getDocument().putProperty("IgnoreCharsetDirective", Boolean.TRUE); // $NON-NLS-1$

        StringWriter stringWriter = new StringWriter();
        try {
            Reader  dataInputStream  = new InputStreamReader(new URL("http://www.jajakarta.org/index.html.ja.sjis").openStream(), "shift_jis");
            //Reader  dataInputStream  = new InputStreamReader(new URL("http://www.google.com").openStream());
            int charRead = -1;
            while((charRead= dataInputStream.read()) != -1) {
                stringWriter.write(charRead);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        xmlDataField.setText(stringWriter.toString());
        xmlDataField.setCaretPosition(0);
        xmlDataPane.setViewportView(xmlDataField);
        
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "HTML2"; // $NON-NLS-1$
    }


    /** {@inheritDoc} */
    public void setupTabPane() {
         // Add xpath tester pane
        if (rightSide.indexOfTab("HTML2") < 0) { // $NON-NLS-1$
            rightSide.addTab("HTML2", xmlWithXPathPane); // $NON-NLS-1$
        }
        clearData();
    }

    /**
     * @return RegExp Tester panel
     */
    private JScrollPane createXpathExtractorPanel() {
        
        xmlDataField = new JEditorPane();
        xmlDataField.setEditable(false);

        this.xmlDataPane = GuiUtils.makeScrollPane(xmlDataField);
        xmlDataPane.setMinimumSize(new Dimension(0, 400));

        return xmlDataPane;
    }

   

    /** {@inheritDoc} */
    public synchronized void setRightSide(JTabbedPane side) {
        rightSide = side;
    }

    /** {@inheritDoc} */
    public synchronized void setSamplerResult(Object userObject) {
        if (userObject instanceof SampleResult) {
            sampleResult = (SampleResult) userObject;
        }
    }

    /** {@inheritDoc} */
    public void setLastSelectedTab(int index) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void renderImage(SampleResult sampleResult) {
        clearData();
        xmlDataField.setText(""); // $NON-NLS-1$
    }

    /** {@inheritDoc} */
    public void setBackgroundColor(Color backGround) {
    }

}
