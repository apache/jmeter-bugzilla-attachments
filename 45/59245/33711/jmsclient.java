package com.example.jmsclient;

import java.util.*;
import javax.net.ssl.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Signature;
import java.security.SignatureSpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.io.FileInputStream;

import org.apache.activemq.ActiveMQConnectionFactory;
 
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;



public class JMSTest {

  public static void main(String args[]) throws Exception {
    String message = "Text message";

    String baseDir = "";
    String keystorePath = "alias-stub.Keystore.jks";
    String keystorePassword = "password";
    char [] keyPwd = keystorePassword.toCharArray();
    String trustStorePath = "truststore.jks";
    String trustStorePassword = "password";
    char [] trustPwd = trustStorePassword.toCharArray();
    String signingAlias = "alias-stub";
    System.out.println(signingAlias);

    System.out.println("step 1: Get keystore");
    KeyStore ks = KeyStore.getInstance("jks");
    ks.load(new FileInputStream(keystorePath), keyPwd);
    PrivateKey key = (PrivateKey) ks.getKey(signingAlias, keyPwd);

    if (key instanceof PrivateKey) {
        System.out.println("step 2: Get signature instance");
        Signature signature = Signature.getInstance("SHA256withRSA");

        System.out.println("step 3: Initiate signature");
        signature.initSign(key);

        System.out.println("step 4: Update signature with message content");
        signature.update(message.getBytes());

        System.out.println("step 5: Sign message wwith private key");
        byte[] messageSignatureByteArray = signature.sign();

        System.out.println("step 6: Encode message to base64");
        String messageSignature =  Base64.getEncoder().encodeToString(messageSignatureByteArray);

        System.out.println("step 7: Get jms header content");
        System.out.println(messageSignature);

        System.out.println("step 8: Add header to jms request");
        HashMap<String,String> jmsHeaders = new HashMap<String, String>();
        jmsHeaders.put("DigitalSignatureConstants.SIGNATURE", messageSignature);
        // ConnectionUtils.sendMessageToQueue(message, jmsHeaders, MQ_INBOUND_QUEUE, configData);

    } else {
        System.out.println("\tError: No private key found on keystore.");
    }

    System.out.println("step 8: Get truststore");
    KeyStore ts = KeyStore.getInstance("JKS");
    ts.load(new FileInputStream(trustStorePath), trustPwd);



    System.out.println("step 9: Do the rest");
  }
}
