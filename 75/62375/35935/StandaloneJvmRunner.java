package ru.argustelecom.system.inf.test.arquillian.sampler.runner;

import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.RunNotifier;

import ru.argustelecom.system.inf.exception.SystemException;

/**
 * fork-executor of arquillian testRun
 * <p>
 * {@link #main(String[])} wanna full classname of testClass and methodName
 * <p>
 * Launches test as well as the failsafe maven plugin do it -- simply calls Arquillian Runner
 * <p>
 * @author kostd
 *
 */
public class StandaloneJvmRunner {
	
	public static void main(String[] args) {
		String classname  = args[0];
		String method = args[1];
		Class<?> clazz = null;
		try {
			clazz = Class.forName(classname);
		} catch (ClassNotFoundException e) {
			throw new SystemException(e);
		}
		Description methodDescription = Description.createTestDescription(clazz, method);
		Filter filter = Filter.matchMethodDescription(methodDescription);
		Request request = Request.aClass(clazz);
		if (filter != null) {
			request = request.filterWith(filter);
		}
		
		// array-holder is need to change retCode from inner notifier#fireTestFailure
		final Integer [] retCode = new Integer [1];
		retCode [0] = 0;
		
		RunNotifier notifier = new RunNotifier() {
			@Override
			public void fireTestFailure(org.junit.runner.notification.Failure failure) {
				// stdout here is not so bad because stdout inherited
				System.out.println(StandaloneJvmRunner.class.getName() + " failure: " + failure.getTestHeader());
				failure.getException().printStackTrace();
				retCode[0] = -1;
				super.fireTestFailure(failure);
			};
		};
		Runner runner = request.getRunner();
		try{
			runner.run(notifier);
		}catch(Exception e){
			System.out.println(StandaloneJvmRunner.class.getName() + "error: ");
			e.printStackTrace();
			retCode[0] = -2;
		}finally{
			System.exit(Integer.valueOf(retCode[0]));
		}


	}

}
