import java.awt.BorderLayout;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class TestJapanese {

	public static void main(String[] args) throws FontFormatException,
			IOException {
		JEditorPane jep = new JEditorPane();
		jep.setEditable(false);

		JScrollPane resultsScrollPane = new JScrollPane(jep);
		JTabbedPane rightSide = new JTabbedPane();
		JPanel panel = new JPanel(new BorderLayout());
        panel.add(resultsScrollPane, BorderLayout.CENTER);
        rightSide.add("HTML", panel);

		try {
			jep.setEditorKitForContentType("text/html",  JEditorPane.createEditorKitForContentType("text/html"));
			jep.setContentType("text/html");
			//jep.setPage("http://jiji.com");
			StringWriter stringWriter = new StringWriter();
			Reader  dataInputStream  = new InputStreamReader(new URL("http://www.jajakarta.org/index.html.ja.sjis").openStream(), "shift_jis");
			int charRead = -1;
			while((charRead= dataInputStream.read()) != -1) {
				stringWriter.write(charRead);
			}
			jep.getDocument().putProperty("IgnoreCharsetDirective",Boolean.TRUE);
			jep.setText(stringWriter.toString());
			//jep.setText(new URL("http://www.jajakarta.org/index.html.ja.sjis").openStream());
			jep.setCaretPosition(0);
			resultsScrollPane.setViewportView(jep);
		} catch (IOException e) {
			jep.setContentType("text/html");
			jep.setText("<html>Could not load http://www.jajakarta.org/index.html.ja.sjis</html>");
		}

		JFrame f = new JFrame("JMeter Japanese BUG");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(rightSide);
		f.setSize(512, 342);
		f.show();

	}

}