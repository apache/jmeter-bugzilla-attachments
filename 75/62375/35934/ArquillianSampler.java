package ru.argustelecom.system.inf.test.arquillian.sampler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.jmeter.protocol.java.sampler.JUnitSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.protocol.servlet.arq514hack.descriptors.impl.web.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import ru.argustelecom.system.inf.configuration.ConfiguratorProperties;
import ru.argustelecom.system.inf.configuration.WorkspaceProperties;
import ru.argustelecom.system.inf.test.arquillian.sampler.runner.StandaloneJvmRunner;

/**
 * Run junit sample with arquillian runner. More precisely, run with the runner by which testclass annotated ( @Runner =
 * {@link Arquillian})
 * <p>
 * Do not calls @Before and @After because arq-runner does it himself
 * <p>
 * Notes:
 * <ul>
 * <li>arq not supports parallel methods execution, so {@link ArquillianSampler} runs each testRun in personal jvm
 * <li>Extends {@link JUnitSampler}, so need override jmeter`s saveservice.properties:
 * <code>JUnitSampler=ru.argustelecom.system.inf.test.arquillian.sampler.ArquillianSampler</code>
 * <p>
 * 
 * @author v.semchenko
 *
 */
public class ArquillianSampler extends JUnitSampler {

	private static final Logger log = LoggerFactory.getLogger(ArquillianSampler.class);

	private static final long serialVersionUID = 240L; // Remember to change this when the class changes ...

	/**
	 * @see org.apache.jmeter.protocol.java.sampler.JUnitSampler#sample(org.apache.jmeter.samplers.Entry)
	 */
	@Override
	public SampleResult sample(Entry entry) {
		final SampleResult sresult = new SampleResult();
		sresult.setSampleLabel(getName());// Bug 41522 - don't use rlabel here
		sresult.setSamplerData(getClassname() + "." + getMethod());
		sresult.setDataType(SampleResult.TEXT);
		// Assume success
		sresult.setSuccessful(true);
		sresult.setResponseMessage(getSuccess());
		sresult.setResponseCode(getSuccessCode());

		if (getMethod() != null) {
			String classname = getClassname();

			String method = getMethod();

			sresult.sampleStart();
			try {
				//@formatter:off
				// Arq is not ready to parallel method execution (i.e per one jvm some several threads calls @Test-methods od one class),
				// sinse notion of ClassContext exist, and this ClassContext is non-thread-safe. So, if parallel method execution admits, 
				 // will often (~ about 10% of all testRun) cath the NPE in ClassConext`s  injectionPoints:
				/*
				 * Caused by: java.lang.NullPointerException
						at org.jboss.arquillian.graphene.enricher.AbstractSearchContextEnricher.enrichRecursively(AbstractSearchContextEnricher.java:67)
						at org.jboss.arquillian.graphene.enricher.PageFragmentEnricher.createPageFragment(PageFragmentEnricher.java:163)
				 */
				// Retrain arquillian is very difficult, so launching each testRun in personal jvm(!). Yes, this costs expensive, but what to do?
				//@formatter:on
				// Now create process, pass our`s ClassPath to `em. To run tests in new process, will call
				// StandaloneJvmRunner. Copy-pasted from
				// https://wiki.cantara.no/display/dev/Start+a+new+JVM+from+Java
				String jvm = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
				// command must be as: jvm + system_properties + main_class + arguments
				List<String> command = new ArrayList<String>();
				command.add(jvm);
				// system_properties
				// we are runnin` each arquillian testRun in separate jvm, so very important to to set all needed System
				// Properties. How we can check, which ones needed? simple: list all system properties here (in this
				// jvm) and similar list in new jvm (i.e add debug-code into StandaloneJvmRunner#main). Compare two
				// lists and add missing(not all, only important) props. Today enough only log4j.configurationFile, but
				// not forever.
				command.add("-Dlog4j.configurationFile=" + System.getProperty("log4j.configurationFile"));
				// заполним остальные настройки в arquillian.xml (в wildcards arq будет пытаться подставить значения из
				// system properties) а мы их возьмем из work(my).properties
				command.add("-DUNIT_TEST_SERVER_PATH=" + WorkspaceProperties.instance().getInstallPath());
				command.add("-DMANAGEMENT_ADDRESS=" + WorkspaceProperties.instance().getBindAddress());
				command.add("-DMANAGEMENT_PORT="
						+ (WorkspaceProperties.DEFAULT_MANAGEMENT_PORT + Integer.valueOf(WorkspaceProperties.instance()
								.getProperties().getProperty(ConfiguratorProperties.PROP_STANDALONE_PORTS_OFFSET))));
				command.add("-DAPP_PORT=" + (WorkspaceProperties.DEFAULT_HTTP_PORT + Integer.valueOf(WorkspaceProperties
						.instance().getProperties().getProperty(ConfiguratorProperties.PROP_STANDALONE_PORTS_OFFSET))));
				command.add("-DforkNumber=" + Thread.currentThread().getName());

				// main_class
				command.add(StandaloneJvmRunner.class.getCanonicalName());
				// arguments
				command.add(classname);
				command.add(method);

				ProcessBuilder processBuilder = new ProcessBuilder(command);
				processBuilder.redirectOutput(Redirect.INHERIT);
				// no, because, if failure, we wanna see stdeer on data-tab of jmeter ui
				// processBuilder.redirectError(Redirect.INHERIT);

				Map<String, String> environment = processBuilder.environment();
				String classpath = System.getProperty("java.class.path");
				environment.put("CLASSPATH", classpath);
				try {
					Process process = processBuilder.start();
					final BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(process.getErrorStream()));
					try {
						final String[] stderr = new String[1];
						new Thread() {
							@Override
							public void run() {
								String line = "";
								try {
									while ((line = bufferedReader.readLine()) != null) {
										stderr[0] = Strings.isNullOrEmpty(stderr[0]) ? line : stderr[0] + "\n" + line;
									}
								} catch (IOException e) {
									stderr[0] = stderr[0] + "\n" + Throwables.getStackTraceAsString(e);
								}
							}
						}.run();
						int retCode = process.waitFor();
						if (retCode != 0) {
							sresult.setSuccessful(false);
							log.error("stderr of failed test run: \n" + stderr[0]);
							log.error("end of stderr ");

							sresult.setResponseMessage(String.valueOf(retCode));
							sresult.setResponseCode(String.valueOf(retCode));
							sresult.setResponseData(stderr[0], "UTF-8");
						}

					} finally {
						if (bufferedReader != null) {
							bufferedReader.close();
						}
						if (process != null) {
							process.destroy();
						}
					}

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
					sresult.setSuccessful(false);
					sresult.setResponseCode("-2");
					sresult.setResponseMessage(e.getMessage());
					sresult.setResponseData(Throwables.getStackTraceAsString(e), "UTF-8");
				}
			} finally {
				sresult.sampleEnd();
			}

		} else {
			// we should log a warning, but allow the test to keep running
			sresult.setSuccessful(false);
			// this should be externalized to the properties
			sresult.setResponseMessage("Failed to create an instance of the class:" + getClassname()
					+ ", reasons may be missing both empty constructor and one "
					+ "String constructor or failure to instantiate constructor,"
					+ " check warning messages in jmeter log file");
			sresult.setResponseCode(getErrorCode());
		}
		return sresult;
	}

}
