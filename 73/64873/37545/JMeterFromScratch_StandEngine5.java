package com.cbim.dataservice.sample.testForJmeterSrc;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class JMeterFromScratch_StandEngine5 {

    public static void main(String[] argv) throws Exception {
        // 设置jmeterHome路径
        String jmeterHome1 = "E:\\Programing\\JmeterRestAPI\\JmeterInstallation\\apache-jmeter-5.0";
        //File jmeterHome = new File(System.getProperty("jmeter.home"));
        File jmeterHome = new File(jmeterHome1);
        String slash = System.getProperty("file.separator");

        if (jmeterHome.exists()) {
            File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
            if (jmeterProperties.exists()) {
                //JMeter Engine 引擎
            	//qiuhui comment 2020-11-2
                //StandardJMeterEngine jmeter = new StandardJMeterEngine();
                JMeterEngine jmeter = new StandardJMeterEngine();

                //JMeter initialization (properties, log levels, locale, etc)
                JMeterUtils.setJMeterHome(jmeterHome.getPath());
                JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
                JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
                JMeterUtils.initLocale();


                // JMeter Test Plan, basically JOrphan HashTree
                HashTree testPlanTree = new HashTree();

                // 第一个 HTTP Sampler - 打开 baidu.com
                HTTPSamplerProxy examplecomSampler = new HTTPSamplerProxy();
                examplecomSampler.setDomain("baidu.com");
                examplecomSampler.setPort(80);
                examplecomSampler.setPath("/");
                examplecomSampler.setMethod("GET");
                examplecomSampler.setName("Open baidu.com");
                examplecomSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                examplecomSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


                // 第二个 HTTP Sampler - 打开 qq.com
                HTTPSamplerProxy blazemetercomSampler = new HTTPSamplerProxy();
                blazemetercomSampler.setDomain("qq.com");
                blazemetercomSampler.setPort(80);
                blazemetercomSampler.setPath("/?fromdefault");
                blazemetercomSampler.setMethod("GET");
                blazemetercomSampler.setName("Open qq.com");
                blazemetercomSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                blazemetercomSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


                // Loop Controller 循环控制
                LoopController loopController = new LoopController();
                loopController.setLoops(1);
                loopController.setFirst(true);
                loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
                loopController.initialize();

                // Thread Group 线程组
                ThreadGroup threadGroup = new ThreadGroup();
                threadGroup.setName("Example Thread Group");
                threadGroup.setNumThreads(1);
                threadGroup.setRampUp(1);
                threadGroup.setSamplerController(loopController);
                threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
                threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

                // Test Plan 测试计划
                TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
                testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
                testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

                // Construct Test Plan from previously initialized elements
                // 从以上初始化的元素构造测试计划
                testPlanTree.add(testPlan);
                HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
                threadGroupHashTree.add(blazemetercomSampler);
                threadGroupHashTree.add(examplecomSampler);

                // save generated test plan to JMeter's .jmx file format
                // 将生成的测试计划保存为JMeter的.jmx文件格式
                SaveService.saveTree(testPlanTree, new FileOutputStream(jmeterHome + slash + "exampleQiuhuiDesign.jmx"));

                //add Summarizer output to get test progress in stdout like:
                // 在stdout中添加summary输出，得到测试进度，如:
                // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
                Summariser summer = null;
                String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                if (summariserName.length() > 0) {
                    summer = new Summariser(summariserName);
                }


                // Store execution results into a .jtl file
                // 将执行结果存储到.jtl文件中
                String logFile = jmeterHome + slash + "exampleQiuhuiDesign.jtl";
                ResultCollector logger = new ResultCollector(summer);
                logger.setFilename(logFile);
                testPlanTree.add(testPlanTree.getArray()[0], logger);
                
                //////////////////////Method1
                /*
                 * Method1 can work smoothly in version 5.0, 
                 * but failure in V5.3,critical failure!
                 * qiuhui 2020-11-2
                 * */

                // Run Test Plan
                // 执行测试计划
                jmeter.configure(testPlanTree);        
                //qiuhui comments 2020-11-2
                //jmeter.run();
                ((StandardJMeterEngine) jmeter).run();

                //////////////////////Method2
                /*
                String command = "E:\\Programing\\JmeterRestAPI\\JmeterInstallation\\apache-jmeter-5.3\\apache-jmeter-5.3\\bin\\jmeter -n -t E:\\Programing\\JmeterRestAPI\\JmeterInstallation\\apache-jmeter-5.3\\apache-jmeter-5.3\\exampleQiuhuiDesign.jmx  -l E:\\Programing\\JmeterRestAPI\\JmeterInstallation\\apache-jmeter-5.3\\apache-jmeter-5.3\\exampleQiuhuiDesign.jtl";

                Runtime.getRuntime().exec("cmd.exe /C start " + command);

                */
                System.out.println("Test completed. See " + jmeterHome + slash + "exampleQiuhuiDesign.jtl file for results");
                System.out.println("JMeter .jmx script is available at " + jmeterHome + slash + "exampleQiuhuiDesign.jmx");
                System.exit(0);
                 
            }
        }

        System.err.println("jmeter.home property is not set or pointing to incorrect location");
        System.exit(1);


    }
}