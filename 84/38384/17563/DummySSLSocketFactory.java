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
package org.apache.jmeter.protocol.mail.sampler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.*;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * @author Marco Vergari
 */
public class DummySSLSocketFactory extends SSLSocketFactory {
  private static Logger log = LoggingManager.getLoggerForClass();
  
  private SSLSocketFactory factory;

  public DummySSLSocketFactory() {
    try {
      SSLContext sslcontext = SSLContext.getInstance("TLS");
      sslcontext.init(null, new TrustManager[] {new DummyTrustManager()}, new java.security.SecureRandom());
      factory = sslcontext.getSocketFactory();
    } catch(Exception e) {
      log.error("Error occurred during initialize sslcontext...", e);
    }
  }

  public static SocketFactory getDefault() {
    return new DummySSLSocketFactory();
  }

  public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
    return factory.createSocket(socket, host, port, autoClose);
  }

  public Socket createSocket(InetAddress addr, int port, InetAddress localAddr, int localPort) throws IOException {
    return factory.createSocket(addr, port, localAddr, localPort);
  }

  public Socket createSocket(InetAddress host, int port) throws IOException {
    return factory.createSocket(host, port);
  }

  public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
    return factory.createSocket(host, port, localHost, localPort);
  }

  public Socket createSocket(String host, int port) throws IOException {
    return factory.createSocket(host, port);
  }

  public String[] getDefaultCipherSuites() {
    return factory.getSupportedCipherSuites();
  }

  public String[] getSupportedCipherSuites() {
    return factory.getSupportedCipherSuites();
  }
}