/*
 * Copyright 2004 The Apache Software Foundation.
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

package org.apache.jmeter.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterError;
import org.apache.jorphan.util.JMeterException;
import org.apache.log.Logger;

/**
 * BeanShell setup function - encapsulates all the access to the BeanShell
 * Interpreter in a single class.
 * 
 * The class uses dynamic class loading to access BeanShell, which means that
 * all the source files can be built without needing access to the bsh jar.
 * 
 * If the beanshell jar is not present at run-time, an error will be logged
 * 
 * @version $Revision: 412820 $ Updated on: $Date: 2006-06-08 19:12:15 +0100 (Thu, 08 Jun 2006) $
 */

public class BeanShellInterpreter {
	private static final Logger log = LoggingManager.getLoggerForClass();

	private static final Method bshGet;

	private static final Method bshSet;

	private static final Method bshEval;

	private static final Method bshSource;

	private static final Method bshGetNameSpace;

	private static final Class bshClass;

	private static final Method bsh_nsClear;

	private static final Class bshNameSpace;

	static {
		Method get = null, eval = null, set = null, source = null, getNameSpace = null;// Temporary,
																	// so can
																	// set the
																	// final
																	// ones
		Method nsClear = null;
		Class clazz = null;
		Class nSpace = null;
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			clazz = loader.loadClass("bsh.Interpreter");
			nSpace = loader.loadClass("bsh.NameSpace");
			
			Class string = String.class;
			Class object = Object.class;

			get = clazz.getMethod("get", //$NON-NLS-1$
					new Class[] { string });
			eval = clazz.getMethod("eval", //$NON-NLS-1$
					new Class[] { string });
			set = clazz.getMethod("set", //$NON-NLS-1$
					new Class[] { string, object });
			source = clazz.getMethod("source", //$NON-NLS-1$
					new Class[] { string });
			getNameSpace = clazz.getMethod("getNameSpace", //$NON-NLS-1$
					new Class[] {});
			nsClear = nSpace.getMethod("clear", //$NON-NLS-1$
					new Class[] {});

		} catch (ClassNotFoundException e) {
			log.error("Beanshell Interpreter not found");
		} catch (SecurityException e) {
			log.error("Beanshell Interpreter not found", e);
		} catch (NoSuchMethodException e) {
			log.error("Beanshell Interpreter not found", e);
		} finally {
			bshEval = eval;
			bshGet = get;
			bshSet = set;
			bshSource = source;
			bshGetNameSpace = getNameSpace;
			bshClass = clazz;
			bshNameSpace = nSpace;
			bsh_nsClear = nsClear;
		}
	}

	// This class is not serialised
	private Object bshInstance = null; // The interpreter instance for this class

	public BeanShellInterpreter() throws ClassNotFoundException {
		if (bshClass == null) {
			throw new ClassNotFoundException("bsh.Interpreter");//$NON-NLS-1$
		}
		try {
			bshInstance = bshClass.newInstance();
		} catch (InstantiationException e) {
			log.error("Can't instantiate BeanShell", e);
			throw new ClassNotFoundException("Can't instantiate BeanShell", e);
		} catch (IllegalAccessException e) {
			log.error("Can't instantiate BeanShell", e);
			throw new ClassNotFoundException("Can't instantiate BeanShell", e);
		}
	}

	public void init(String initFile, Object logger) throws IOException, JMeterException {
		if (logger != null) {// Do this before starting the script
			try {
				set("log", logger);//$NON-NLS-1$
			} catch (JMeterException e) {
				log.error("Can't set logger variable", e);
				throw e;
			}
		}
		if (initFile != null && initFile.length() > 0) {
			// Check file so we can distinguish file error from script error
			File in = new File(initFile);
			if (!in.exists()) {
				throw new FileNotFoundException(initFile);
			}
			if (!in.canRead()) {
				throw new IOException("Cannot read" + initFile);
			}
			source(initFile);
		}
	}

    /**
     * 
     * @param init initialisation file
     * @param log logger to pass to interpreter; also used to log errors in this method
     */
    public BeanShellInterpreter(String init, Logger _log)  throws ClassNotFoundException {
        this();
        try {
            this.init(init, _log);
        } catch (IOException e) {
            _log.warn("Could not initialise interpreter", e);
        } catch (JMeterException e) {
            _log.warn("Could not initialise interpreter", e);
        }
    }

	private Object bshInvoke(Method m, String s) throws JMeterException {
		Object r = null;
		try {
			r = m.invoke(bshInstance, new Object[] { s });
		} catch (IllegalArgumentException e) { // Programming error
			log.error("Error invoking bsh method " + m.getName() + "\n", e);
			throw new JMeterError("Error invoking bsh method " + m.getName(), e);
		} catch (IllegalAccessException e) { // Also programming error
			log.error("Error invoking bsh method " + m.getName() + "\n", e);
			throw new JMeterError("Error invoking bsh method " + m.getName(), e);
		} catch (InvocationTargetException e) { // Can occur at run-time
			// could be caused by the bsh Exceptions:
			// EvalError, ParseException or TargetError
			log.error("Error invoking bsh method " + m.getName() + "\n", e);
			throw new JMeterException("Error invoking bsh method " + m.getName(), e);
		}
		return r;
	}

	private Object bshInvoke(Method m, String s, Object o) throws JMeterException {
		Object r = null;
		try {
			r = m.invoke(bshInstance, new Object[] { s, o });
		} catch (IllegalArgumentException e) { // Programming error
			log.error("Error invoking bsh method " + m.getName() + "\n", e);
			throw new JMeterError("Error invoking bsh method " + m.getName(), e);
		} catch (IllegalAccessException e) { // Also programming error
			log.error("Error invoking bsh method " + m.getName() + "\n", e);
			throw new JMeterError("Error invoking bsh method " + m.getName(), e);
		} catch (InvocationTargetException e) { // Can occur at run-time
			// could be caused by the bsh Exceptions:
			// EvalError, ParseException or TargetError
			log.error("Error invoking bsh method " + m.getName() + "\n", e);
			throw new JMeterException("Error invoking bsh method " + m.getName(), e);
		}
		return r;
	}

	public Object eval(String s) throws JMeterException {
		return bshInvoke(bshEval, s);
	}

	public Object set(String s, Object o) throws JMeterException {
		return bshInvoke(bshSet, s, o);
	}

	public Object set(String s, boolean b) throws JMeterException {
		return bshInvoke(bshSet, s, b ? Boolean.TRUE : Boolean.FALSE);// JDK1.4
																		// Boolean.vaueof
	}

	public Object source(String s) throws JMeterException {
		return bshInvoke(bshSource, s);
	}

	public Object get(String s) throws JMeterException {
		return bshInvoke(bshGet, s);
	}

	public void cleanup() throws JMeterException {
		
		try {
			//call getNameSpace() on Interpreter
			Object ns = bshGetNameSpace.invoke(bshInstance, new Object[] {});
			
			//call clear() on resulting NameSpace
			bsh_nsClear.invoke(ns, new Object[] {});

		} catch (IllegalArgumentException e) {
			log.error("Error invoking bsh method " + bshGetNameSpace.getName() + "\n", e);
			throw new JMeterError("Error invoking bsh method " + bshGetNameSpace.getName(), e);
		} catch (IllegalAccessException e) {
			log.error("Error invoking bsh method " + bshGetNameSpace.getName() + "\n", e);
			throw new JMeterError("Error invoking bsh method " + bshGetNameSpace.getName(), e);
		} catch (InvocationTargetException e) {
			log.error("Error invoking bsh method " + bshGetNameSpace.getName() + "\n", e);
			throw new JMeterException("Error invoking bsh method " + bshGetNameSpace.getName(), e);
		}
	}
}
