/*
 * Copyright 2002-2004 The Apache Software Foundation.
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

package org.apache.jmeter.timers;

import java.util.Hashtable;
import java.util.Map;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * This class implements a constant throughput timer. A Constant Throughtput
 * Timer paces the samplers under it's influence so that the total number of
 * samples per unit of time approaches a given constant as much as possible.
 * 
 */
public class ConstantThroughputTimer extends AbstractTestElement implements Timer, TestListener, TestBean {
	private static final long serialVersionUID = 2;
	private static final Logger log = LoggingManager.getLoggerForClass();
	private static final double MILLISEC_PER_MIN = 60000.0;
    
    private String calcMode;
    private int modeInt;
    private double requestsPerMinute;
    private long millisecondsPerRequest;
    
    //For calculating throughput across all threads
    private final static ThrougputInfo allThreadsInfo = new ThrougputInfo();
    
    //For holding the ThrougputInfo objects for all ThreadGroups. Keyed by ThreadGroup objects
    private final static Map threadGroupsInfoMap = new Hashtable();
    
    //For calculating throughput on a single thread
    private final ThrougputInfo thisThreadInfo = new ThrougputInfo();
    
    
    
    public String getCalcMode() {
        return calcMode;
    }

    public void setCalcMode(String mode) {
        // TODO find better way to get modeInt
        this.modeInt = ConstantThroughputTimerBeanInfo.getCalcModeAsInt(mode);
        this.calcMode = mode;
    }
    
    
    public void setThroughput(double requestsPerMinute) {
        if (log.isDebugEnabled()) {
            log.debug(this.threadName() + " - setThroughput(" + requestsPerMinute + ")");
        }
        
        this.requestsPerMinute = requestsPerMinute;
        this.millisecondsPerRequest = (long)(MILLISEC_PER_MIN / this.requestsPerMinute);
        
        if (log.isDebugEnabled()) {
            log.debug(this.threadName() + " - Calculated millisecondsPerRequest=" + this.millisecondsPerRequest);
        }
    }

    public double getThroughput() {
        return this.requestsPerMinute;
    }
    
    
    /**
     * @see org.apache.jmeter.timers.Timer#delay()
     */
    public long delay() {
        final ThrougputInfo currentInfo = getTargetThroughputInfo();
        final long calculatedDelay = this.calculateDelay(currentInfo);
        return Math.max(calculatedDelay, 0);
    }
    
    /**
     * @see org.apache.jmeter.testelement.AbstractTestElement#threadName()
     */
    private String threadName() {
        return Thread.currentThread().getName();
    }

    /**
     * Using the specified mode return the appropriate ThrougputInfo object.
     * 
     * @return The ThrougputInfo for the current mode.
     */
    private ThrougputInfo getTargetThroughputInfo() {
        final ThrougputInfo currentInfo;
        
        if (log.isDebugEnabled()) {
            log.debug(this.threadName() + " - Getting ThroughputInfo for modeInt=" + this.modeInt);
        }
        
        // Use switch statement for performance (large order performance difference over if/else if/else
        switch (this.modeInt) {
            case 2: //All threads in this group
                final JMeterContext context = JMeterContextService.getContext();
                final JMeterThread thread = context.getThread();
                final org.apache.jmeter.threads.ThreadGroup group = thread.getThreadGroup();
                
                if (log.isDebugEnabled()) {
                    log.debug(this.threadName() + " - Getting ThroughputInfo for ThreadGroup=" + group.getName());
                }
                
                synchronized (threadGroupsInfoMap) {
                    ThrougputInfo info = (ThrougputInfo)threadGroupsInfoMap.get(group);
                    if (info == null) {
                        info = new ThrougputInfo();
                        threadGroupsInfoMap.put(group, info);
                    }
                    currentInfo = info;
                }
                
                break;
                
            case 1: //All threads
                currentInfo = allThreadsInfo;
                break;

            case 0: //This thread only
            default:
                currentInfo = thisThreadInfo;
                break;
        }

        return currentInfo;
    }
    
    /**
     * Calculates the the delay needed to achieve the througput using the
     * passed ThrougputInfo object.
     * 
     * @param info The ThrougputInfo object that contains the last scheduled request time and a mutext for synchronization.
     * @return The delay before the next request is executed to achieve the desired throughput.
     */
    private long calculateDelay(ThrougputInfo info) {
        final long now = System.currentTimeMillis();
        final long calculatedDelay;
        
        if (log.isDebugEnabled()) {
            log.debug(this.threadName() + " - now=" + now + ", lastScheduledTime=" + info.lastScheduledTime);
        }
        
        synchronized (info.MUTEX) {
            final long nextRequstTime = info.lastScheduledTime + this.millisecondsPerRequest;
            info.lastScheduledTime = Math.max(now, nextRequstTime);
            calculatedDelay = info.lastScheduledTime - now;
        }
        
        if (log.isDebugEnabled()) {
            log.debug(this.threadName() + " - nextScheduledTime=" + info.lastScheduledTime + ", calculatedDelay=" + calculatedDelay);
        }
        
        return Math.max(calculatedDelay, 0);
    }

    /**
     * Resets all of the ThrougputInfo objects.
     */
    private synchronized void reset() {
        log.debug("Clearing last scheduled times for all scopes.");
        this.thisThreadInfo.lastScheduledTime = 0;
        allThreadsInfo.lastScheduledTime = 0;
        threadGroupsInfoMap.clear();
    }
    
    /**
     * @see org.apache.jmeter.testelement.TestListener#testStarted()
     */
    public void testStarted() {
        reset();
    }
    
    /**
     * @see org.apache.jmeter.testelement.TestListener#testEnded()
     */
    public void testEnded() {
        reset();
    }

    /**
     * @see org.apache.jmeter.testelement.TestListener#testEnded(java.lang.String)
     */
    public void testEnded(String host) {
    }
    /**
     * @see org.apache.jmeter.testelement.TestListener#testIterationStart(org.apache.jmeter.engine.event.LoopIterationEvent)
     */
    public void testIterationStart(LoopIterationEvent event) {
    }
    /**
     * @see org.apache.jmeter.testelement.TestListener#testStarted(java.lang.String)
     */
    public void testStarted(String host) {
    }
}