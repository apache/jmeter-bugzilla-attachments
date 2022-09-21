package com.castlight.performance.jmeter;
import java.net.MalformedURLException;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;


public class JMeterClient {
   
   StandardJMeterEngine jmeter;
   
   
   public JMeterClient() {
   }


   public void run(String host, int port, String searchURL) {
      
      jmeter = new StandardJMeterEngine();

      JMeterUtils
            .loadJMeterProperties("//Users/ramesh/Projects/Ventana/soa-systems/thirdparty/libraries/apache/jmeter/bin/jmeter.properties");
      
      
      
      JMeterUtils.initLogging();// you can comment this line out to see extra
                                // log messages of i.e. DEBUG level
      JMeterUtils.initLocale();

      System.out.print("Jmeter run.1.");
      HashTree testPlanTree = new HashTree();
      
      HTTPSampler httpSampler = new HTTPSampler();
      httpSampler.setName("Test");
      httpSampler.setDomain("www.google.com");
      httpSampler.setPort(80);
      httpSampler.setPath("/");
      httpSampler.setMethod("GET");
      
      
      try {
         System.out.println(httpSampler.getUrl().toExternalForm());
      } catch (MalformedURLException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      
      LoopController loopController = new SamplerLoopContoller();
      loopController.setLoops(1);
      loopController.addTestElement(httpSampler);
      loopController.setFirst(true);
      
      loopController.initialize();
      
      ThreadGroup threadGroup = new ThreadGroup();
      threadGroup.setNumThreads(1);
      threadGroup.setRampUp(1);
      threadGroup.setSamplerController(loopController);
      
      TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
      testPlanTree.add("testPlan", testPlan);
      testPlanTree.add("loopController", loopController);
      testPlanTree.add("threadGroup", threadGroup);
      testPlanTree.add("httpSampler", httpSampler);

//      HashTree testPlanTree1 = new HashTree();
//      testPlanTree1.add(testPlanTree);

    
      jmeter.configure(testPlanTree);
//      try {
         jmeter.run();
         System.out.println("after run before stop");
         jmeter.askThreadsToStop();
         jmeter.stopEngine();
         
//      } catch (R e1) {
         // TODO Auto-generated catch block
//         e1.printStackTrace();
//      }
      
    
      
      System.out.print("Jmeter run..");
   }   

}
