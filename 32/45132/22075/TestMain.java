import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.net.URLCodec;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String encoding = "cp950";
		URLCodec codec = new URLCodec(encoding);
		char givenChar = (char) 0x5A77;

		// I use \\u5A77 instead of \u5A77 since you may not see the correct
		// glyph/font in your version of windows xp.
		try {
			System.out.println("URL-Encode \\u5a77 using microsoft internet explorer           ： %B4@");
			System.out.println("URL-Encode \\u5a77 using org.apache.commons.codec.net.URLCodec ： " + codec.encode(givenChar + ""));
			System.out.println("URL-Encode \\u5a77 using java.net.URLEncoder                   ： " + URLEncoder.encode(givenChar + "", encoding));
			System.out.println("URL-Decode %B4%40 using org.apache.commons.codec.net.URLCodec ： " + toCodepointHexString(codec.decode("%B4%40")));
			System.out.println("URL-Decode %B4%40 using java.net.URLDecoder                   ： " + toCodepointHexString(URLDecoder.decode("%B4%40",encoding)));
			System.out.println("URL-Decode %B4@   using org.apache.commons.codec.net.URLCodec ： " + toCodepointHexString(codec.decode("%B4@")));
			System.out.println("URL-Decode %B4@    using java.net.URLDecoder                   ： " + toCodepointHexString(URLDecoder.decode("%B4@",encoding)));
			//System.out.println("URL-Decode %B4@   using java.net.URLDecoder                   ： " + URLDecoder.decode("%B4@",encoding).codePointAt(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String toCodepointHexString(String str) {
		
		StringBuffer strbuf = new StringBuffer();
		
		for(int i = 0; i < str.length(); i++) {
			strbuf.append("\\u");
			strbuf.append(Integer.toHexString(str.codePointAt(i)));
		}
		
		return strbuf.toString();
	}

}
