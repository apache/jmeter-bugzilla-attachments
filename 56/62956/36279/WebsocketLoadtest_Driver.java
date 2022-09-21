


@JmeterTest(threadNum = 2)
@ClientEndpoint()
public class WebsocketLoadtest implements JavaSamplerClient {
    //the URI could be exposed as gradle property for actual usage
    private static final String WS_URI = "ws://localhost:9002/";
    private static final String WS_MESSAGE = "test message";
    private WebSocketContainer container;

    @Override
    public void setupTest(JavaSamplerContext javaSamplerContext) {

    }

    @Override
    public Arguments getDefaultParameters() {
        return null;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult result = new SampleResult();
        result.sampleStart();

        // all the test logic here
        try {
            System.out.println("!!!system property = " + System.getProperty("remote.bots.host"));
            container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(WS_URI));

        } catch (Throwable throwable) {
            System.out.print("JMeter load test exception: " + throwable.toString());
            throwable.printStackTrace();
            result.setResponseMessage("JMeter load test exception: " + throwable.toString() + "!!!system property = " + System.getProperty("remote.bots.host"));
            result.sampleEnd();
            result.setSuccessful(false);
            return result;
        } finally {
            try {
                ((ContainerLifeCycle)container).stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        result.sampleEnd();
        result.setSuccessful(true);
        result.setResponseMessage("test success");

        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext javaSamplerContext) {
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("ws connection opened!");
        try {
            session.getBasicRemote().sendText(WS_MESSAGE);
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        return null;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("ws connection closed!");
        try {
            ((ContainerLifeCycle) session.getContainer()).stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

}
















public class Driver {
    private static final int MAX_THREAD_PER_ITERATION = Runtime.getRuntime().availableProcessors()*16;

    public static void main(String[] args) throws Exception {


        final String JMETER_PROPERTY = System.getProperty("jmeter.property");
        final String JMETER_HOME = System.getProperty("jmeter.home");
        final String REPORT_DIR = System.getProperty("jmeter.report.dir");
        final String REMOTE_HOST_ADDR = "MY_REMOTE_HOST_ADDRESS";

        if (JMETER_HOME == null || JMETER_PROPERTY == null || REPORT_DIR == null) {
            throw new Exception("Please check if the below system properties are set:\n"
                    + "jmeter.home\njmeter.property\njmeter.report.dir");
        }

        // the sleep time during each sample process in millisecond, default Sleep_Mask = 0xff
        // totalSleepTime = Sleep_Time + (System.currentTimeMillis() % Sleep_Mask)
        final String SAMPLE_SLEEP_TIME = "1000";

        final String REPORT_FILE = REPORT_DIR + "jmeterReport.jtl";
        final String LOG_FILE = REPORT_DIR + "jmeter.log";
        final String TEST_PLAN = REPORT_DIR + "javaTestPlan.jmx";

        JMeterUtils.loadJMeterProperties(JMETER_PROPERTY);
        JMeterUtils.setJMeterHome(JMETER_HOME);
        JMeterUtils.initLocale();

        //bootstrap the JMeter Engine and load properties, set log output files
        //StandardJMeterEngine jmeter = new StandardJMeterEngine();
        ClientJMeterEngine jmeter = new ClientJMeterEngine(REMOTE_HOST_ADDR);

//        Properties props = JMeterUtils.getJMeterProperties();
//        props.setProperty("logfile", LOG_FILE);
//        LoggingManager.initializeLogging(props);
//        LoggingManager.setLoggingLevels(props);

        HashTree testPlanTree = new HashTree();

        Map<String, Integer> testClassMap = getAllTestClasses();
        addLoadTestToTestPlan(testPlanTree, testClassMap, SAMPLE_SLEEP_TIME);

        // save generated test plan to JMeter's .jmx file format
        SaveService.saveTree(testPlanTree, new FileOutputStream(TEST_PLAN));

        //add Summarizer output to get test progress in stdout like:
        //summary =  3 in  34.2s = 0.1/s Avg: 27324 Min: 13778 Max: 34233 Err: 0 (0.00%)
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }

        // Store execution results into a .jtl file
        ResultCollector logger = new ResultCollector(summer);
        logger.setFilename(REPORT_FILE);
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        // Run Test Plan
        jmeter.configure(testPlanTree);
        //jmeter.run();
        jmeter.runTest();
        //jmeter.exit();

        System.out.println("Test completed. See test.jtl file for results");
        System.out.println("JMeter .jmx script is available as javaTestPlan.jmx in build folder");

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread t : threadSet) {
            //System.out.println("thread name is: " + t.getName() );
            //t.join();
        }
    }

    /**
     * Add all load tests to testPlanTree according to JMeter test plan format
     * @param testPlanTree the blank JMeter testPlanTree
     * @param testClassMap (key, value): key=test class name, value=threadNum count
     */
    private static void addLoadTestToTestPlan(HashTree testPlanTree,
                                              Map<String, Integer> testClassMap,
                                              String sampleSleepTime) {

        List<ImmutablePair<ThreadGroup, JavaSampler>> threadSamplerPairList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : testClassMap.entrySet()) {
            int iterCount = 1;
            int threadNum = entry.getValue();
            if (entry.getValue() > MAX_THREAD_PER_ITERATION) {
                iterCount = entry.getValue()/MAX_THREAD_PER_ITERATION + 1;
                threadNum = MAX_THREAD_PER_ITERATION;
            }

            //Step1: create new java sampler
            JavaSampler javaSampler = new JavaSampler();
            javaSampler.setProperty(TestElement.GUI_CLASS, JavaTestSamplerGui.class.getName());
            javaSampler.setProperty(TestElement.TEST_CLASS, JavaSampler.class.getName());
            javaSampler.setName("Jmeter_LoadTest_" + entry.getKey());
            javaSampler.setClassname(entry.getKey());
            Arguments arguments = new Arguments();
            arguments.addArgument("SleepTime", sampleSleepTime);
            javaSampler.setArguments(arguments);

            //Step2: create loop controller for the sampler
            TestElement loopController = new LoopController();
            ((LoopController) loopController).setLoops(iterCount);
            loopController.addTestElement(javaSampler);
            ((LoopController) loopController).setFirst(true);
            loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
            loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
            ((LoopController) loopController).initialize();

            //Step3: create thread group and set thread count
            //we'll set the ramp up period same as thread count, so that each thread starts 1 second after previous thread
            ThreadGroup threadGroup = new ThreadGroup();
            threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
            threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
            threadGroup.setNumThreads(threadNum);
            threadGroup.setRampUp(threadNum);
            threadGroup.setName("Thread Group");
            threadGroup.setSamplerController(((LoopController) loopController));

            ImmutablePair<ThreadGroup, JavaSampler> pair = new ImmutablePair<>(threadGroup, javaSampler);
            threadSamplerPairList.add(pair);
        }

        //Step4: create a test plan and append to the test plan tree
        TestPlan testPlan = new TestPlan("Java Load Test Plan");
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        testPlanTree.add(testPlan);
        HashTree threadGroupHashTree = null;
        for (ImmutablePair<ThreadGroup, JavaSampler> pair : threadSamplerPairList) {
            threadGroupHashTree = testPlanTree.add(testPlan, pair.getLeft());
            threadGroupHashTree.add(pair.getRight());
        }

    }

    /**
     * @return map containing key = class name, value = associated threadNum count
     */
    private static Map<String, Integer> getAllTestClasses(){
        Map<String, Integer> testClassMap = new HashedMap<>();

        Reflections ref = new Reflections("WebsocketLoadtest");

        for (Class<?> cl : ref.getTypesAnnotatedWith(JmeterTest.class)) {
            JmeterTest jt = cl.getAnnotation(JmeterTest.class);
            testClassMap.put(cl.getName(), jt.threadNum());
        }

        return testClassMap;
    }

}
