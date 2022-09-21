/**
 * test.perf.JmeterTest
 */
 
package test.perf;
import javax.servlet.http.*;

/**
 * <p>This class serves as a test case for a JMeter problem encountered
 * in Apache JMeter Version 1.9.RC2.</p>
 * 
 * <p>Basically, JMeter gets confused (adds redundant query string parameters)
 * when a HTTP Sampler is used to get a page that redirects more than once.</p>  
 * 
 * <p>Reproduction Scenario
 * 
 * <p>Use Jmeter to call this servlet with a ?redirect=<number> query string where <number>
 * is greater than 2.  The servlet will count down the number of redirects
 * specified and finally render a basic html message.</p>
 * 
 * <p>JMeter's response to this is interesting.  Each subsequent time the HTTP Sampler is 
 * called (for a single thread in a Thread Group that loops), the attributes in
 * the query string are DOUBLED, then TRIPLED, etc.  This can be observed
 * using the View Results Tree "Results Window".</p>  
 * 
 * <p>In our case, servlets do this depending on varying circumstances.  The result
 * is that the integrity of the JMeter tests are unreliable thereafter.
 * 
 * An accompanying jmx file is provided.</p>
 * 
 * @author carl
 */
 
public class JmeterTest
extends HttpServlet
{
  /**
   * 
   */

  public void service (HttpServletRequest req, 
                       HttpServletResponse resp) 
  {
		try
		{
	    String param = req.getParameter("redirect");
	        
		  if (param != null)
		  {
		  	int count = Integer.parseInt (param);
		  	if (count > 0)
		  	{
		      String countDown = Integer.toString (count-1);
		      String url = req.getScheme() + "://" + 
		                   req.getServerName() + 
		                   req.getServletPath() +
		                   "?redirect=" + countDown;
		                   
		      resp.sendRedirect (resp.encodeRedirectUrl (url));
		      return;
	      }
		  }
		  
		  resp.setContentType("text/html");
		  resp.getWriter().write("<html><h1>JMeter Test.  Redirect countdown complete</h1></html>");
		  
    }    
	  catch (Throwable t)
	  {
	    System.err.println ("unable to service request: " + t.getMessage());
	  }         
  }   
   
  /**
   * 
   */

  public static String getCVSHeader() 
  {
	return "$Header$";
  }
}
