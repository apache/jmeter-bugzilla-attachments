Index: src/protocol/http/org/apache/jmeter/protocol/http/sampler/HTTPSamplerBase.java
===================================================================
--- src/protocol/http/org/apache/jmeter/protocol/http/sampler/HTTPSamplerBase.java	(revision 513802)
+++ src/protocol/http/org/apache/jmeter/protocol/http/sampler/HTTPSamplerBase.java	(working copy)
@@ -395,7 +395,13 @@
 		}
 		this.getArguments().addArgument(arg);
 	}
-
+    
+    public void addNonEncodedArgument(String name, String value, String metadata) {
+        HTTPArgument arg = new HTTPArgument(name, value, metadata, false);
+        arg.setAlwaysEncoded(false);
+        this.getArguments().addArgument(arg);
+    }
+    
 	public void addArgument(String name, String value) {
 		this.getArguments().addArgument(new HTTPArgument(name, value));
 	}
