/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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

package org.apache.jmeter.timers;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * This class implements a constant throughput timer. A Constant Throughtput
 * Timer paces the samplers under its influence so that the total number of
 * samples per unit of time approaches a given constant as much as possible.
 *
 * There are two different ways of pacing the requests:
 * <ul>
 * <li>delay each thread according to when it last ran</li>
 * <li>delay each thread according to when any thread last ran</li>
 * </ul>
 */
public class ConstantThroughputTimer extends AbstractTestElement implements Timer, TestListener, TestBean {
    private static final long serialVersionUID = 4;

    private static final int CALC_MODE_1_THIS_THREAD_ONLY = 0;
    private static final int CALC_MODE_2_ALL_ACTIVE_THREADS = 1;
    private static final int CALC_MODE_3_ALL_ACTIVE_THREADS_IN_CURRENT_THREAD_GROUP = 2;
    private static final int CALC_MODE_4_ALL_ACTIVE_THREADS_SHARED = 3;
    private static final int CALC_MODE_5_ALL_ACTIVE_THREADS_IN_CURRENT_THREAD_GROUP_SHARED = 4;

    private static final Logger log = LoggingManager.getLoggerForClass();

    protected static final double MILLISEC_PER_MIN = 60000.0;

    /**
     * Target time for the start of the next request. The delay provided by the
     * timer will be calculated so that the next request happens at this time.
     */
    private ThroughputInfo throughputInfo = new ThroughputInfo();

    private String calcMode; // String representing the mode
                                // (Locale-specific)

    private int modeInt; // mode as an integer

    /**
     * Desired throughput, in samples per minute.
     */
    private double throughput;

    //For calculating throughput across all threads
    private final static ThroughputInfo allThreadsInfo = new ThroughputInfo();

    //For holding the ThrougputInfo objects for all ThreadGroups. Keyed by AbstractThreadGroup objects
    private final static ConcurrentHashMap<AbstractThreadGroup, ThroughputInfo> threadGroupsInfoMap =
        new ConcurrentHashMap<AbstractThreadGroup, ThroughputInfo>();


    /**
     * Constructor for a non-configured ConstantThroughputTimer.
     */
    public ConstantThroughputTimer() {
    }

    /**
     * Sets the desired throughput.
     *
     * @param throughput
     *            Desired sampling rate, in samples per minute.
     */
    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }

    /**
     * Gets the configured desired throughput.
     *
     * @return the rate at which samples should occur, in samples per minute.
     */
    public double getThroughput() {
        return throughput;
    }

    public String getCalcMode() {
        return calcMode;
    }

    // Needed by test code
    int getCalcModeInt() {
        return modeInt;
    }

    /**
     * Setting this has the side effect of sharing <code>throughputInfo</code> if the mode is
     * <em>shared</em>.
     *
     * @param mode
     *            the delay calculation mode
     */
    public void setCalcMode(String mode) {
        this.calcMode = mode;
        // TODO find better way to get modeInt
        this.modeInt = ConstantThroughputTimerBeanInfo.getCalcModeAsInt(calcMode);

        switch (modeInt) {
        case CALC_MODE_4_ALL_ACTIVE_THREADS_SHARED:
            throughputInfo = allThreadsInfo;
            break;

        case CALC_MODE_5_ALL_ACTIVE_THREADS_IN_CURRENT_THREAD_GROUP_SHARED:
            final org.apache.jmeter.threads.AbstractThreadGroup group = JMeterContextService
                    .getContext().getThreadGroup();
            /*
             * Share the first thread's throughputInfo
             */
            threadGroupsInfoMap.putIfAbsent(group, throughputInfo);
            throughputInfo = threadGroupsInfoMap.get(group);
            break;
        }
    }

    /**
     * Retrieve the delay to use during test execution.
     *
     * @see org.apache.jmeter.timers.Timer#delay()
     */
    public long delay() {
        return throughputInfo.calculateDelay(calculateDelayForMode());
    }

    /**
     * <p>
     * Calculate the delay based on the mode
     * </p>
     *
     * @return the delay (how long before another request should be made) in
     *         milliseconds
     */
    private long calculateDelayForMode() {
        long delay = 0;
        // N.B. we fetch the throughput each time, as it may vary during a test
        double msPerRequest = (MILLISEC_PER_MIN / getThroughput());
        switch (modeInt) {
        case CALC_MODE_2_ALL_ACTIVE_THREADS:
            /*
             * Each request is allowed to run every msPerRequest. Each thread
             * can run a request, so each thread needs to be delayed by the
             * total pool size of threads to keep the expected throughput.
             */
            delay = (long) (JMeterContextService.getNumberOfThreads() * msPerRequest);
            break;

        case CALC_MODE_3_ALL_ACTIVE_THREADS_IN_CURRENT_THREAD_GROUP:
            /*
             * Each request is allowed to run every msPerRequest. Each thread
             * can run a request, so each thread needs to be delayed by the
             * total pool size of threads to keep the expected throughput.
             */
            delay = (long) (JMeterContextService.getContext().getThreadGroup().getNumberOfThreads() * msPerRequest);
            break;

        /*
         * The following modes all fall through for the default
         */
        case CALC_MODE_1_THIS_THREAD_ONLY:
        case CALC_MODE_4_ALL_ACTIVE_THREADS_SHARED:
        case CALC_MODE_5_ALL_ACTIVE_THREADS_IN_CURRENT_THREAD_GROUP_SHARED:
        default:
            delay = (long) msPerRequest;
            break;
        }
        return delay;
    }

    private synchronized void reset() {
        throughputInfo = new ThroughputInfo();
        setCalcMode(calcMode);
    }

    /**
     * Provide a description of this timer class.
     *
     * TODO: Is this ever used? I can't remember where. Remove if it isn't --
     * TODO: or obtain text from bean's displayName or shortDescription.
     *
     * @return the description of this timer class.
     */
    @Override
    public String toString() {
        return JMeterUtils.getResString("constant_throughput_timer_memo"); //$NON-NLS-1$
    }

    /**
     * Get the timer ready to compute delays for a new test.
     * <p>
     * {@inheritDoc}
     */
    public void testStarted()
    {
        log.debug("Test started - reset throughput calculation.");
        reset();
    }

    /**
     * {@inheritDoc}
     */
    public void testEnded() {
        /*
         * There is no way to clean up static variables. The best place to do
         * that is in the testEnded() call as this wont affect a running test.
         * If these are in testStarted then they affect already initialised
         * objects.
         */
        allThreadsInfo.reset();
        threadGroupsInfoMap.clear();
    }

    /**
     * {@inheritDoc}
     */
    public void testStarted(String host) {
        testStarted();
    }

    /**
     * {@inheritDoc}
     */
    public void testEnded(String host) {
    }

    /**
     * {@inheritDoc}
     */
    public void testIterationStart(LoopIterationEvent event) {
    }
}