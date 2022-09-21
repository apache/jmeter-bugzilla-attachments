Index: src/protocol/http/org/apache/jmeter/protocol/http/proxy/HttpRequestHdr.java
===================================================================
--- src/protocol/http/org/apache/jmeter/protocol/http/proxy/HttpRequestHdr.java	(revision 513802)
+++ src/protocol/http/org/apache/jmeter/protocol/http/proxy/HttpRequestHdr.java	(working copy)
@@ -246,8 +246,10 @@
 			sampler.setFileField(urlConfig.getFileFieldName());
 			sampler.setFilename(urlConfig.getFilename());
 			sampler.setMimetype(urlConfig.getMimeType());
+        } else if (postData != null && postData.trim().startsWith("<?")) {
+            sampler.addNonEncodedArgument("", postData, ""); //used when postData is pure xml (ex. an xml-rpc call)
 		} else {
-			sampler.parseArguments(postData);
+			sampler.parseArguments(postData); //standard name=value postData
 		}
         if (log.isDebugEnabled())
     		log.debug("sampler path = " + sampler.getPath());
