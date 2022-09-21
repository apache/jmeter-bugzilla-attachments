Index: /home/sabayonuser/workspace/jmeter 2.2/src/protocol/jms/org/apache/jmeter/protocol/jms/sampler/Receiver.java
===================================================================
--- /home/sabayonuser/workspace/jmeter 2.2/src/protocol/jms/org/apache/jmeter/protocol/jms/sampler/Receiver.java	(revision 710135)
+++ /home/sabayonuser/workspace/jmeter 2.2/src/protocol/jms/org/apache/jmeter/protocol/jms/sampler/Receiver.java	(working copy)
@@ -77,7 +77,7 @@
                     if (reply.getJMSCorrelationID() == null) {
                         log.warn("Received message with correlation id null. Discarding message ...");
                     } else {
-                        MessageAdmin.getAdmin().putReply(reply.getJMSCorrelationID(), reply);
+                        MessageAdmin.getAdmin().putReply(reply.getJMSMessageID(), reply);
                     }
                 }
 
