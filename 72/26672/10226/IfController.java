package org.apache.jmeter.control;

import java.io.Serializable;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.apache.jmeter.threads.JMeterVariables;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.apache.jmeter.junit.JMeterTestCase;
import org.apache.jmeter.engine.util.ValueReplacer;
import org.apache.jmeter.config.ConfigTestElement;
/**
 * 
 *@author    Cyrus Montakab
 * created    2003/06/30
 *@version   $Date: 2003/12/14 01:32:40 $ $Revision: 1.2 $
 * This is a Conditional Controller; it will execute the set of statements
 * (samplers/controllers, etc) while the 'condition' is true.
 * In a programming world - this is equivalant of :
 *   if (condition) {
 *        statements ....
 *   }
 * In JMeter you may have :
 *    Thread-Group (set to loop a number of times or indefinitely,
 *       ... Samplers ... (e.g. Counter )
 *       ... Other Controllers ....
 *       ... IfController ( condition set to something like - ${counter}<10)
 *            ... statements to perform if condition is true ...
 *       ... Other Controllers /Samplers
 *    }
 *
 ***************************************/

public class IfController extends GenericController implements Serializable
{

	  private static Logger logger =
			LoggingManager.getLoggerForClass();
	  private final static String CONDITION = "IfController.condition";
     private int loopCount = 0;

	  /**
	   * constructor
	   */
	  public IfController() {
			super();
	  }

	  /**
	   * constructor
	   */
	  public IfController(String condition) {
			super();
			this.setCondition(condition);
	  }

	  /**
	   * Condition Accessor - this is gonna be like     ${count}<10
	   */
	  public void setCondition(String condition) {
			setProperty(new StringProperty(CONDITION, condition));
				  logger.debug(
						"    >> set Condition called with -  [ "
							  + getCondition());
	  }

	  /**
	   * Condition Accessor - this is gonna be like     ${count}<10
	   */
	  public String getCondition() {
			return getPropertyAsString(CONDITION);
	  }

	  /**
	   * evaluate the condition clause
	   * Throw Exception If bad condition -
	   */
	  private boolean evaluateCondition() {
			// condition string is going to be of the form :  ${counter}<10
			// the following replaces the ${xxx} with actual valuess
			setRunningVersion(false);
            ValueReplacer replacer = new ValueReplacer();
            TestElement element = new ConfigTestElement();
            String Conditi="leeg";
            element.setProperty(new StringProperty(Conditi, getCondition()));
			try {            
				replacer.replaceValues(element);
			}			
			catch (Exception e) {
				  logger.error(e.getMessage(), e);
			} 
			element.setRunningVersion(true);
			setRunningVersion(true);
			logger.debug("    getProp() : ["+element.getPropertyAsString(Conditi)+"]-");

			String resultStr = "";
			boolean result = false;

			// now evaluate the condition using JavaScript
			Context cx = Context.enter();
			try {
				  Scriptable scope = cx.initStandardObjects(null);
				  Object cxResultObject =
						cx.evaluateString(scope, element.getPropertyAsString(Conditi)
				  /*** conditionString ***/
				  , "<cmd>", 1, null);
				  resultStr = Context.toString(cxResultObject);

				  if (resultStr.equals("false")) {
						result = false;
				  } else if (resultStr.equals("true")) {
						result = true;
				  } else {
						throw new Exception(" BAD CONDITION :: " + element.getPropertyAsString(Conditi));
				  }

				  logger.debug(
						"    >> evaluate Condition -  [ "
							  + element.getPropertyAsString(Conditi)
							  + "] results is  ["
							  + result
							  + "]");

			} 
			catch (Exception e) {
				  logger.error(e.getMessage(), e);
			} 
			finally {
				  Context.exit();
			}
			return result;
	  }

	  /**
	   * This is overriding the parent method.
	   * IsDone indicates whether the termination condition is reached.
	   * I.e. if the condition evaluates to False - then isDone() returns TRUE
	   */
	  public boolean isDone() {
	  	 	if (evaluateCondition())
	  	 	{
	  	 	    return false;
	  	 	} else
	  	 	{
	  	 		return true;
	  	 	}
	  }
	  /**
	   * @see org.apache.jmeter.control.Controller#next()
	   * 'JMeterThread' iterates thru the Controller by calling this method.
	   * IF a valid 'Sampler' is returned, then it executes the sampler
	   * (calls sampler.sampler(xxx) method) .
	   * So here we make sure that the samplers belonging to this
	   * Controller do not get called
	   *    - if isDone is true
	   *    - if its the first time this is run. The first time is special
	   *       cause it is called prior the iteration even starts !
	   */
    /* (non-Javadoc)
     * @see org.apache.jmeter.control.GenericController#nextIsNull()
     */
    protected Sampler nextIsNull() throws NextIsNullException
    {
		setFirst(true);
		resetCurrent();
        return null;
    }

/*	  public Sampler next() {
			Sampler currentElement = super.next();

			if (!isDone()) {
				  return currentElement;
			} else {
				  return null;
			}
	  }
*/
////////////////////////////// Start of Test Code ///////////////////////////

	  /**
	   * JUnit test
	   */
	  public static class Test extends JMeterTestCase {
			public Test(String name) {
				  super(name);
			}

			public void testProcessing() throws Exception {

				  GenericController controller = new GenericController();

				  controller.addTestElement(new IfController("false==false"));
				  controller.addTestElement(new IfController(" \"a\".equals(\"a\")"));
				  controller.addTestElement(new IfController("2<100"));

				  /*          GenericController sub_1 = new GenericController();
							  sub_1.addTestElement(new IfController("3==3"));
							  controller.addTestElement(sub_1);
							  controller.addTestElement(new IfController("false==true"));
				  */

				  /*
				  GenericController controller = new GenericController();
				  GenericController sub_1 = new GenericController();
				  sub_1.addTestElement(new IfController("10<100"));
				  sub_1.addTestElement(new IfController("true==false"));
				  controller.addTestElement(sub_1);
				  controller.addTestElement(new IfController("false==false"));

				  IfController sub_2 = new IfController();
				  sub_2.setCondition( "10<10000");
				  GenericController sub_3 = new GenericController();

				  sub_2.addTestElement(new IfController( " \"a\".equals(\"a\")" ) );
				  sub_3.addTestElement(new IfController("2>100"));
				  sub_3.addTestElement(new IfController("false==true"));
				  sub_2.addTestElement(sub_3);
				  sub_2.addTestElement(new IfController("2==3"));
				  controller.addTestElement(sub_2);
				  */

				  /*        IfController controller = new IfController("12==12");
						  controller.initialize();
				  */
				  logger.debug(">>>>>   testProcessing : Starting the iteration  ");
				  TestElement sampler = null;
				  while ((sampler = controller.next()) != null) {
						logger.debug(
							  "    ->>>  Gonna assertTrue :"
									+ sampler.getClass().getName()
									+ " Property is   ---->>>"
									+ sampler.getPropertyAsString(TestElement.NAME));
				  }
			}
	  }

}