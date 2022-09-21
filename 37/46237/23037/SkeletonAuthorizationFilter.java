package org.apache.jmeter.protocol.http.util.accesslog;

import java.io.Serializable;

import org.apache.jmeter.protocol.http.control.AuthManager;
import org.apache.jmeter.protocol.http.control.SkeletonAuthManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.testelement.TestCloneable;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * Provides username parsing from logs for the AccessLog Sampler, overrides AuthManager with one
 * that uses the username from the log (or no auth if it is "-") and a single password from the property "skeleton.password"
 * @author ej
 */
public class SkeletonAuthorizationFilter implements Filter, Serializable, TestCloneable
{
  private static final long serialVersionUID = -480275799056489612L;
  private static final Logger log = LoggingManager.getLoggerForClass();
  
  private static final AuthManager NULL_AUTH_MANAGER = new SkeletonAuthManager();
  
  private static final String SKELETON_PASSWORD = 
  	JMeterUtils.getPropDefault("skeleton.password", "skeleton");
  private static final String USERNAME_REGEX = 
  	JMeterUtils.getPropDefault("skeleton.username.regexp", "^\\S+ - (\\S+)");
  private static boolean first = true;
  
	/**
	 * 
	 */
  public SkeletonAuthorizationFilter()
  {
  }
  
  public Object clone()
  {
  	// no state, nothing to clone
  	return new SkeletonAuthorizationFilter();
  }
  
  protected String getUser(String logLine)
  {
		Pattern incIp = JMeterUtils.getPatternCache().getPattern(USERNAME_REGEX,
		                                                 				Perl5Compiler.READ_ONLY_MASK | Perl5Compiler.SINGLELINE_MASK);
		if (first)
		{
			log.debug("skeleton.username.regexp = " + USERNAME_REGEX + " read as " + incIp.getPattern() + " EOL");
			first = false;
		}
		
		Perl5Matcher matcher = JMeterUtils.getMatcher();
		if (!matcher.contains(logLine, incIp))
		{
			log.debug("line did not match skeleton.username.regexp");
			return null;
		}
		
		return matcher.getMatch().group(1);  	
  }
  
	public boolean isFiltered( String logLine, TestElement sampler )
	{
		String user = getUser(logLine);
		log.debug("getUser found " + user + " for " + logLine);
		
		if (user == null) {
			log.warn("log line didn't match user pattern, assuming no auth: " + logLine);
			((HTTPSamplerBase)sampler).setAuthManager(NULL_AUTH_MANAGER);
		} else if (user.equals("-"))
			((HTTPSamplerBase)sampler).setAuthManager(NULL_AUTH_MANAGER);
		else
		  ((HTTPSamplerBase)sampler).setAuthManager(new SkeletonAuthManager(user, SKELETON_PASSWORD));
		
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.util.accesslog.Filter#filter()
	 */	
	public void reset()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.util.accesslog.Filter#excludeFiles(java.lang.String[])
	 */
	public void excludeFiles(String[] filenames) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.util.accesslog.Filter#excludePattern(java.lang.String[])
	 */
	public void excludePattern(String[] regexp) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.util.accesslog.Filter#filter(java.lang.String)
	 */
	public String filter(String text) {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.util.accesslog.Filter#includeFiles(java.lang.String[])
	 */
	public void includeFiles(String[] filenames) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.util.accesslog.Filter#includePattern(java.lang.String[])
	 */
	public void includePattern(String[] regexp) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.util.accesslog.Filter#setReplaceExtension(java.lang.String,
	 *      java.lang.String)
	 */
	public void setReplaceExtension(String oldextension, String newextension) {
	}
}
