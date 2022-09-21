package org.apache.jmeter.protocol.http.control.gui;

import java.awt.Font;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.jmeter.protocol.http.sampler.WebServiceSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledTextArea;
import org.apache.jorphan.gui.JLabeledTextField;
import org.apache.jorphan.gui.layout.VerticalLayout;

/**
 * @author peter lin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public class WebServiceSamplerGui extends AbstractSamplerGui {
	// private static final String label = JMeterUtils.getResString("webservice_sampler_title");
	private static final String label = "Webservice";
	JLabeledTextField urlField = new JLabeledTextField(JMeterUtils.getResString("url"));
	JLabeledTextField soapAction = new JLabeledTextField("Soap Action");
	JLabeledTextArea soapXml = new JLabeledTextArea(JMeterUtils.getResString("soap_data_title"),null);

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
		} catch(MalformedURLException e) {
		}
		return sampler;
	}

	/**
	 * init() adds soapAction to the mainPanel. The class
	 * reuses logic from SOAPSampler, since it is common.
	 */	
	private void init()
	{
		this.setLayout(new VerticalLayout(5, VerticalLayout.LEFT, VerticalLayout.TOP));

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

		mainPanel.add(urlField);
		mainPanel.add(soapAction);
		// OPTIONAL TASKS
		mainPanel.add(soapXml);

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
	}

}
