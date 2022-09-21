/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.examples.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.LogManager;

import org.apache.http.HttpConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class Metrics {

    private static void initLogging() throws Exception {
        LogManager mgr = LogManager.getLogManager();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.println("handlers=java.util.logging.ConsoleHandler");
        pw.println("java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter");
        pw.println("java.util.logging.ConsoleHandler.level = ALL");
        pw.println("org.apache.http.level = FINEST");
        pw.close();
        InputStream ins = new ByteArrayInputStream(os.toByteArray());
        mgr.readConfiguration(ins);
        ins.close();
    }
    public static void main(String[] args) throws Exception {
        initLogging();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpRequestBase req = new HttpGet("http://wiki.apache.org/jakarta-jmeter/JMeterCommitters");
            HttpContext localContext = new BasicHttpContext();
            HttpResponse rsp = httpclient.execute(req, localContext);
            System.err.println("*** "+rsp.getStatusLine());
            HttpConnection conn = (HttpConnection) localContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
            HttpConnectionMetrics metrics = conn.getMetrics();
            long hdr = metrics.getReceivedBytesCount();
            System.err.println("*** HEADER "+hdr);

            HttpEntity entity = rsp.getEntity();
            if (entity != null) {
                EntityUtils.consume(entity);
            }
            long total = metrics.getReceivedBytesCount();
            System.err.println("*** TOTAL "+total);
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

}
