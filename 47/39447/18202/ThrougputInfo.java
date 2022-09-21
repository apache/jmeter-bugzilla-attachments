/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.apache.jmeter.timers;

/**
 * Simple class used by the {@link org.apache.jmeter.timers.ConstantThroughputTimer} to track the
 * last scheduled request time for a scope and a mutex object to hold while performing calculations
 * on the last scheduled time.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
class ThrougputInfo {
    final Object MUTEX = new Object();
    long lastScheduledTime = 0;
}