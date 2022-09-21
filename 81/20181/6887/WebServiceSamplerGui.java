package org.apache.jmeter.protocol.http.control.gui;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.jmeter.protocol.http.sampler.WebServiceSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.gui.util.FilePanel;
import org.apache.jorphan.gui.JLabeledChoice;
import org.apache.jorphan.gui.JLabeledTextArea;
import org.apache.jorphan.gui.JLabeledTextField;
import org.apache.jorphan.gui.layout.VerticalLayout;
import org.apache.jmeter.protocol.http.util.WSDLHelper;
import org.apache.jmeter.protocol.http.util.WSDLException;

/**
 * @author peter lin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public class WebServiceSamplerGui extends AbstractSamplerGui
	implements java.awt.event.ActionListener, java.awt.event.MouseListener {
		
	private static final String label = JMeterUtils.getResString("webservice_sampler_title");
	JLabeledTextField urlField = new JLabeledTextField(JMeterUtils.getResString("url"));
	JLabeledTextField soapAction = new JLabeledTextField(JMeterUtils.getResString("webservice_soap_action"));
	JLabeledTextArea soapXml = new JLabeledTextArea(JMeterUtils.getResString("soap_data_title"),null);
	JLabeledTextField wsdlField = new JLabeledTextField(JMeterUtils.getResString("wsdl_url"));
	JButton wsdlButton = new JButton(JMeterUtils.getResString("load_wsdl"));
	JButton selectButton = new JButton(JMeterUtils.getResString("configure_wsdl"));
	JLabeledChoice wsdlMethods = null;
	WSDLHelper HELPER = null;
	FilePanel soapXmlFile = new FilePanel(JMeterUtils.getResString("get_xml_from_file"),".xml");
	JLabeledTextField randomXmlFile = new JLabeledTextField(JMeterUtils.getResString("get_xml_from_random"));
	/**
	 * We create several JLabel objects to display
	 * usage instructions in the GUI. The reason
	 * there are multiple labels is to make sure
	 * it displays correctly.
	 */
	JLabel wsdlMessage = new JLabel(JMeterUtils.getResString("get_xml_message"));
	JLabel wsdlMessage2 = new JLabel(JMeterUtils.getResString("get_xml_message2"));
	JLabel wsdlMessage3 = new JLabel(JMeterUtils.getResString("get_xml_message3"));
	JLabel wsdlMessage4 = new JLabel(JMeterUtils.getResString("get_xml_message4"));
	JLabel wsdlMessage5 = new JLabel(JMeterUtils.getResString("get_xml_message5"));
	/**
	 * This is the font for the note.
	 */
	Font plainText = new Font("plain",Font.PLAIN,10);
	/**
	 * checkbox for memory cache.
	 */
	JCheckBox memCache = new JCheckBox(JMeterUtils.getResString("memory_cache"),true);
	/**
	 * checkbox for reading the response
	 */
	JCheckBox readResponse = new JCheckBox(JMeterUtils.getResString("read_soap_response"));
	/**
	 * Text note about read response and it's usage.
	 */
	JLabel readMessage = new JLabel(JMeterUtils.getResString("read_response_note"));
	JLabel readMessage2 = new JLabel(JMeterUtils.getResString("read_response_note2"));
	JLabel readMessage3 = new JLabel(JMeterUtils.getResString("read_response_note3"));
	
	public WebServiceSamplerGui()
	{
		init();
	}
	
	/**
	 * @see JMeterGUIComponent#getStaticLabel()
	 */
	public String getStaticLabel() {
		return label;
	}

	/**
	 * @see JMeterGUIComponent#createTestElement()
	 */
	public TestElement createTestElement() {
		WebServiceSampler sampler = new WebServiceSampler();
		this.configureTestElement(sampler);
		try {
			URL url = new URL(urlField.getText());
			sampler.setDomain(url.getHost());
			sampler.setPort(url.getPort());
			sampler.setProtocol(url.getProtocol());
			sampler.setMethod(WebServiceSampler.POST);
			sampler.setPath(url.getPath());
			sampler.setSoapAction(soapAction.getText());
			sampler.setXmlData(soapXml.getText());
			sampler.setXmlFile(soapXmlFile.getFilename());
			sampler.setXmlPathLoc(randomXmlFile.getText());
			sampler.setMemoryCache(memCache.isSelected());
			sampler.setReadResponse(readResponse.isSelected());
		} catch(MalformedURLException e) {
		}
		return sampler;
	}

	/**
	 * Modifies a given TestElement to mirror the data in the gui components.
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
	 */
	public void modifyTestElement(TestElement s)
	{
		WebServiceSampler sampler = (WebServiceSampler)s;
		this.configureTestElement(sampler);
		try {
			URL url = new URL(urlField.getText());
			sampler.setDomain(url.getHost());
			sampler.setPort(url.getPort());
			sampler.setProtocol(url.getProtocol());
			sampler.setMethod(WebServiceSampler.POST);
			sampler.setPath(url.getPath());
			sampler.setSoapAction(soapAction.getText());
			sampler.setXmlData(soapXml.getText());
			sampler.setXmlFile(soapXmlFile.getFilename());
			sampler.setXmlPathLoc(randomXmlFile.getText());
			sampler.setMemoryCache(memCache.isSelected());
			sampler.setReadResponse(readResponse.isSelected());
		} catch(MalformedURLException e) {
		}
	}

	/**
	 * init() adds soapAction to the mainPanel. The class
	 * reuses logic from SOAPSampler, since it is common.
	 */	
	private void init()
	{
		this.setLayout(new VerticalLayout(5, VerticalLayout.LEFT, VerticalLayout.TOP));
		wsdlMessage.setFont(plainText);
		wsdlMessage2.setFont(plainText);
		wsdlMessage3.setFont(plainText);
		wsdlMessage4.setFont(plainText);
		wsdlMessage5.setFont(plainText);
		readMessage.setFont(plainText);
		readMessage2.setFont(plainText);
		readMessage3.setFont(plainText);
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel();
		Border margin = new EmptyBorder(10, 10, 5, 10);
		mainPanel.setBorder(margin);
		mainPanel.setLayout(new VerticalLayout(5, VerticalLayout.LEFT));

		// TITLE
		JLabel panelTitleLabel = new JLabel(label);
		Font curFont = panelTitleLabel.getFont();
		int curFontSize = curFont.getSize();
		curFontSize += 4;
		panelTitleLabel.setFont(new Font(curFont.getFontName(), curFont.getStyle(), curFontSize));
		mainPanel.add(panelTitleLabel);
		// NAME
		mainPanel.add(getNamePanel());

		// Button for browsing webservice wsdl
		JPanel wsdlEntry = new JPanel();
		mainPanel.add(wsdlEntry);
		wsdlEntry.add(wsdlField);
		wsdlEntry.add(wsdlButton);
		wsdlButton.addActionListener(this);

		// Web Methods
		JPanel listPanel = new JPanel();
		JLabel selectLabel = new JLabel("Web Methods");
		wsdlMethods = new JLabeledChoice();
		mainPanel.add(listPanel);
		listPanel.add(selectLabel);
		listPanel.add(wsdlMethods);
		listPanel.add(selectButton);
		wsdlMethods.addMouseListener(this);
		selectButton.addActionListener(this);
		
		mainPanel.add(urlField);
		mainPanel.add(soapAction);
		// OPTIONAL TASKS
		mainPanel.add(soapXml);
		mainPanel.add(soapXmlFile);
		mainPanel.add(wsdlMessage);
		mainPanel.add(wsdlMessage2);
		mainPanel.add(wsdlMessage3);
		mainPanel.add(wsdlMessage4);
		mainPanel.add(wsdlMessage5);
		mainPanel.add(randomXmlFile);
		mainPanel.add(memCache);
		mainPanel.add(readResponse);
		mainPanel.add(readMessage);
		mainPanel.add(readMessage2);
		mainPanel.add(readMessage3);

		this.add(mainPanel);
	}
	
	/**
	 * the implementation loads the URL and the soap
	 * action for the request.
	 */
	public void configure(TestElement el)
	{
		super.configure(el);
		WebServiceSampler sampler = (WebServiceSampler)el;
		try {
			urlField.setText(sampler.getUrl().toString());
			soapAction.setText(sampler.getSoapAction());
		} catch(MalformedURLException e) {
		}
		soapXml.setText(sampler.getXmlData());
		soapXmlFile.setFilename(sampler.getXmlFile());
		randomXmlFile.setText(sampler.getXmlPathLoc());
		memCache.setSelected(sampler.getMemoryCache());
		readResponse.setSelected(sampler.getReadResponse());
	}
	
	/**
	 * configure the sampler from the WSDL. If the
	 * WSDL did not include service node, it will
	 * use the original URL minus the querystring.
	 * That may not be correct, so we should
	 * probably add a note. For Microsoft webservices
	 * it will work, since that's how IIS works.
	 */
	public void configureFromWSDL(){
		if (HELPER.getBinding() != null){
			this.urlField.setText(HELPER.getBinding());
		} else {
			StringBuffer buf = new StringBuffer();
			buf.append("http://" + HELPER.getURL().getHost());
			if (HELPER.getURL().getPort() != -1){
				buf.append(":" + HELPER.getURL().getPort());
			}
			buf.append(HELPER.getURL().getPath());
			this.urlField.setText(buf.toString());
		}
		this.soapAction.setText(HELPER.getSoapAction((String)this.wsdlMethods.getText()));
	}
	
	/**
	 * The method uses WSDLHelper to get the information
	 * from the WSDL. Since the logic for getting the
	 * description is isolated to this method, we can
	 * easily replace it with a different WSDL driver
	 * later on.
	 * @param url
	 * @return
	 */
	public String[] browseWSDL(String url){
		try {
			HELPER = new WSDLHelper(url);
			HELPER.parse();
			return HELPER.getWebMethods();
		} catch (Exception exception){
			// we should pop up a dialog, for
			// now don't do anything.
			JOptionPane.showConfirmDialog(this,"The WSDL was not valid, please double check the url","Warning",JOptionPane.OK_OPTION,JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public void actionPerformed(ActionEvent event){
		if (event.getSource() == selectButton){
			this.configureFromWSDL();
		} else {
			if (this.urlField.getText() != null){
				String[] wsdlData = browseWSDL(wsdlField.getText());
				wsdlMethods.setValues(wsdlData);
				wsdlMethods.repaint();
			} else {
				// we should pop up a dialog to give
				// the user instructions
				JOptionPane.showConfirmDialog(this,"The WSDL was not valid","Warning",JOptionPane.OK_OPTION,JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void mouseExited(java.awt.event.MouseEvent event){
		// do nothing
	}

	public void mousePressed(java.awt.event.MouseEvent event){
		// do nothing
	}

	public void mouseClicked(java.awt.event.MouseEvent event){
		if (event.getClickCount() == 2) {
			// call configureFromWSDL
		}
	}

	public void mouseReleased(java.awt.event.MouseEvent event){
		// do nothing
	}

	public void mouseEntered(java.awt.event.MouseEvent event){
		// do nothing
	}
}
