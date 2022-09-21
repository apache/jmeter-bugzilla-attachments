<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
-->
<!-- Content Stylesheet for Site -->
<!-- start the processing -->
<!-- ====================================================================== -->
<!-- Main Page Section -->
<!-- ====================================================================== -->
<html><head>
<link rel="stylesheet" type="text/css" href="page_fichiers/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



<title>Apache JMeter - User's Manual: Component Reference</title>
</head>

<body bgcolor="#ffffff" link="#525D76" text="#000000">
<table border="0" cellspacing="0">
<!-- TOP IMAGE -->
<tbody><tr>
<td>
<!-- Need to specify height otherwise iframe seems to grab extra -->
<iframe src="page_fichiers/halfbanner.html" style="border-width:0; float: left" height="102" frameborder="0" scrolling="no"></iframe>
</td>
<td align="left">
<a href="http://www.apache.org/"><img title="Apache Software Foundation" src="page_fichiers/asf-logo.gif" border="0" height="100" width="387"></a>
</td>
<td align="right">
<a href="http://jmeter.apache.org/"><img src="page_fichiers/logo.jpg" alt="Apache JMeter" title="Apache JMeter" border="0" height="102" width="221"></a>
</td>
</tr>
</tbody></table>
<table border="0" cellspacing="4" width="100%">
<tbody><tr><td colspan="2">
<hr noshade="noshade" size="1">
</td></tr>
<tr>
<!-- LEFT SIDE NAVIGATION -->
<td nowrap="true" valign="top" width="20%">
<p><strong>About</strong></p>
<ul>
<li><a href="http://jmeter.apache.org/index.html">Overview</a>
</li>
<li><a href="http://jmeter.apache.org/changes.html">Changes</a>
</li>
<li><a href="http://projects.apache.org/feeds/rss/jmeter.xml">Subscribe to What's New</a>
</li>
<li><a href="http://jmeter.apache.org/issues.html">Issues</a>
</li>
<li><a href="http://www.apache.org/licenses/">License</a>
</li>
<li><a href="http://wiki.apache.org/jmeter/JMeterCommitters">Contributors</a>
</li>
</ul>
<p><strong>Download</strong></p>
<ul>
<li><a href="http://jmeter.apache.org/download_jmeter.cgi">Download Releases</a>
</li>
<li><a href="http://jmeter.apache.org/nightly.html">Developer (Nightly) Builds</a>
</li>
</ul>
<p><strong>Documentation</strong></p>
<ul>
<li><a href="http://jmeter.apache.org/usermanual/index.html">User Manual</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/best-practices.html">Best Practices</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html">Component Reference</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/functions.html">Functions Reference</a>
</li>
<li><a href="http://jmeter.apache.org/api/index.html">Javadocs</a>
</li>
<li><a href="http://jmeter.apache.org/localising/index.html">Localisation (Translator's Guide)</a>
</li>
<li><a href="http://jmeter.apache.org/building.html">Building JMeter and Add-Ons</a>
</li>
<li><a href="http://wiki.apache.org/jmeter">JMeter Wiki</a>
</li>
<li><a href="http://wiki.apache.org/jmeter/JMeterFAQ">FAQ (Wiki)</a>
</li>
</ul>
<p><strong>Tutorials (PDF format)</strong></p>
<ul>
<li><a href="http://jmeter.apache.org/usermanual/jmeter_distributed_testing_step_by_step.pdf">Distributed Testing</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/jmeter_proxy_step_by_step.pdf">Recording Tests</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/junitsampler_tutorial.pdf">JUnit Sampler</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/jmeter_accesslog_sampler_step_by_step.pdf">Access Log Sampler</a>
</li>
<li><a href="http://jmeter.apache.org/extending/jmeter_tutorial.pdf">Extending JMeter</a>
</li>
</ul>
<p><strong>Community</strong></p>
<ul>
<li><a href="http://www.apache.org/foundation/getinvolved.html">Get Involved</a>
</li>
<li><a href="http://jmeter.apache.org/mail.html">Mailing Lists</a>
</li>
<li><a href="http://jmeter.apache.org/svnindex.html">SVN Repositories</a>
</li>
</ul>
<p><strong>Foundation</strong></p>
<ul>
<li><a href="http://www.apache.org/">ASF</a>
</li>
<li><a href="http://www.apache.org/foundation/sponsorship.html">Sponsorship</a>
</li>
<li><a href="http://www.apache.org/foundation/thanks.html">Thanks</a>
</li>
</ul>
</td>
<td align="left" valign="top" width="80%">
<table>
<tbody><tr>
<td bgcolor="#525D76">
<div align="right"><a href="http://jmeter.apache.org/usermanual/index.html"><font color="#ffffff" face="arial,helvetica,sanserif" size="-1">Index</font></a></div>
</td>
<td bgcolor="#525D76">
<div align="right"><a href="http://jmeter.apache.org/usermanual/functions.html"><font color="#ffffff" face="arial,helvetica,sanserif" size="-1">Next</font></a></div>
</td>
<td bgcolor="#525D76">
<div align="right"><a href="http://jmeter.apache.org/usermanual/boss.html"><font color="#ffffff" face="arial,helvetica,sanserif" size="-1">Prev</font></a></div>
</td>
</tr>
</tbody></table>
<br>
<ul>
<li><a href="#introduction">18.0 Introduction</a></li>
<ul>
</ul>
<li><a href="#samplers">18.1 Samplers</a></li>
<ul>
<li><a href="#FTP_Request">FTP Request</a></li>
<li><a href="#HTTP_Request">HTTP Request</a></li>
<li><a href="#JDBC_Request">JDBC Request</a></li>
<li><a href="#Java_Request">Java Request</a></li>
<li><a href="#SOAP/XML-RPC_Request">SOAP/XML-RPC Request</a></li>
<li><a href="#WebService%28SOAP%29_Request">WebService(SOAP) Request</a></li>
<li><a href="#LDAP_Request">LDAP Request</a></li>
<li><a href="#LDAP_Extended_Request">LDAP Extended Request</a></li>
<li><a href="#Access_Log_Sampler">Access Log Sampler</a></li>
<li><a href="#BeanShell_Sampler">BeanShell Sampler</a></li>
<li><a href="#BSF_Sampler">BSF Sampler</a></li>
<li><a href="#JSR223_Sampler">JSR223 Sampler</a></li>
<li><a href="#TCP_Sampler">TCP Sampler</a></li>
<li><a href="#JMS_Publisher">JMS Publisher</a></li>
<li><a href="#JMS_Subscriber">JMS Subscriber</a></li>
<li><a href="#JMS_Point-to-Point">JMS Point-to-Point</a></li>
<li><a href="#JUnit_Request">JUnit Request</a></li>
<li><a href="#Mail_Reader_Sampler">Mail Reader Sampler</a></li>
<li><a href="#Test_Action">Test Action</a></li>
<li><a href="#SMTP_Sampler">SMTP Sampler</a></li>
<li><a href="#OS_Process_Sampler">OS Process Sampler</a></li>
</ul>
<li><a href="#logic_controllers">18.2 Logic Controllers</a></li>
<ul>
<li><a href="#Simple_Controller">Simple Controller</a></li>
<li><a href="#Loop_Controller">Loop Controller</a></li>
<li><a href="#Once_Only_Controller">Once Only Controller</a></li>
<li><a href="#Interleave_Controller">Interleave Controller</a></li>
<li><a href="#Random_Controller">Random Controller</a></li>
<li><a href="#Random_Order_Controller">Random Order Controller</a></li>
<li><a href="#Throughput_Controller">Throughput Controller</a></li>
<li><a href="#Runtime_Controller">Runtime Controller</a></li>
<li><a href="#If_Controller">If Controller</a></li>
<li><a href="#While_Controller">While Controller</a></li>
<li><a href="#Switch_Controller">Switch Controller</a></li>
<li><a href="#ForEach_Controller">ForEach Controller</a></li>
<li><a href="#Module_Controller">Module Controller</a></li>
<li><a href="#Include_Controller">Include Controller</a></li>
<li><a href="#Transaction_Controller">Transaction Controller</a></li>
<li><a href="#Recording_Controller">Recording Controller</a></li>
</ul>
<li><a href="#listeners">18.3 Listeners</a></li>
<ul>
<li><a href="#Sample_Result_Save_Configuration">Sample Result Save Configuration</a></li>
<li><a href="#Graph_Results">Graph Results</a></li>
<li><a href="#Spline_Visualizer">Spline Visualizer</a></li>
<li><a href="#Assertion_Results">Assertion Results</a></li>
<li><a href="#View_Results_Tree">View Results Tree</a></li>
<li><a href="#Aggregate_Report">Aggregate Report</a></li>
<li><a href="#View_Results_in_Table">View Results in Table</a></li>
<li><a href="#Simple_Data_Writer">Simple Data Writer</a></li>
<li><a href="#Monitor_Results">Monitor Results</a></li>
<li><a href="#Distribution_Graph_%28alpha%29">Distribution Graph (alpha)</a></li>
<li><a href="#Aggregate_Graph">Aggregate Graph</a></li>
<li><a href="#Response_Time_Graph">Response Time Graph</a></li>
<li><a href="#Mailer_Visualizer">Mailer Visualizer</a></li>
<li><a href="#BeanShell_Listener">BeanShell Listener</a></li>
<li><a href="#Summary_Report">Summary Report</a></li>
<li><a href="#Save_Responses_to_a_file">Save Responses to a file</a></li>
<li><a href="#BSF_Listener">BSF Listener</a></li>
<li><a href="#JSR223_Listener">JSR223 Listener</a></li>
<li><a href="#Generate_Summary_Results">Generate Summary Results</a></li>
<li><a href="#Comparison_Assertion_Visualizer">Comparison Assertion Visualizer</a></li>
</ul>
<li><a href="#config_elements">18.4 Configuration Elements</a></li>
<ul>
<li><a href="#CSV_Data_Set_Config">CSV Data Set Config</a></li>
<li><a href="#FTP_Request_Defaults">FTP Request Defaults</a></li>
<li><a href="#HTTP_Authorization_Manager">HTTP Authorization Manager</a></li>
<li><a href="#HTTP_Cache_Manager">HTTP Cache Manager</a></li>
<li><a href="#HTTP_Cookie_Manager">HTTP Cookie Manager</a></li>
<li><a href="#HTTP_Request_Defaults">HTTP Request Defaults</a></li>
<li><a href="#HTTP_Header_Manager">HTTP Header Manager</a></li>
<li><a href="#Java_Request_Defaults">Java Request Defaults</a></li>
<li><a href="#JDBC_Connection_Configuration">JDBC Connection Configuration</a></li>
<li><a href="#Keystore_Configuration">Keystore Configuration</a></li>
<li><a href="#Login_Config_Element">Login Config Element</a></li>
<li><a href="#LDAP_Request_Defaults">LDAP Request Defaults</a></li>
<li><a href="#LDAP_Extended_Request_Defaults">LDAP Extended Request Defaults</a></li>
<li><a href="#TCP_Sampler_Config">TCP Sampler Config</a></li>
<li><a href="#User_Defined_Variables">User Defined Variables</a></li>
<li><a href="#Random_Variable">Random Variable</a></li>
<li><a href="#Counter">Counter</a></li>
<li><a href="#Simple_Config_Element">Simple Config Element</a></li>
</ul>
<li><a href="#assertions">18.5 Assertions</a></li>
<ul>
<li><a href="#Response_Assertion">Response Assertion</a></li>
<li><a href="#Duration_Assertion">Duration Assertion</a></li>
<li><a href="#Size_Assertion">Size Assertion</a></li>
<li><a href="#XML_Assertion">XML Assertion</a></li>
<li><a href="#BeanShell_Assertion">BeanShell Assertion</a></li>
<li><a href="#MD5Hex_Assertion">MD5Hex Assertion</a></li>
<li><a href="#HTML_Assertion">HTML Assertion</a></li>
<li><a href="#XPath_Assertion">XPath Assertion</a></li>
<li><a href="#XML_Schema_Assertion">XML Schema Assertion</a></li>
<li><a href="#BSF_Assertion">BSF Assertion</a></li>
<li><a href="#JSR223_Assertion">JSR223 Assertion</a></li>
<li><a href="#Compare_Assertion">Compare Assertion</a></li>
<li><a href="#SMIME_Assertion">SMIME Assertion</a></li>
</ul>
<li><a href="#timers">18.6 Timers</a></li>
<ul>
<li><a href="#Constant_Timer">Constant Timer</a></li>
<li><a href="#Gaussian_Random_Timer">Gaussian Random Timer</a></li>
<li><a href="#Uniform_Random_Timer">Uniform Random Timer</a></li>
<li><a href="#Constant_Throughput_Timer">Constant Throughput Timer</a></li>
<li><a href="#Synchronizing_Timer">Synchronizing Timer</a></li>
<li><a href="#BeanShell_Timer">BeanShell Timer</a></li>
<li><a href="#BSF_Timer">BSF Timer</a></li>
<li><a href="#JSR223_Timer">JSR223 Timer</a></li>
<li><a href="#Poisson_Random_Timer">Poisson Random Timer</a></li>
</ul>
<li><a href="#preprocessors">18.7 Pre Processors</a></li>
<ul>
<li><a href="#HTML_Link_Parser">HTML Link Parser</a></li>
<li><a href="#HTTP_URL_Re-writing_Modifier">HTTP URL Re-writing Modifier</a></li>
<li><a href="#HTML_Parameter_Mask">HTML Parameter Mask</a></li>
<li><a href="#User_Parameters">User Parameters</a></li>
<li><a href="#BeanShell_PreProcessor">BeanShell PreProcessor</a></li>
<li><a href="#BSF_PreProcessor">BSF PreProcessor</a></li>
<li><a href="#JSR223_PreProcessor">JSR223 PreProcessor</a></li>
<li><a href="#JDBC_PreProcessor">JDBC PreProcessor</a></li>
</ul>
<li><a href="#postprocessors">18.8 Post-Processors</a></li>
<ul>
<li><a href="#Regular_Expression_Extractor">Regular Expression Extractor</a></li>
<li><a href="#XPath_Extractor">XPath Extractor</a></li>
<li><a href="#Result_Status_Action_Handler">Result Status Action Handler</a></li>
<li><a href="#BeanShell_PostProcessor">BeanShell PostProcessor</a></li>
<li><a href="#BSF_PostProcessor">BSF PostProcessor</a></li>
<li><a href="#JSR223_PostProcessor">JSR223 PostProcessor</a></li>
<li><a href="#JDBC_PostProcessor">JDBC PostProcessor</a></li>
</ul>
<li><a href="#Miscellaneous_Features">18.9 Miscellaneous Features</a></li>
<ul>
<li><a href="#Test_Plan">Test Plan</a></li>
<li><a href="#Thread_Group">Thread Group</a></li>
<li><a href="#WorkBench">WorkBench</a></li>
<li><a href="#SSL_Manager">SSL Manager</a></li>
<li><a href="#HTTP_Proxy_Server">HTTP Proxy Server</a></li>
<li><a href="#HTTP_Mirror_Server">HTTP Mirror Server</a></li>
<li><a href="#Property_Display">Property Display</a></li>
<li><a href="#Debug_Sampler">Debug Sampler</a></li>
<li><a href="#Debug_PostProcessor">Debug PostProcessor</a></li>
<li><a href="#Test_Fragment">Test Fragment</a></li>
<li><a href="#setUp_Thread_Group">setUp Thread Group</a></li>
<li><a href="#tearDown_Thread_Group">tearDown Thread Group</a></li>
</ul>
</ul>
<table border="0" cellpadding="2" cellspacing="0" width="100%">
<tbody><tr><td bgcolor="#525D76">
<font color="#ffffff" face="arial,helvetica,sanserif">
<a name="introduction"><strong>18.0 Introduction</strong></a><a class="sectionlink" href="#introduction" title="Link to here">¶</a></font>
</td></tr>
<tr><td>
<blockquote>
<description>


<p>



</p>

 
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>
 Several test elements use JMeter properties to control their behaviour.
 These properties are normally resolved when the class is loaded.
 This generally occurs before the test plan starts, so it's not possible
 to change the settings by using the __setProperty() function.

</td></tr>
</tbody></table>
<p></p>


<p>


</p>


</description>
</blockquote>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<table border="0" cellpadding="2" cellspacing="0" width="100%">
<tbody><tr><td bgcolor="#525D76">
<font color="#ffffff" face="arial,helvetica,sanserif">
<a name="samplers"><strong>18.1 Samplers</strong></a><a class="sectionlink" href="#samplers" title="Link to here">¶</a></font>
</td></tr>
<tr><td>
<blockquote>
<description>

	
<p>

	Samplers perform the actual work of JMeter.
	Each sampler (except Test Action) generates one or more sample results.
	The sample results have various attributes (success/fail, elapsed time,
 data size etc) and can be viewed in the various listeners.
	
</p>


</description>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="FTP_Request">18.1.1 FTP Request</a>
<a class="sectionlink" href="#FTP_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>

This controller lets you send an FTP "retrieve file" or "upload file" request to an FTP server.
If you are going to send multiple requests to the same FTP server, consider
using a 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#FTP_Request_Defaults">FTP Request Defaults</a>
 Configuration
Element so you do not have to enter the same information for each FTP Request Generative
Controller. When downloading a file, it can be stored on disk (Local File) or in the Response Data, or both.

<p>

Latency is set to the time it takes to login (versions of JMeter after 2.3.1).

</p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/ftp-request.png" height="289" width="519"></div>
<p>
<b>Parameters</b>
<a name="FTP_Request_parms">
</a><a class="sectionlink" href="#FTP_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Server Name or IP</td>
<td>Domain name or IP address of the FTP server.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Port</td>
<td>Port to use. If this is  &gt;0, then this specific port is used, otherwise JMeter uses the default FTP port.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Remote File:</td>
<td>File to retrieve or name of destination file to upload.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Local File:</td>
<td>File to upload, or destination for downloads (defaults to remote file name).
</td>
<td>
Yes, if uploading (*)
</td>
</tr>
<tr>
<td>Local File Contents:</td>
<td>Provides the contents for the upload, overrides the Local File property.
</td>
<td>
Yes, if uploading (*)
</td>
</tr>
<tr>
<td>get(RETR) / put(STOR)</td>
<td>Whether to retrieve or upload a file.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Use Binary mode ?</td>
<td>Check this to use Binary mode (default Ascii)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Save File in Response ?</td>
<td>
        Whether to store contents of retrieved file in response data.
        If the mode is Ascii, then the contents will be visible in the Tree View Listener.
        
</td>
<td>
Yes, if downloading
</td>
</tr>
<tr>
<td>Username</td>
<td>FTP account username.
</td>
<td>
Usually
</td>
</tr>
<tr>
<td>Password</td>
<td>FTP account password. N.B. This will be visible in the test plan.
</td>
<td>
Usually
</td>
</tr>
</tbody></table>
<p></p>
<p><b>See Also:</b>
</p><ul>
<li><a href="http://jmeter.apache.org/usermanual/test_plan.html#assertions">Assertions</a></li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#FTP_Request_Defaults">FTP Request Defaults</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/build-ftp-test-plan.html">Building an FTP Test Plan</a></li>
</ul>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="HTTP_Request">18.1.2 HTTP Request</a>
<a class="sectionlink" href="#HTTP_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>

        
<p>
This sampler lets you send an HTTP/HTTPS request to a web server.  It
        also lets you control whether or not JMeter parses HTML files for images and
        other embedded resources and sends HTTP requests to retrieve them.
        The following types of embedded resource are retrieved:
</p>

        
<ul>

        
<li>
images
</li>

        
<li>
applets
</li>

        
<li>
stylesheets
</li>

        
<li>
external scripts
</li>

        
<li>
frames, iframes
</li>

        
<li>
background images (body, table, TD, TR)
</li>

        
<li>
background sound
</li>

        
</ul>

        
<p>

        The default parser is htmlparser.
        This can be changed by using the property "htmlparser.classname" - see jmeter.properties for details.
        
</p>

        
<p>
If you are going to send multiple requests to the same web server, consider
        using an 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request_Defaults">HTTP Request Defaults</a>

        Configuration Element so you do not have to enter the same information for each
        HTTP Request.
</p>


        
<p>
Or, instead of manually adding HTTP Requests, you may want to use
        JMeter's 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Proxy_Server">HTTP Proxy Server</a>
 to create
        them.  This can save you time if you have a lot of HTTP requests or requests with many
        parameters.
</p>


        
<p>
<b>
There are two different screens for defining the samplers:
</b>

        
</p><ul>

        
<li>
AJP/1.3 Sampler - uses the Tomcat mod_jk protocol (allows testing of Tomcat in AJP mode without needing Apache httpd)
        The AJP Sampler does not support multiple file upload; only the first file will be used.
        
</li>

        
<li>
HTTP Request - this has an implementation drop-down box, which selects the HTTP protocol implementation to be used:
</li>

        
<ul>

        
<li>
Java - uses the HTTP implementation provided by the JVM. 
        This has some limitations in comparison with the HttpClient implementations - see below.
</li>

        
<li>
HTTPClient3.1 - uses Apache Commons HttpClient 3.1. 
        This is no longer being developed, and support for this may be dropped in a future JMeter release.
</li>

        
<li>
HTTPClient4 - uses Apache HttpComponents HttpClient 4.x.
</li>

        
</ul>

        
</ul>

         
<p></p>

         
<p>
The Java HTTP implementation has some limitations:
</p>

         
<ul>

         
<li>
There is no control over how connections are re-used. 
         When a connection is released by JMeter, it may or may not be re-used by the same thread.
</li>

         
<li>
The API is best suited to single-threaded usage - various settings (e.g. proxy) 
         are defined via system properties, and therefore apply to all connections.
</li>

         
<li>
There is a bug in the handling of HTTPS via a Proxy (the CONNECT is not handled correctly).
         See Java bugs 6226610 and 6208335.
         
</li>

         
<li>
It does not support virtual hosts.
</li>

         
</ul>

         
<p>
Note: the FILE protocol is intended for testing puposes only. 
         It is handled by the same code regardless of which HTTP Sampler is used.
</p>

        
<p>
If the request requires server or proxy login authorization (i.e. where a browser would create a pop-up dialog box),
         you will also have to add an 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Authorization_Manager">HTTP Authorization Manager</a>
 Configuration Element.
         For normal logins (i.e. where the user enters login information
 in a form), you will need to work out what the form submit button does,
         and create an HTTP request with the appropriate method (usually
 POST) 
         and the appropriate parameters from the form definition. 
         If the page uses HTTP, you can use the JMeter Proxy to capture 
the login sequence.
        
</p>

        
<p>

        In versions of JMeter up to 2.2, only a single SSL context was used for all threads and samplers.
        This did not generate the proper load for multiple users.
        A separate SSL context is now used for each thread.
        To revert to the original behaviour, set the JMeter property:

</p><pre>
https.sessioncontext.shared=true

</pre>

        By default, the SSL context is retained for the duration of the test.
        In versions of JMeter from 2.5.1, the SSL session can be optionally reset for each test iteration.
        To enable this, set the JMeter property:

<pre>
https.use.cached.ssl.context=false

</pre>

        Note: this does not apply to the Java HTTP implementation.
        
<p></p>

        
<p>

        JMeter defaults to the SSL protocol level TLS.
        If the server needs a different level, e.g. SSLv3, change the JMeter property, for example:

</p><pre>
https.default.protocol=SSLv3

</pre>
 
        
<p></p>

        
<p>

        JMeter also allows one to enable additional protocols, by changing the property 
<tt>
https.socket.protocols
</tt>
.
        
</p>

        
<p>
If the request uses cookies, then you will also need an
        
<a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Cookie_Manager">HTTP Cookie Manager</a>
.  You can
        add either of these elements to the Thread Group or the HTTP Request.  If you have
        more than one HTTP Request that needs authorizations or cookies, then add the
        elements to the Thread Group.  That way, all HTTP Request controllers will share the
        same Authorization Manager and Cookie Manager elements.
</p>


        
<p>
If the request uses a technique called "URL Rewriting" to maintain sessions,
        then see section
        
<a href="http://jmeter.apache.org/usermanual/build-adv-web-test-plan.html#session_url_rewriting">
6.1 Handling User Sessions With URL Rewriting
</a>

        for additional configuration steps.
</p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/http-request.png" height="674" width="907"></div>
<p>
<b>Parameters</b>
<a name="HTTP_Request_parms">
</a><a class="sectionlink" href="#HTTP_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Server</td>
<td>
            Domain name or IP address of the web server. e.g. www.example.com. [Do not include the http:// prefix.]
            Note: in JMeter 2.5 (and later) if the "Host" header is defined in a Header Manager, then this will be used
            as the virtual host name.
        
</td>
<td>
Yes, unless provided by HTTP Request Defaults
</td>
</tr>
<tr>
<td>Port</td>
<td>Port the web server is listening to. Default: 80
</td>
<td>
No
</td>
</tr>
<tr>
<td>Connect Timeout</td>
<td>Connection Timeout. Number of milliseconds to wait for a connection to open.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Response Timeout</td>
<td>Response Timeout. Number of milliseconds to wait for a response.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Server (proxy)</td>
<td>Hostname or IP address of a proxy server to perform request. [Do not include the http:// prefix.]
</td>
<td>
No
</td>
</tr>
<tr>
<td>Port</td>
<td>Port the proxy server is listening to.
</td>
<td>
No, unless proxy hostname is specified
</td>
</tr>
<tr>
<td>Username</td>
<td>(Optional) username for proxy server.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Password</td>
<td>(Optional) password for proxy server. (N.B. this is stored unencrypted in the test plan)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Implementation</td>
<td>Java, HttpClient3.1, HttpClient4. 
        If not specified (and not defined by HTTP Request Defaults), the default depends on the value of the JMeter property
        
<code>
jmeter.httpsampler
</code>
, failing that, the Java implementation is used.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Protocol</td>
<td>HTTP, HTTPS or FILE. Default: HTTP
</td>
<td>
No
</td>
</tr>
<tr>
<td>Method</td>
<td>GET, POST, HEAD, TRACE, OPTIONS, PUT, DELETE, PATCH (not supported for JAVA implementation)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Content Encoding</td>
<td>
        Content encoding to be used (for POST, PUT, PATCH and FILE). 
        This the the character encoding to be used, and is not related to the Content-Encoding HTTP header.
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Redirect Automatically</td>
<td>
		Sets the underlying http protocol handler to automatically follow redirects,
		so they are not seen by JMeter, and thus will not appear as samples.
		Should only be used for GET and HEAD requests.
		The HttpClient sampler will reject attempts to use it for POST or PUT.
		
<b>
Warning: see below for information on cookie and header handling.
</b>

        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Follow Redirects</td>
<td>
		This only has any effect if "Redirect Automatically" is not enabled.
		If set, the JMeter sampler will check if the response is a redirect and follow it if so.
		The initial redirect and further responses will appear as additional samples.
        The URL and data fields of the parent sample will be taken from the final (non-redirected)
        sample, but the parent byte count and elapsed time include all samples.
        The latency is taken from the initial response (versions of JMeter after 2.3.4 - previously it was zero).
		Note that the HttpClient sampler may log the following message:
<br>


		"Redirect requested but followRedirects is disabled"
<br>


		This can be ignored.
        
<br>


        In versions after 2.3.4, JMeter will collapse paths of the form '/../segment' in
        both absolute and relative redirect URLs. For example http://host/one/../two =&gt; http://host/two.
        If necessary, this behaviour can be suppressed by setting the JMeter property
        
<code>
httpsampler.redirect.removeslashdotdot=false
</code>

		
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use KeepAlive</td>
<td>JMeter sets the Connection: keep-alive header. This does not work 
properly with the default HTTP implementation, as connection re-use is 
not under user-control. 
                  It does work with the Apache HttpComponents HttpClient
 implementations.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use multipart/form-data for HTTP POST</td>
<td>
        Use a multipart/form-data or application/x-www-form-urlencoded post request
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Browser-compatible headers</td>
<td>
        When using multipart/form-data, this suppresses the Content-Type and 
        Content-Transfer-Encoding headers; only the Content-Disposition header is sent.
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Path</td>
<td>The path to resource (for example, /servlets/myServlet). If the
resource requires query string parameters, add them below in the
"Send Parameters With the Request" section.

<b>

As a special case, if the path starts with "http://" or "https://" then this is used as the full URL.

</b>

In this case, the server, port and protocol are ignored; parameters are also ignored for GET and DELETE methods.

</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Send Parameters With the Request</td>
<td>The query string will
        be generated from the list of parameters you provide.  Each parameter has a 
<i>
name
</i>
 and
        
<i>
value
</i>
, the options to encode the parameter, and an option to include or 
exclude an equals sign (some applications
        don't expect an equals when the value is the empty string).  The
 query string will be generated in the correct fashion, depending on
        the choice of "Method" you made (ie if you chose GET or DELETE, 
the query string will be
        appended to the URL, if POST or PUT, then it will be sent 
separately).  Also, if you are
        sending a file using a multipart form, the query string will be 
created using the
        multipart form specifications.
        
<b>
See below for some further information on parameter handling.
</b>

        
<p>

        Additionally, you can specify whether each parameter should be 
URL encoded.  If you are not sure what this
        means, it is probably best to select it.  If your values contain
 characters such as the following then encoding is usually required.:
        
</p><ul>

            
<li>
ASCII Control Chars
</li>

            
<li>
Non-ASCII characters
</li>

            
<li>
Reserved characters:URLs use some characters for special use in defining
 their syntax. When these characters are not used in their special role 
inside a URL, they need to be encoded, example : '$', '&amp;', '+', ',' ,
 '/', ':', ';', '=', '?', '@'
</li>

            
<li>
Unsafe characters: Some characters present the possibility of being 
misunderstood within URLs for various reasons. These characters should 
also always be encoded, example : ' ', '&lt;', '&gt;', '#', '%', ...
</li>

        
</ul>

        
<p></p>

        
</td>
<td>
No
</td>
</tr>
<tr>
<td>File Path:</td>
<td>Name of the file to send.  If left blank, JMeter
        does not send a file, if filled in, JMeter automatically sends the request as
        a multipart form request.
        
<p>

        If it is a POST or PUT or PATCH request and there is a single 
file whose 'Parameter name' attribute (below) is omitted, 
        then the file is sent as the entire body
        of the request, i.e. no wrappers are added. This allows 
arbitrary bodies to be sent. This functionality is present for POST 
requests
        after version 2.2, and also for PUT requests after version 2.3.
        
<b>
See below for some further information on parameter handling.
</b>

        
</p>

        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Parameter name:</td>
<td>Value of the "name" web request parameter.
</td>
<td>
No
</td>
</tr>
<tr>
<td>MIME Type</td>
<td>MIME type (for example, text/plain).
        If it is a POST or PUT or PATCH request and either the 'name' atribute (below) are omitted or the request body is
        constructed from parameter values only, then the value of this field is used as the value of the
        content-type request header.
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Retrieve All Embedded Resources from HTML Files</td>
<td>Tell JMeter to parse the HTML file
and send HTTP/HTTPS requests for all images, Java applets, JavaScript files, CSSs, etc. referenced in the file.
        See below for more details.
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use as monitor</td>
<td>For use with the 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#Monitor_Results">Monitor Results</a>
 listener.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Save response as MD5 hash?</td>
<td>
       If this is selected, then the response is not stored in the sample result.
       Instead, the 32 character MD5 hash of the data is calculated and stored instead.
       This is intended for testing large amounts of data.
       
</td>
<td>
No
</td>
</tr>
<tr>
<td>Embedded URLs must match:</td>
<td>
        If present, this must be a regular expression that is used to match against any embedded URLs found.
        So if you only want to download embedded resources from http://example.com/, use the expression:
        http://example\.com/.*
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use concurrent pool</td>
<td>Use a pool of concurrent connections to get embedded resources.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Size</td>
<td>Pool size for concurrent connections used to get embedded resources.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Source IP address:</td>
<td>
        [Only for HTTP Request HTTPClient] 
        Override the default local IP address for this sample.
        The JMeter host must have multiple IP addresses (i.e. IP aliases or network interfaces). 
        If the property 
<b>
httpclient.localaddress
</b>
 is defined, that is used for all HttpClient requests.
        
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
<p>


<b>
N.B.
</b>
 when using Automatic Redirection, cookies are only sent for the initial URL.
This can cause unexpected behaviour for web-sites that redirect to a local server.
E.g. if www.example.com redirects to www.example.co.uk.
In this case the server will probably return cookies for both URLs, but JMeter will only see the cookies for the last
host, i.e. www.example.co.uk. If the next request in the test plan uses www.example.com, 
rather than www.example.co.uk, it will not get the correct cookies.
Likewise, Headers are sent for the initial request, and won't be sent for the redirect.
This is generally only a problem for manually created test plans,
as a test plan created using a recorder would continue from the redirected URL.

</p>
<p>


<b>
Parameter Handling:
</b>
<br>


For the POST and PUT method, if there is no file to send, and the name(s) of the parameter(s) are omitted,
then the body is created by concatenating all the value(s) of the parameters.
Note that the values are concatenated without adding any end-of-line characters.
These can be added by using the __char() function in the value fields.
This allows arbitrary bodies to be sent.
The values are encoded if the encoding flag is set (versions of JMeter after 2.3).
See also the MIME Type above how you can control the content-type request header that is sent.

<br>


For other methods, if the name of the parameter is missing,
then the parameter is ignored. This allows the use of optional parameters defined by variables.
(versions of JMeter after 2.3)

</p>
<br>

<p>
Since JMeter 2.6, you have the option to switch to Post Body when a request has only unnamed parameters
(or no parameters at all).
This option is useful in the following cases (amongst others):

</p><ul>


<li>
GWT RPC HTTP Request
</li>


<li>
JSON REST HTTP Request
</li>


<li>
XML REST HTTP Request
</li>


<li>
SOAP HTTP Request
</li>


</ul>

Note that once you leave the Tree node, you cannot switch back to the parameter tab unless you clear the Post Body tab of data.

<p></p>
<p>

In Post Body mode, each line will be sent with CRLF appended, apart from the last line.
To send a CRLF after the last line of data, just ensure that there is an empty line following it.
(This cannot be seen, except by noting whether the cursor can be placed on the subsequent line.)

</p>
<p></p><table border="0" cellpadding="0" cellspacing="0"><tbody><tr><td><img src="page_fichiers/http-request-raw-single-parameter.png" height="421" width="902"><br>
<font size="-1">Figure 1 - HTTP Request with one unnamed parameter
</font></td></tr></tbody></table><p></p>
<p></p><table border="0" cellpadding="0" cellspacing="0"><tbody><tr><td><img src="page_fichiers/http-request-confirm-raw-body.png" height="212" width="908"><br>
<font size="-1">Figure 2 - Confirm dialog to switch
</font></td></tr></tbody></table><p></p>
<p></p><table border="0" cellpadding="0" cellspacing="0"><tbody><tr><td><img src="page_fichiers/http-request-raw-body.png" height="423" width="905"><br>
<font size="-1">Figure 3 - HTTP Request using RAW Post body
</font></td></tr></tbody></table><p></p>
<p>


<b>
Method Handling:
</b>
<br>


The POST, PUT and PATCH request methods work similarly, except that the PUT and PATCH methods do not support multipart requests
or file upload.
The PUT and PATCH method body must be provided as one of the following:

</p><ul>


<li>
define the body as a file with empty Parameter name field; in which case the MIME Type is used as the Content-Type
</li>


<li>
define the body as parameter value(s) with no name
</li>


<li>
use the Post Body tab
</li>


</ul>

If you define any parameters with a name in either the sampler or Http
defaults then nothing is sent.
PUT and PATCH require a Content-Type. 
If not using a file, attach a Header Manager to the sampler and define the Content-Type there.
The GET and DELETE request methods work similarly to each other.

<p></p>
<p>
Upto and including JMeter 2.1.1, only responses with the content-type "text/html" were scanned for
embedded resources. Other content-types were assumed to be something other than HTML.
JMeter 2.1.2 introduces the a new property 
<b>
HTTPResponse.parsers
</b>
, which is a list of parser ids,
 e.g. 
<b>
htmlParser
</b>
 and 
<b>
wmlParser
</b>
. For each id found, JMeter checks two further properties:
</p>
<ul>

 
<li>
id.types - a list of content types
</li>

 
<li>
id.className - the parser to be used to extract the embedded resources
</li>

 
</ul>
<p>
See jmeter.properties file for the details of the settings. 
 If the HTTPResponse.parser property is not set, JMeter reverts to the previous behaviour,
 i.e. only text/html responses will be scanned
</p>
<b>
Emulating slow connections (HttpClient only):
</b>
<br>

<pre>
# Define characters per second &gt; 0 to emulate slow connections
#httpclient.socket.http.cps=0
#httpclient.socket.https.cps=0

</pre>
<p>
<b>
Response size calculation
</b>
<br>


Optional properties to allow change the method to get response size:
<br>



</p><ul>
<li>
Gets the real network size in bytes for the body response

<pre>sampleresult.getbytes.body_real_size=true
</pre>
</li>


<li>
Add HTTP headers to full response size

<pre>sampleresult.getbytes.headers_size=true
</pre>
</li>
</ul>



<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>
The Java and HttpClient3 inplementations do not include transport overhead such as
chunk headers in the response body size.
<br>


The HttpClient4 implementation does include the overhead in the response body size,
so the value may be greater than the number of bytes in the response content.

</td></tr>
</tbody></table>
<p></p>



<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>Versions of JMeter before 2.5 returns only data response size (uncompressed if request uses gzip/defate mode).

<br>

To return to settings before version 2.5, set the two properties to false.
</td></tr>
</tbody></table>
<p></p>


<p></p>
<p>


<b>
Retry handling
</b>
<br>


In version 2.5 of JMeter, the HttpClient4 and Commons HttpClient 3.1 samplers used the default retry count, which was 3.
In later versions, the retry count has been set to 1, which is what the Java implementation appears to do.
The retry count can be overridden by setting the relevant JMeter property, for example:

</p><pre>
httpclient4.retrycount=3
httpclient3.retrycount=3

</pre>


<p></p>
<p><b>See Also:</b>
</p><ul>
<li><a href="http://jmeter.apache.org/usermanual/test_plan.html#assertions">Assertion</a></li>
<li><a href="http://jmeter.apache.org/usermanual/build-web-test-plan.html">Building a Web Test Plan</a></li>
<li><a href="http://jmeter.apache.org/usermanual/build-adv-web-test-plan.html">Building an Advanced Web Test Plan</a></li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Authorization_Manager">HTTP Authorization Manager</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Cookie_Manager">HTTP Cookie Manager</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Header_Manager">HTTP Header Manager</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#HTML_Link_Parser">HTML Link Parser</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Proxy_Server">HTTP Proxy Server</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request_Defaults">HTTP Request Defaults</a>
</li>
<li><a href="http://jmeter.apache.org/usermanual/build-adv-web-test-plan.html#session_url_rewriting">HTTP Requests and Session ID's: URL Rewriting</a></li>
</ul>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="JDBC_Request">18.1.3 JDBC Request</a>
<a class="sectionlink" href="#JDBC_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
This sampler lets you send an JDBC Request (an SQL query) to a database.
</p>


<p>
Before using this you need to set up a

<a href="http://jmeter.apache.org/usermanual/component_reference.html#JDBC_Connection_Configuration">JDBC Connection Configuration</a>
 Configuration element

</p>


<p>

If the Variable Names list is provided, then for each row returned by a Select statement, the variables are set up
with the value of the corresponding column (if a variable name is provided), and the count of rows is also set up.
For example, if the Select statement returns 2 rows of 3 columns, and the variable list is 
<code>
A,,C
</code>
,
then the following variables will be set up:

</p><pre>
A_#=2 (number of rows)
A_1=column 1, row 1
A_2=column 1, row 2
C_#=2 (number of rows)
C_1=column 3, row 1
C_2=column 3, row 2

</pre>

If the Select statement returns zero rows, then the A_# and C_# 
variables would be set to 0, and no other variables would be set.

<p></p>


<p>

Old variables are cleared if necessary - e.g. if the first select retrieves 6 rows and a second select returns only 3 rows,
the additional variables for rows 4, 5 and 6 will be removed.

</p>


<p>


<b>
Note:
</b>
 The latency time is set from the time it took to acquire a connection.

</p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/jdbc-request.png" height="334" width="466"></div>
<p>
<b>Parameters</b>
<a name="JDBC_Request_parms">
</a><a class="sectionlink" href="#JDBC_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Variable Name</td>
<td>
		Name of the JMeter variable that the connection pool is bound to.
		This must agree with the 'Variable Name' field of a JDBC Connection Configuration.
		
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Query Type</td>
<td>Set this according to the statement type:
		    
<ul>

		    
<li>
Select Statement
</li>

		    
<li>
Update Statement - use this for Inserts as well
</li>

		    
<li>
Callable Statement
</li>

		    
<li>
Prepared Select Statement
</li>

		    
<li>
Prepared Update Statement - use this for Inserts as well
</li>

		    
<li>
Commit
</li>

		    
<li>
Rollback
</li>

		    
<li>
Autocommit(false)
</li>

		    
<li>
Autocommit(true)
</li>

		    
<li>
Edit - this should be a variable reference that evaluates to one of the above
</li>

		    
</ul>

		
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>SQL Query</td>
<td>
        SQL query.
        Do not enter a trailing semi-colon.
        There is generally no need to use { and } to enclose Callable statements;
        however they mey be used if the database uses a non-standard syntax.
        [The JDBC driver automatically converts the statement if necessary when it is enclosed in {}].
        For example:
        
<ul>

        
<li>
select * from t_customers where id=23
</li>

        
<li>
CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE (null,?, ?, null, null, null)
        
<ul>

        
<li>
Parameter values: tablename,filename
</li>

        
<li>
Parameter types:  VARCHAR,VARCHAR
</li>

        
</ul>

        
</li>

        The second example assumes you are using Apache Derby.
        
</ul>

        
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Parameter values</td>
<td>
        Comma-separated list of parameter values. Use ]NULL[ to indicate a NULL parameter.
        (If required, the null string can be changed by defining the property "jdbcsampler.nullmarker".)
        
<br>


        The list must be enclosed in double-quotes if any of the values contain a comma or double-quote,
        and any embedded double-quotes must be doubled-up, for example:
        
<pre>"Dbl-Quote: "" and Comma: ,"
</pre>

        There must be as many values as there are placeholders in the statement.
        
</td>
<td>
Yes, if a prepared or callable statement has parameters
</td>
</tr>
<tr>
<td>Parameter types</td>
<td>
        Comma-separated list of SQL parameter types (e.g. INTEGER, DATE, VARCHAR, DOUBLE).
        These are defined as fields in the class java.sql.Types, see for example:
        
<a href="http://download.oracle.com/javase/1.5.0/docs/api/java/sql/Types.html">
Javadoc for java.sql.Types
</a>
.
        [Note: JMeter will use whatever types are defined by the runtime JVM, 
        so if you are running on a different JVM, be sure to check the appropriate document]
        If the callable statement has INOUT or OUT parameters, then these must be indicated by prefixing the
        appropriate parameter types, e.g. instead of "INTEGER", use "INOUT INTEGER".
        If not specified, "IN" is assumed, i.e. "DATE" is the same as "IN DATE".
        
<br>


        If the type is not one of the fields found in java.sql.Types, versions of JMeter after 2.3.2 also
        accept the corresponding integer number, e.g. since INTEGER == 4, you can use "INOUT 4".
        
<br>


        There must be as many types as there are placeholders in the statement.
        
</td>
<td>
Yes, if a prepared or callable statement has parameters
</td>
</tr>
<tr>
<td>Variable Names</td>
<td>Comma-separated list of variable names to hold values returned by 
Select statements, Prepared Select Statements or CallableStatement. 
        Note that when used with CallableStatement, list of variables 
must be in the same sequence as the OUT parameters returned by the call.
        If there are less variable names than OUT parameters only as 
many results shall be stored in the thread-context variables as variable
 names were supplied. 
        If more variable names than OUT parameters exist, the additional
 variables will be ignored
</td>
<td>
No
</td>
</tr>
<tr>
<td>Result Variable Name</td>
<td>
        If specified, this will create an Object variable containing a list of row maps.
        Each map contains the column name as the key and the column data as the value. Usage:
<br>


        
<code>
columnValue = vars.getObject("resultObject").get(0).get("Column Name");
</code>

        
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
<p><b>See Also:</b>
</p><ul>
<li><a href="http://jmeter.apache.org/usermanual/build-db-test-plan.html">Building a Database Test Plan</a></li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#JDBC_Connection_Configuration">JDBC Connection Configuration</a>
</li>
</ul>
<p></p>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>Versions of JMeter after 2.3.2 use UTF-8 as the character encoding. Previously the platform default was used.
</td></tr>
</tbody></table>
<p></p>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>Ensure Variable Name is unique accross Test Plan.
</td></tr>
</tbody></table>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="Java_Request">18.1.4 Java Request</a>
<a class="sectionlink" href="#Java_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
This sampler lets you control a java class that implements the

<b>
<code>
org.apache.jmeter.protocol.java.sampler.JavaSamplerClient
</code>
</b>
 interface.
By writing your own implementation of this interface,
you can use JMeter to harness multiple threads, input parameter control, and
data collection.
</p>


<p>
The pull-down menu provides the list of all such implementations found by
JMeter in its classpath.  The parameters can then be specified in the
table below - as defined by your implementation.  Two simple examples (JavaTest and SleepTest) are provided.

</p>


<p>

The JavaTest example sampler can be useful for checking test plans, because it allows one to set
values in almost all the fields. These can then be used by Assertions, etc.
The fields allow variables to be used, so the values of these can readily be seen.

</p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/java_request.png" height="347" width="563"></div>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>Since JMeter 2.8, if the method teardownTest is not 
overriden by a subclass of AbstractJavaSamplerClient, its teardownTest 
method will not be called.
This reduces JMeter memory requirements.
This will not have any impact on existing Test plans.

</td></tr>
</tbody></table>
<p></p>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>The Add/Delete buttons don't serve any purpose at present.
</td></tr>
</tbody></table>
<p></p>
<p>
<b>Parameters</b>
<a name="Java_Request_parms">
</a><a class="sectionlink" href="#Java_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler
         that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Classname</td>
<td>The specific implementation of
        the JavaSamplerClient interface to be sampled.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Send Parameters with Request</td>
<td>A list of
        arguments that will be passed to the sampled class.  All arguments
        are sent as Strings. See below for specific settings.
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
<p>
The following parameters apply to the 
<b>
SleepTest
</b>
 and 
<b>
JavaTest
</b>
 implementations:
</p>
<p>
<b>Parameters</b>
<a name="Java_Request_parms">
</a><a class="sectionlink" href="#Java_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Sleep_time</td>
<td>How long to sleep for (ms)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Sleep_mask</td>
<td>How much "randomness" to add:
<br>


            The sleep time is calculated as follows:
<br>


            totalSleepTime = SleepTime + (System.currentTimeMillis() % SleepMask)
        
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>
<p>
The following parameters apply additionaly to the 
<b>
JavaTest
</b>
 implementation:
</p>
<p>
<b>Parameters</b>
<a name="Java_Request_parms">
</a><a class="sectionlink" href="#Java_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Label</td>
<td>The label to use. If provided, overrides Name
</td>
<td>
No
</td>
</tr>
<tr>
<td>ResponseCode</td>
<td>If provided, sets the SampleResult ResponseCode.
</td>
<td>
No
</td>
</tr>
<tr>
<td>ResponseMessage</td>
<td>If provided, sets the SampleResult ResponseMessage.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Status</td>
<td>If provided, sets the SampleResult Status. If this equals "OK" 
(ignoring case) then the status is set to success, otherwise the sample 
is marked as failed.
</td>
<td>
No
</td>
</tr>
<tr>
<td>SamplerData</td>
<td>If provided, sets the SampleResult SamplerData.
</td>
<td>
No
</td>
</tr>
<tr>
<td>ResultData</td>
<td>If provided, sets the SampleResult ResultData.
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="SOAP/XML-RPC_Request">18.1.5 SOAP/XML-RPC Request</a>
<a class="sectionlink" href="#SOAP/XML-RPC_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
This sampler lets you send a SOAP request to a webservice.  It can also be
used to send XML-RPC over HTTP.  It creates an HTTP POST request, with the specified XML as the
POST content. 
To change the "Content-type" from the default of "text/xml", use a HeaderManager. 
Note that the sampler will use all the headers from the HeaderManager.
If a SOAP action is specified, that will override any SOAPaction in the HeaderManager.
The primary difference between the soap sampler and
webservice sampler, is the soap sampler uses raw post and does not require conformance to
SOAP 1.1.
</p>


<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>For versions of JMeter later than 2.2, the sampler no longer uses chunked encoding by default.
<br>


For screen input, it now always uses the size of the data.
<br>


File input uses the file length as determined by Java.
<br>


On some OSes this may not work for all files, in which case add a child Header Manager
with Content-Length set to the actual length of the file.
<br>


Or set Content-Length to -1 to force chunked encoding.

</td></tr>
</tbody></table>
<p></p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/soap_sampler.png" height="276" width="426"></div>
<p>
<b>Parameters</b>
<a name="SOAP/XML-RPC_Request_parms">
</a><a class="sectionlink" href="#SOAP/XML-RPC_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler
         that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>URL</td>
<td>The URL to direct the SOAP request to.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Send SOAP action</td>
<td>Send a SOAP action header? (overrides the Header Manager)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use KeepAlive</td>
<td>If set, sends Connection: keep-alive, else sends Connection: close
</td>
<td>
No
</td>
</tr>
<tr>
<td>Soap/XML-RPC Data</td>
<td>The Soap XML message, or XML-RPC instructions.
        Not used if the filename is provided.
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Filename</td>
<td>If specified, then the contents of the file are sent, and the Data field is ignored
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="WebService(SOAP)_Request">18.1.6 WebService(SOAP) Request</a>
<a class="sectionlink" href="#WebService%28SOAP%29_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
This sampler has been tested with IIS Webservice running .NET 1.0 and .NET 1.1.
 It has been tested with SUN JWSDP, IBM webservices, Axis and gSoap toolkit for C/C++.
 The sampler uses Apache SOAP driver to serialize the message and set the header
 with the correct SOAPAction. Right now the sampler doesn't support automatic WSDL
 handling, since Apache SOAP currently does not provide support for it. Both IBM
 and SUN provide WSDL drivers. There are 3 options for the post data: text area,
 external file, or directory. If you want the sampler to randomly select a message,
 use the directory. Otherwise, use the text area or a file. The if either the
 file or path are set, it will not use the message in the text area. If you need
 to test a soap service that uses different encoding, use the file or path. If you
 paste the message in to text area, it will not retain the encoding and will result
 in errors. Save your message to a file with the proper encoding, and the sampler
 will read it as java.io.FileInputStream.
</p>

 
<p>
An important note on the sampler is it will automatically use the proxy host
 and port passed to JMeter from command line, if those fields in the sampler are
 left blank. If a sampler has values in the proxy host and port text field, it
 will use the ones provided by the user. This behavior may not be what users
 expect.
</p>

 
<p>
By default, the webservice sampler sets SOAPHTTPConnection.setMaintainSession
 (true). If you need to maintain the session, add a blank Header Manager. The
 sampler uses the Header Manager to store the SOAPHTTPConnection object, since
 the version of apache soap does not provide a easy way to get and set the cookies.
</p>

 
<p>
<b>
Note:
</b>
 If you are using CSVDataSet, do not check "Memory Cache". If memory
 cache is checked, it will not iterate to the next value. That means all the requests
 will use the first value.
</p>

 
<p>
Make sure you use &lt;soap:Envelope rather than &lt;Envelope. For example:
</p>

 
<pre>
&lt;?xml version="1.0" encoding="utf-8"?&gt;
&lt;soap:Envelope 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"&gt;
&lt;soap:Body&gt;
&lt;foo xmlns="http://clients-xlmns"/&gt;
&lt;/soap:Body&gt;
&lt;/soap:Envelope&gt;

</pre>


<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>The SOAP library that is used does not support SOAP 1.2, only SOAP 1.1. 
Also the library does not provide access to the HTTP response code (e.g. 200) or message (e.g. OK). 
To get round this, versions of JMeter after 2.3.2 check the returned message length.
If this is zero, then the request is marked as failed.

</td></tr>
</tbody></table>
<p></p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/webservice_sampler.png" height="648" width="943"></div>
<p>
<b>Parameters</b>
<a name="WebService(SOAP)_Request_parms">
</a><a class="sectionlink" href="#WebService%28SOAP%29_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler
         that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>WSDL URL</td>
<td>The WSDL URL with the service description.
        Versions of JMeter after 2.3.1 support the file: protocol for local WSDL files.
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Web Methods</td>
<td>Will be populated from the WSDL when the Load WSDL button is 
pressed.
        Select one of the methods and press the Configure button to 
populate the Protocol, Server, Port, Path and SOAPAction fields. 
        
</td>
<td>
No
</td>
</tr>
<tr>
<td>Protocol</td>
<td>HTTP or HTTPS are acceptable protocol.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Server Name or IP</td>
<td>The hostname or IP address.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Port Number</td>
<td>Port Number.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Timeout</td>
<td>Connection timeout.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Path</td>
<td>Path for the webservice.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>SOAPAction</td>
<td>The SOAPAction defined in the webservice description or WSDL.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Soap/XML-RPC Data</td>
<td>The Soap XML message
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Soap file</td>
<td>File containing soap message
</td>
<td>
No
</td>
</tr>
<tr>
<td>Message(s) Folder</td>
<td>Folder containing soap files. Files are choose randomly during test.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Memory cache</td>
<td>
        When using external files, setting this causes the file to be processed once and caches the result.
        This may use a lot of memory if there are many different large files.
        
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Read SOAP Response</td>
<td>Read the SOAP reponse (consumes performance). Permit to have assertions or post-processors
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use HTTP Proxy</td>
<td>Check box if http proxy should be used
</td>
<td>
No
</td>
</tr>
<tr>
<td>Server Name or IP</td>
<td>Proxy hostname
</td>
<td>
No
</td>
</tr>
<tr>
<td>Port Number</td>
<td>Proxy host port
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>
Webservice Soap Sampler assumes that empty response means failure.

</td></tr>
</tbody></table>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="LDAP_Request">18.1.7 LDAP Request</a>
<a class="sectionlink" href="#LDAP_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
This Sampler lets you send a different Ldap request(Add, Modify, Delete and Search) to an LDAP server.
    
<p>
If you are going to send multiple requests to the same LDAP server, consider
      using an 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#LDAP_Request_Defaults">LDAP Request Defaults</a>

      Configuration Element so you do not have to enter the same information for each
      LDAP Request.
</p>
 The same way the 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#Login_Config_Element">Login Config Element</a>
 also using for Login and password.
  
<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/ldap_request.png" height="462" width="621"></div>
<p>
There are two ways to create test cases for testing an LDAP Server.
</p>
<ol>
<li>
Inbuilt Test cases.
</li>

    
<li>
User defined Test cases.
</li>
</ol>
<p>
There are four test scenarios of testing LDAP. The tests are given below:
</p>
<ol>

      
<li>
Add Test
</li>

      
<ol>
<li>
Inbuilt test :
        
<p>
This will add a pre-defined entry in the LDAP Server and calculate
          the execution time. After execution of the test, the created entry will be
          deleted from the LDAP
          Server.
</p>
</li>

          
<li>
User defined test :
            
<p>
This will add the entry in the LDAP Server. User has to enter all the
              attributes in the table.The entries are collected from the table to add. The
              execution time is calculated. The created entry will not be deleted after the
              test.
</p>
</li>
</ol>


              
<li>
Modify Test
</li>

              
<ol>
<li>
Inbuilt test :
                
<p>
This will create a pre-defined entry first, then will modify the
                  created	entry in the LDAP Server.And calculate the execution time. After
                  execution
                  of the test, the created entry will be deleted from the LDAP Server.
</p>
</li>

                  
<li>
User defined test
                    
<p>
This will modify the entry in the LDAP Server. User has to enter all the
                      attributes in the table. The entries are collected from the table to modify.
                      The execution time is calculated. The entry will not be deleted from the LDAP
                      Server.
</p>
</li>
</ol>


                      
<li>
Search Test
</li>

                      
<ol>
<li>
Inbuilt test :
                        
<p>
This will create the entry first, then will search if the attributes
                          are available. It calculates the execution time of the search query. At the
                          end of  the execution,created entry will be deleted from the LDAP Server.
</p>
</li>

                          
<li>
User defined test
                            
<p>
This will search the user defined entry(Search filter) in the Search
                              base (again, defined by the user). The entries should be available in the LDAP
                              Server. The execution time is  calculated.
</p>
</li>
</ol>


                              
<li>
Delete Test
</li>

                              
<ol>
<li>
Inbuilt test :
                                
<p>
This will create a pre-defined entry first, then it will be deleted
                                  from the LDAP Server. The execution time is calculated.
</p>
</li>


                                  
<li>
User defined test
                                    
<p>
This will delete the user-defined entry in the LDAP Server. The entries
                                      should be available in the LDAP Server. The execution time is calculated.
</p>
</li>
</ol>
</ol>
<p>
<b>Parameters</b>
<a name="LDAP_Request_parms">
</a><a class="sectionlink" href="#LDAP_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Server Name or IP</td>
<td>Domain name or IP address of the LDAP server.
                                          JMeter assumes the LDAP server is listening on the default port(389).
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Port</td>
<td>default port(389).
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>root DN</td>
<td>DN for the server to communicate
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Username</td>
<td>LDAP server username.
</td>
<td>
Usually
</td>
</tr>
<tr>
<td>Password</td>
<td>LDAP server password. (N.B. this is stored unencrypted in the test plan)
</td>
<td>
Usually
</td>
</tr>
<tr>
<td>Entry DN</td>
<td>the name of the context to create or Modify; may not be empty Example: do you want to add cn=apache,ou=test
                                            you have to add in table name=cn, value=apache
                                          
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Delete</td>
<td>the name of the context to Delete; may not be empty
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Search base</td>
<td>the name of the context or object to search
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Search filter</td>
<td> the filter expression to use for the search; may not be null
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>add test</td>
<td> this name, value pair to added in the given context object
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>modify test</td>
<td> this name, value pair to add or modify in the given context object
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>
<p><b>See Also:</b>
</p><ul>
<li><a href="http://jmeter.apache.org/usermanual/build-ldap-test-plan.html">Building an Ldap Test Plan</a></li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#LDAP_Request_Defaults">LDAP Request Defaults</a>
</li>
</ul>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="LDAP_Extended_Request">18.1.8 LDAP Extended Request</a>
<a class="sectionlink" href="#LDAP_Extended_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
This Sampler can send all 8 different LDAP request to an LDAP server. It is an extended version of the LDAP sampler,
  therefore it is harder to configure, but can be made much closer resembling a real LDAP session.
    
<p>
If you are going to send multiple requests to the same LDAP server, consider
      using an 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#LDAP_Extended_Request_Defaults">LDAP Extended Request Defaults</a>

      Configuration Element so you do not have to enter the same information for each
      LDAP Request.
</p>
 
<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/ldapext_request.png" height="371" width="619"></div>
<p>
There are nine test operations defined. These operations are given below:
</p>
<ol>

      
<li>
<b>
Thread bind
</b>
</li>

      
<p>
Any LDAP request is part of an LDAP session, so the first thing that should be done is starting a session to the LDAP server.
       For starting this session a thread bind is used, which is equal to the LDAP "bind" operation.
       The user is requested to give a username (Distinguished name) and password, 
       which will be used to initiate a session.
       When no password, or the wrong password is specified, an anonymous session is started. Take care,
       omitting the password will not fail this test, a wrong password will. 
       (N.B. this is stored unencrypted in the test plan)
</p>

     
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Servername</td>
<td>The name (or IP-address) of the LDAP server.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Port</td>
<td>The port number that the LDAP server is listening to. If this is omitted 
     JMeter assumes the LDAP server is listening on the default port(389).
</td>
<td>
No
</td>
</tr>
<tr>
<td>DN</td>
<td>The distinguished name of the base object that will be used for any subsequent operation. 
     It can be used as a starting point for all operations. You cannot start any operation on a higher level than this DN!
</td>
<td>
No
</td>
</tr>
<tr>
<td>Username</td>
<td>Full distinguished name of the user as which you want to bind.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Password</td>
<td>Password for the above user. If omitted it will result in an 
anonymous bind. 
     If is is incorrect, the sampler will return an error and revert to 
an anonymous bind. (N.B. this is stored unencrypted in the test plan)
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>

 
<br>

       
      
<li>
<b>
Thread unbind
</b>
</li>

      
<p>
This is simply the operation to end a session. 
      It is equal to the LDAP "unbind" operation.
</p>

     
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>

     
 
<br>

       
      
<li>
<b>
Single bind/unbind
</b>
</li>

		
<p>
 This is a combination of the LDAP "bind" and "unbind" operations.
		It can be used for an authentication request/password check for any user. It will open an new session, just to
		check the validity of the user/password combination, and end the session again.
</p>

    
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Username</td>
<td>Full distinguished name of the user as which you want to bind.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Password</td>
<td>Password for the above user. If omitted it will result in an anonymous bind. 
     If is is incorrect, the sampler will return an error. (N.B. this is stored unencrypted in the test plan)
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>

		
 
<br>

       
      
<li>
<b>
Rename entry
</b>
</li>

       
<p>
This is the LDAP "moddn" operation. It can be used to rename an entry, but 
       also for moving an entry or a complete subtree to a different place in 
       the LDAP tree.  
</p>

    
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Old entry name</td>
<td>The current distinguished name of the object you want to rename or move, 
      relative to the given DN in the thread bind operation.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>New distinguished name</td>
<td>The new distinguished name of the object you want to rename or move, 
      relative to the given DN in the thread bind operation.
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>

       
 
<br>

       
        
<li>
<b>
Add test
</b>
</li>

       
<p>
This is the ldap "add" operation. It can be used to add any kind of 
       object to the LDAP server.  
</p>

    
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Entry DN</td>
<td>Distinguished name of the object you want to add, relative to the given DN in the thread bind operation.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Add test</td>
<td>A list of attributes and their values you want to use for the object.
     If you need to add a multiple value attribute, you need to add the same attribute with their respective 
     values several times to the list.
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>

       
 
<br>

       
      
<li>
<b>
Delete test
</b>
</li>

       
<p>
 This is the LDAP "delete" operation, it can be used to delete an 
       object from the LDAP tree 
</p>

    
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Delete</td>
<td>Distinguished name of the object you want to delete, relative to the given DN in the thread bind operation.
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>

       
 
<br>

       
      
<li>
<b>
Search test
</b>
</li>

       
<p>
This is the LDAP "search" operation, and will be used for defining searches.  
</p>

    
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Search base</td>
<td>Distinguished name of the subtree you want your 
      search to look in, relative to the given DN in the thread bind operation.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Search Filter</td>
<td>searchfilter, must be specified in LDAP syntax.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Scope</td>
<td>Use 0 for baseobject-, 1 for onelevel- and 2 for a subtree search. (Default=0)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Size Limit</td>
<td>Specify the maximum number of results you want back from the server.
 (default=0, which means no limit.) When the sampler hits the maximum 
number of results, it will fail with errorcode 4
</td>
<td>
No
</td>
</tr>
<tr>
<td>Time Limit</td>
<td>Specify the maximum amount of (cpu)time (in miliseconds) that the 
server can spend on your search. Take care, this does not say anything 
about the responsetime. (default is 0, which means no limit)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Attributes</td>
<td>Specify the attributes you want to have returned, seperated by a semicolon. An empty field will return all attributes
</td>
<td>
No
</td>
</tr>
<tr>
<td>Return object</td>
<td>Whether the object will be returned (true) or not (false). Default=false
</td>
<td>
No
</td>
</tr>
<tr>
<td>Dereference aliases</td>
<td>If true, it will dereference aliases, if false, it will not follow them (default=false)
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>


 
<br>

       
      
<li>
<b>
Modification test
</b>
</li>

       
<p>
This is the LDAP "modify" operation. It can be used to modify an object. It
       can be used to add, delete or replace values of an attribute. 
</p>

    
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Entry name</td>
<td>Distinguished name of the object you want to modify, relative 
      to the given DN in the thread bind operation
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Modification test</td>
<td>The attribute-value-opCode triples. The opCode can be any 
     valid LDAP operationCode (add, delete/remove or replace). If you don't specify a value with a delete operation,
     all values of the given attribute will be deleted. If you do specify a value in a delete operation, only 
     the given value will be deleted. If this value is non-existent, the sampler will fail the test.
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>

       
 
<br>

       
      
<li>
<b>
Compare
</b>
</li>

       
<p>
This is the LDAP "compare" operation. It can be used to compare the value 
       of a given attribute with some already known value. In reality this is mostly 
       used to check whether a given person is a member of some group. In such a case
        you can compare the DN of the user as a given value, with the values in the
         attribute "member" of an object of the type groupOfNames.
         If the compare operation fails, this test fails with errorcode 49.
</p>

    
<p>
<b>Parameters</b>
</p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Entry DN</td>
<td>The current distinguished name of the object of 
      which you want  to compare an attribute, relative to the given DN in the thread bind operation.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Compare filter</td>
<td>In the form "attribute=value"
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>

    
</ol>
<p><b>See Also:</b>
</p><ul>
<li><a href="http://jmeter.apache.org/usermanual/build-ldapext-test-plan.html">Building an LDAP Test Plan</a></li>
<li><a href="http://jmeter.apache.org/usermanual/component_reference.html#LDAP_Extended_Request_Defaults">LDAP Extended Request Defaults</a>
</li>
</ul>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="Access_Log_Sampler">18.1.9 Access Log Sampler</a>
<a class="sectionlink" href="#Access_Log_Sampler" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<center>
<h2>
(Alpha Code)
</h2>
</center>
<p>
AccessLogSampler was designed to read access logs and generate http requests.
For those not familiar with the access log, it is the log the webserver maintains of every
request it accepted. This means every image, css file, javascript file, html file.... 
The current implementation is complete, but some features have not been enabled. 
There is a filter for the access log parser, but I haven't figured out how to link to the pre-processor. 
Once I do, changes to the sampler will be made to enable that functionality.
</p>


<p>
Tomcat uses the common format for access logs. This means any webserver that uses the
common log format can use the AccessLogSampler. Server that use common log format include:
Tomcat, Resin, Weblogic, and SunOne. Common log format looks
like this:
</p>


<p>
127.0.0.1 - - [21/Oct/2003:05:37:21 -0500] "GET /index.jsp?%2Findex.jsp= HTTP/1.1" 200 8343
</p>


<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>The current implementation of the parser only looks at 
the text within the quotes that contains one of the HTTP protocol 
methods (GET, PUT, POST, DELETE...).
Everything else is stripped out and ignored. For example, the response 
code is completely
ignored by the parser. 
</td></tr>
</tbody></table>
<p></p>


<p>
For the future, it might be nice to filter out entries that
do not have a response code of 200. Extending the sampler should be fairly simple. There
are two interfaces you have to implement:
</p>


<ul>


<li>
org.apache.jmeter.protocol.http.util.accesslog.LogParser
</li>


<li>
org.apache.jmeter.protocol.http.util.accesslog.Generator
</li>


</ul>


<p>
The current implementation of AccessLogSampler uses the generator to create a new
HTTPSampler. The servername, port and get images are set by AccessLogSampler. Next,
the parser is called with integer 1, telling it to parse one entry. After that,
HTTPSampler.sample() is called to make the request.

<code>


</code></p><pre><code>
            samp = (HTTPSampler) GENERATOR.generateRequest();
            samp.setDomain(this.getDomain());
            samp.setPort(this.getPort());
            samp.setImageParser(this.isImageParser());
            PARSER.parse(1);
            res = samp.sample();
            res.setSampleLabel(samp.toString());

</code></pre><code>


</code>

The required methods in LogParser are:

<ul>


<li>
setGenerator(Generator)
</li>


<li>
parse(int)
</li>
 

</ul>

Classes implementing Generator interface should provide concrete implementation
for all the methods. For an example of how to implement either interface, refer to
StandardGenerator and TCLogParser.

<p></p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/accesslogsampler.png" height="318" width="613"></div>
<p>
<b>Parameters</b>
<a name="Access_Log_Sampler_parms">
</a><a class="sectionlink" href="#Access_Log_Sampler_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Server</td>
<td>Domain name or IP address of the web server.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Port</td>
<td>Port the web server is listening to.
</td>
<td>
No (defaults to 80)
</td>
</tr>
<tr>
<td>Log parser class</td>
<td>The log parser class is responsible for parsing the logs.
</td>
<td>
Yes (default provided)
</td>
</tr>
<tr>
<td>Filter</td>
<td>The filter class is used to filter out certain lines.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Location of log file</td>
<td>The location of the access log file.
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>
<p>

The TCLogParser processes the access log independently for each thread.
The SharedTCLogParser and OrderPreservingLogParser share access to the file, 
i.e. each thread gets the next entry in the log.

</p>
<p>

The SessionFilter is intended to handle Cookies across threads. 
It does not filter out any entries, but modifies the cookie manager so that the cookies for a given IP are
processed by a single thread at a time. If two threads try to process samples from the same client IP address,
then one will be forced to wait until the other has completed.

</p>
<p>

The LogFilter is intended to allow access log entries to be filtered by filename and regex,
as well as allowing for the replacement of file extensions. However, it is not currently possible
to configure this via the GUI, so it cannot really be used.

</p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="BeanShell_Sampler">18.1.10 BeanShell Sampler</a>
<a class="sectionlink" href="#BeanShell_Sampler" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
This sampler allows you to write a sampler using the BeanShell scripting language.		

</p>
<p>


<b>
For full details on using BeanShell, please see the 
<a href="http://www.beanshell.org/">
BeanShell website.
</a>
</b>


</p>


<p>

The test element supports the ThreadListener and TestListener interface methods.
These must be defined in the initialisation file.
See the file BeanShellListeners.bshrc for example definitions.

</p>


<p>

From JMeter version 2.5.1, the BeanShell sampler also supports the Interruptible interface.
The interrupt() method can be defined in the script or the init file.

</p>

	
<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/beanshellsampler.png" height="505" width="1034"></div>
<p>
<b>Parameters</b>
<a name="BeanShell_Sampler_parms">
</a><a class="sectionlink" href="#BeanShell_Sampler_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
    The name is stored in the script variable Label
</td>
<td>
No
</td>
</tr>
<tr>
<td>Reset bsh.Interpreter before each call</td>
<td>
    If this option is selected, then the interpreter will be recreated for each sample.
    This may be necessary for some long running scripts. 
    For further information, see 
<a href="http://jmeter.apache.org/usermanual/best-practices#bsh_scripting">
Best Practices - BeanShell scripting
</a>
.
    
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Parameters</td>
<td>Parameters to pass to the BeanShell script.
    This is intended for use with script files; for scripts defined in the GUI, you can use whatever
    variable and function references you need within the script itself.
	The parameters are stored in the following variables:
	
<ul>

		
<li>
Parameters - string containing the parameters as a single variable
</li>

	    
<li>
bsh.args - String array containing parameters, split on white-space
</li>

	
</ul>
</td>
<td>
No
</td>
</tr>
<tr>
<td>Script file</td>
<td>A file containing the BeanShell script to run.
    The file name is stored in the script variable FileName
</td>
<td>
No
</td>
</tr>
<tr>
<td>Script</td>
<td>The BeanShell script to run. 
    The return value (if not null) is stored as the sampler result.
</td>
<td>
Yes (unless script file is provided)
</td>
</tr>
</tbody></table>
<p></p>
<p>

N.B. Each Sampler instance has its own BeanShell interpeter,
and Samplers are only called from a single thread

</p>
<p>

If the property "beanshell.sampler.init" is defined, it is passed to the Interpreter
as the name of a sourced file.
This can be used to define common methods and variables. 
There is a sample init file in the bin directory: BeanShellSampler.bshrc.

</p>
<p>

If a script file is supplied, that will be used, otherwise the script will be used.
</p>
<p>
Before invoking the script, some variables are set up in the BeanShell interpreter:
			
</p>
<p>
The contents of the Parameters field is put into the variable "Parameters".
			The string is also split into separate tokens using a single space as the separator, and the resulting list
			is stored in the String array bsh.args.
</p>
<p>
The full list of BeanShell variables that is set up is as follows:
</p>
<ul>

		
<li>
log - the Logger
</li>

		
<li>
Label - the Sampler label
</li>

		
<li>
FileName - the file name, if any
</li>

		
<li>
Parameters - text from the Parameters field
</li>

		
<li>
bsh.args - the parameters, split as described above
</li>

		
<li>
SampleResult - pointer to the current SampleResult
</li>

			
<li>
ResponseCode = 200
</li>

			
<li>
ResponseMessage = "OK"
</li>

			
<li>
IsSuccess = true
</li>

			
<li>
ctx - JMeterContext
</li>

			
<li>
vars - 
<a href="http://jmeter.apache.org/docs/api/org/apache/jmeter/threads/JMeterVariables.html">
JMeterVariables
</a>
  - e.g. vars.get("VAR1"); vars.put("VAR2","value"); vars.remove("VAR3"); vars.putObject("OBJ1",new Object());
</li>

            
<li>
props - JMeterProperties (class java.util.Properties)- e.g. props.get("START.HMS"); props.put("PROP1","1234");
</li>

		
</ul>
<p>
When the script completes, control is returned to the Sampler, and it copies the contents
			of the following script variables into the corresponding variables in the SampleResult:
</p>
<ul>

			
<li>
ResponseCode - for example 200
</li>

			
<li>
ResponseMessage - for example "OK"
</li>

			
<li>
IsSuccess - true/false
</li>

			
</ul>
<p>
The SampleResult ResponseData is set from the return value of the script.
			Since version 2.1.2, if the script returns null, it can set the response directly, by using the method 
			SampleResult.setResponseData(data), where data is either a String or a byte array.
			The data type defaults to "text", but can be set to binary by using the method
			SampleResult.setDataType(SampleResult.BINARY).
			
</p>
<p>
The SampleResult variable gives the script full access to all the fields and
				methods in the SampleResult. For example, the script has access to the methods
				setStopThread(boolean) and setStopTest(boolean).
				
				Here is a simple (not very useful!) example script:
</p>
<pre>
if (bsh.args[0].equalsIgnoreCase("StopThread")) {
    log.info("Stop Thread detected!");
    SampleResult.setStopThread(true);
}
return "Data from sample with Label "+Label;
//or, since version 2.1.2
SampleResult.setResponseData("My data");
return null;

</pre>
<p>
Another example:
<br>

 ensure that the property 
<b>
beanshell.sampler.init=BeanShellSampler.bshrc
</b>
 is defined in jmeter.properties. 
The following script will show the values of all the variables in the ResponseData field:

</p>
<pre>
return getVariables();

</pre>
<p>

For details on the methods available for the various classes 
(JMeterVariables, SampleResult etc) please check the Javadoc or the 
source code.
Beware however that misuse of any methods can cause subtle faults that 
may be difficult to find ...

</p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="BSF_Sampler">18.1.11 BSF Sampler</a>
<a class="sectionlink" href="#BSF_Sampler" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
This sampler allows you to write a sampler using a BSF scripting language.
<br>


		See the 
<a href="http://commons.apache.org/bsf/index.html">
Apache Bean Scripting Framework
</a>

		website for details of the languages supported.
		You may need to download the appropriate jars for the language; they should be put in the JMeter 
<b>
lib
</b>
 directory.
		
</p>

		
<p>
By default, JMeter supports the following languages:
</p>

		
<ul>

		
<li>
javascript
</li>

        
<li>
jexl (JMeter version 2.3.2 and later)
</li>

        
<li>
xslt
</li>

		
</ul>

        
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>Unlike the BeanShell sampler, the interpreter is not saved between invocations.
</td></tr>
</tbody></table>
<p></p>

	
<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/bsfsampler.png" height="412" width="865"></div>
<p>
<b>Parameters</b>
<a name="BSF_Sampler_parms">
</a><a class="sectionlink" href="#BSF_Sampler_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this sampler that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Scripting Language</td>
<td>Name of the BSF scripting language to be used.
	N.B. Not all the languages in the drop-down list are supported by default.
	The following are supported: jexl, javascript, xslt.
	Others may be available if the appropriate jar is installed in the JMeter lib directory.
	
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Script File</td>
<td>Name of a file to be used as a BSF script
</td>
<td>
No
</td>
</tr>
<tr>
<td>Parameters</td>
<td>List of parameters to be passed to the script file or the script.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Script</td>
<td>Script to be passed to BSF language
</td>
<td>
Yes (unless script file is provided)
</td>
</tr>
</tbody></table>
<p></p>
<p>

If a script file is supplied, that will be used, otherwise the script will be used.
</p>
<p>

Before invoking the script, some variables are set up.
Note that these are BSF variables - i.e. they can be used directly in the script.

</p>
<ul>


<li>
log - the Logger
</li>


<li>
Label - the Sampler label
</li>


<li>
FileName - the file name, if any
</li>


<li>
Parameters - text from the Parameters field
</li>


<li>
args - the parameters, split as described above
</li>


<li>
SampleResult - pointer to the current SampleResult
</li>


<li>
sampler - pointer to current Sampler
</li>


<li>
ctx - JMeterContext
</li>


<li>
vars - 
<a href="http://jmeter.apache.org/docs/api/org/apache/jmeter/threads/JMeterVariables.html">
JMeterVariables
</a>
  - e.g. vars.get("VAR1"); vars.put("VAR2","value"); vars.remove("VAR3"); vars.putObject("OBJ1",new Object());
</li>


<li>
props - JMeterProperties  (class java.util.Properties) - e.g. props.get("START.HMS"); props.put("PROP1","1234");
</li>


<li>
OUT - System.out - e.g. OUT.println("message")
</li>


</ul>
<p>

The SampleResult ResponseData is set from the return value of the script.
If the script returns null, it can set the response directly, by using the method 
SampleResult.setResponseData(data), where data is either a String or a byte array.
The data type defaults to "text", but can be set to binary by using the method
SampleResult.setDataType(SampleResult.BINARY).

</p>
<p>

The SampleResult variable gives the script full access to all the fields and
methods in the SampleResult. For example, the script has access to the methods
setStopThread(boolean) and setStopTest(boolean).

</p>
<p>

Unlike the Beanshell Sampler, the BSF Sampler does not set the 
ResponseCode, ResponseMessage and sample status via script variables.
Currently the only way to changes these is via the SampleResult methods:

</p><ul>


<li>
SampleResult.setSuccessful(true/false)
</li>


<li>
SampleResult.setResponseCode("code")
</li>


<li>
SampleResult.setResponseMessage("message")
</li>


</ul>


<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="JSR223_Sampler">18.1.11.1 JSR223 Sampler</a>
<a class="sectionlink" href="#JSR223_Sampler" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>


<p>

The JSR223 Sampler allows JSR223 script code to be used to perform a sample.
JSR223 related elements have a feature that increases highly their performances.
To benefit from this feature use Script files instead of inlining them. This will make JMeter compile them if this
feature is available on ScriptEngine and cache them.
Cache size is controlled by the following jmeter property (jmeter.properties):

</p><ul>


<li>
jsr223.compiled_scripts_cache_size=100
</li>


</ul>

For details, see 
<a href="http://jmeter.apache.org/usermanual/component_reference.html#BSF_Sampler">BSF Sampler</a>
.

<p></p>


<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>Unlike the BeanShell sampler, the interpreter is not saved between invocations.
</td></tr>
</tbody></table>
<p></p>


<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>
Since JMeter 2.8, JSR223 Test Elements using Script file are now 
Compiled if ScriptEngine supports this feature, this enables great 
performance enhancements.

</td></tr>
</tbody></table>
<p></p>


</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="TCP_Sampler">18.1.12 TCP Sampler</a>
<a class="sectionlink" href="#TCP_Sampler" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>

		
<p>

		The TCP Sampler opens a TCP/IP connection to the specified server.
		It then sends the text, and waits for a response.
		
<br>


		If "Re-use connection" is selected, connections are shared between Samplers in the same thread,
		provided that the exact same host name string and port are used. 
		Different hosts/port combinations will use different connections, as will different threads. 
		
<br>


		If an error is detected - or "Re-use connection" is not selected - the socket is closed. 
		Another socket will be reopened on the next sample.
		
<br>


		The following properties can be used to control its operation:
		
</p>

		
<ul>

			
<li>
tcp.status.prefix - text that precedes a status number
</li>

			
<li>
tcp.status.suffix - text that follows a status number
</li>

			
<li>
tcp.status.properties - name of property file to convert status codes to messages
</li>

			
<li>
tcp.handler - Name of TCP Handler class (default TCPClientImpl) - only used if not specified on the GUI
</li>

		
</ul>

		The class that handles the connection is defined by the GUI, failing that the property tcp.handler. 
		If not found, the class is then searched for in the package org.apache.jmeter.protocol.tcp.sampler.
		
<p>

		Users can provide their own implementation.
		The class must extend org.apache.jmeter.protocol.tcp.sampler.TCPClient.
		
</p>

		
<p>

		The following implementations are currently provided.
		
</p><ul>

		
<li>
TCPClientImpl
</li>

        
<li>
BinaryTCPClientImpl
</li>

        
<li>
LengthPrefixedBinaryTCPClientImpl
</li>

		
</ul>

		The implementations behave as follows:
		
<p></p>

		
<p>
<b>
TCPClientImpl
</b>
<br>


		This implementation is fairly basic.
        When reading the response, it reads until the end of line byte, if this is defined
        by setting the property 
<b>
tcp.eolByte
</b>
, otherwise until the end of the input stream.
        You can control charset encoding by setting 
<b>
tcp.charset
</b>
, which will default to Platform default encoding.
        
</p>

        
<p>
<b>
BinaryTCPClientImpl
</b>
<br>


        This implementation converts the GUI input, which must be a hex-encoded string, into binary,
        and performs the reverse when reading the response.
        When reading the response, it reads until the end of message byte, if this is defined
        by setting the property 
<b>
tcp.BinaryTCPClient.eomByte
</b>
, otherwise until the end of the input stream.
        
</p>

        
<p>
<b>
LengthPrefixedBinaryTCPClientImpl
</b>
<br>


        This implementation extends BinaryTCPClientImpl by prefixing the binary message data with a binary length byte.
        The length prefix defaults to 2 bytes.
        This can be changed by setting the property 
<b>
tcp.binarylength.prefix.length
</b>
.
        
</p>

        
<p>
<b>
Timeout handling
</b>

        If the timeout is set, the read will be terminated when this expires. 
        So if you are using an eolByte/eomByte, make sure the timeout is sufficiently long,
        otherwise the read will be terminated early.    
		
</p>

		
<p>
<b>
Response handling
</b>

		
<br>


		If tcp.status.prefix is defined, then the response message is searched for the text following
		that up to the suffix. If any such text is found, it is used to set the response code.
		The response message is then fetched from the properties file (if provided).
		
<br>


		For example, if the prefix = "[" and the suffix = "]", then the following repsonse:
		
<br>


		[J28] XI123,23,GBP,CR
		
<br>


		would have the response code J28.
		
<br>


		Response codes in the range "400"-"499" and "500"-"599" are currently regarded as failures;
		all others are successful. [This needs to be made configurable!]
		
</p>


<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>The login name/password are not used by the supplied TCP implementations.
</td></tr>
</tbody></table>
<p></p>

		
<br>


		Sockets are disconnected at the end of a test run.

<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/tcpsampler.png" height="357" width="743"></div>
<p>
<b>Parameters</b>
<a name="TCP_Sampler_parms">
</a><a class="sectionlink" href="#TCP_Sampler_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this element that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>TCPClient classname</td>
<td>Name of the TCPClient class. Defaults to the property tcp.handler, failing that TCPClientImpl.
</td>
<td>
No
</td>
</tr>
<tr>
<td>ServerName or IP</td>
<td>Name or IP of TCP server
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Port Number</td>
<td>Port to be used
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Re-use connection</td>
<td>If selected, the connection is kept open. Otherwise it is closed when the data has been read.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Connect Timeout</td>
<td>Connect Timeout (milliseconds, 0 disables).
</td>
<td>
No
</td>
</tr>
<tr>
<td>Response Timeout</td>
<td>Response Timeout (milliseconds, 0 disables).
</td>
<td>
No
</td>
</tr>
<tr>
<td>Set Nodelay</td>
<td>See java.net.Socket.setTcpNoDelay().
  If selected, this will disable Nagle's algorithm, otherwise Nagle's algorithm will be used.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Text to Send</td>
<td>Text to be sent
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Login User</td>
<td>User Name - not used by default implementation
</td>
<td>
No
</td>
</tr>
<tr>
<td>Password</td>
<td>Password - not used by default implementation (N.B. this is stored unencrypted in the test plan)
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="JMS_Publisher">18.1.13 JMS Publisher</a>
<a class="sectionlink" href="#JMS_Publisher" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>BETA CODE - the code is still subject to change
</td></tr>
</tbody></table>
<p></p>

		
<p>

		JMS Publisher will publish messages to a given destination (topic/queue). For those not
		familiar with JMS, it is the J2EE specification for messaging. There are
		numerous JMS servers on the market and several open source options.
		
</p>

		
<br>



<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>JMeter does not include any JMS implementation jar; this must be downloaded from the JMS provider and put in the lib directory
</td></tr>
</tbody></table>
<p></p>

	
<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/jmspublisher.png" height="735" width="802"></div>
<p>
<b>Parameters</b>
<a name="JMS_Publisher_parms">
</a><a class="sectionlink" href="#JMS_Publisher_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this element that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>use JNDI properties file</td>
<td>use jndi.properties. 
  Note that the file must be on the classpath - e.g. by updating the user.classpath JMeter property.
  If this option is not selected, JMeter uses the "JNDI Initial Context Factory" and "Provider URL" fields
  to create the connection.
  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>JNDI Initial Context Factory</td>
<td>Name of the context factory
</td>
<td>
No
</td>
</tr>
<tr>
<td>Provider URL</td>
<td>The URL for the jms provider
</td>
<td>
Yes, unless using jndi.properties
</td>
</tr>
<tr>
<td>Destination</td>
<td>The message destination (topic or queue name)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Setup</td>
<td>The destination setup type. With At startup, the destination name is
 static (i.e. always same name during the test), with Each sample, the 
destination name is dynamic and is evaluate at each sample (i.e. the 
destination name may be a variable)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Authentication</td>
<td>Authentication requirement for the JMS provider
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>User</td>
<td>User Name
</td>
<td>
No
</td>
</tr>
<tr>
<td>Password</td>
<td>Password (N.B. this is stored unencrypted in the test plan)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Number of samples to aggregate</td>
<td>Number of samples to aggregate
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Message source</td>
<td>Where to obtain the message
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Message type</td>
<td>Text, Map or Object message
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Use non-persistent delivery mode?</td>
<td>
      Whether to set DeliveryMode.NON_PERSISTENT (defaults to false)
  
</td>
<td>
No
</td>
</tr>
<tr>
<td>JMS Properties</td>
<td>
      The JMS Properties are properties specific for the underlying messaging system.
      For example: for WebSphere 5.1 web services you will need to set the JMS Property targetService to test
      webservices through JMS.
  
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
<p>

For the MapMessage type, JMeter reads the source as lines of text.
Each line must have 3 fields, delimited by commas.
The fields are:

</p><ul>


<li>
Name of entry
</li>


<li>
Object class name, e.g. "String" (assumes java.lang package if not specified)
</li>


<li>
Object string value
</li>


</ul>

For each entry, JMeter adds an Object with the given name.
The value is derived by creating an instance of the class, and using the
 valueOf(String) method to convert the value if necessary.
For example:

<pre>
name,String,Example
size,Integer,1234

</pre>

This is a very simple implementation; it is not intended to support all possible object types.

<p></p>
<p>


</p><p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td> 
The Object message is implemented since 2.7 and works as follow:

<ul>


<li>
Put the JAR that contain you object and its dependencies in jmeter_home/lib/ folder 
</li>


<li>
Serialize your object as XML using XStream
</li>


<li>
Either put result in a file suffixed with .txt or .obj or put XML content direclty in Text Area
</li>
  

</ul>

Note that if message is in an file, replacement of properties will not occur while it will happen if you use Text Area.

</td></tr>
</tbody></table>
<p></p>



<p></p>
<p>

The following table shows some values which may be useful when configuring JMS:

</p><table>
<tbody><tr>
<td align="left" bgcolor="#039acc" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Apache <a href="http://activemq.apache.org/">ActiveMQ</a>
</font>
</td>
<td align="left" bgcolor="#039acc" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Value(s)
</font>
</td>
<td align="left" bgcolor="#039acc" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Comment
</font>
</td>
</tr>
<tr>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Context Factory
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
org.apache.activemq.jndi.ActiveMQInitialContextFactory
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
.
</font>
</td>
</tr>
<tr>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Provider URL
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
vm://localhost
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
&nbsp;
</font>
</td>
</tr>
<tr>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Provider URL
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
vm:(broker:(vm://localhost)?persistent=false)
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Disable persistence
</font>
</td>
</tr>
<tr>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Queue Reference
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
dynamicQueues/QUEUENAME
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
<a href="http://activemq.apache.org/jndi-support.html#JNDISupport-Dynamicallycreatingdestinations">Dynamically define</a> the QUEUENAME to JNDI
</font>
</td>
</tr>
<tr>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
Topic Reference
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
dynamicTopics/TOPICNAME
</font>
</td>
<td align="left" bgcolor="#a0ddf0" valign="top">
<font color="#000000" face="arial,helvetica,sanserif" size="-1">
<a href="http://activemq.apache.org/jndi-support.html#JNDISupport-Dynamicallycreatingdestinations">Dynamically define</a> the TOPICNAME to JNDI
</font>
</td>
</tr>
</tbody></table>


<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="JMS_Subscriber">18.1.14 JMS Subscriber</a>
<a class="sectionlink" href="#JMS_Subscriber" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>BETA CODE - the code is still subject to change
</td></tr>
</tbody></table>
<p></p>

		
<p>

		JMS Publisher will subscribe to messages in a given destination (topic or queue). For those not
		familiar with JMS, it is the J2EE specification for messaging. There are
		numerous JMS servers on the market and several open source options.
		
</p>

		
<br>



<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>JMeter does not include any JMS implementation jar; this must be downloaded from the JMS provider and put in the lib directory
</td></tr>
</tbody></table>
<p></p>

	
<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/jmssubscriber.png" height="498" width="709"></div>
<p>
<b>Parameters</b>
<a name="JMS_Subscriber_parms">
</a><a class="sectionlink" href="#JMS_Subscriber_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this element that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>use JNDI properties file</td>
<td>use jndi.properties. 
  Note that the file must be on the classpath - e.g. by updating the user.classpath JMeter property.
  If this option is not selected, JMeter uses the "JNDI Initial Context Factory" and "Provider URL" fields
  to create the connection.
  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>JNDI Initial Context Factory</td>
<td>Name of the context factory
</td>
<td>
No
</td>
</tr>
<tr>
<td>Provider URL</td>
<td>The URL for the jms provider
</td>
<td>
No
</td>
</tr>
<tr>
<td>Destination</td>
<td>the message destination (topic or queue name)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Durable Subscription ID</td>
<td>The ID to use for a durable subscription. On first 
  use the respective queue will automatically be generated by the JMS provider if it does not exist yet.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Client ID</td>
<td>The Client ID to use when you you use a durable subscription. 
  Be sure to add a variable like ${__threadNum} when you have more than one Thread.
</td>
<td>
No
</td>
</tr>
<tr>
<td>JMS Selector</td>
<td>Message Selector as defined by JMS specification to extract only 
  messages that respect the Selector condition. Syntax uses subpart of SQL 92.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Setup</td>
<td>The destination setup type. With At startup, the destination name is
 static (i.e. always same name during the test), with Each sample, the 
destination name is dynamic and is evaluate at each sample (i.e. the 
destination name may be a variable)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Authentication</td>
<td>Authentication requirement for the JMS provider
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>User</td>
<td>User Name
</td>
<td>
No
</td>
</tr>
<tr>
<td>Password</td>
<td>Password (N.B. this is stored unencrypted in the test plan)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Number of samples to aggregate</td>
<td>number of samples to aggregate
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Read response</td>
<td>should the sampler read the response. If not, only the response length is returned.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Timeout</td>
<td>Specify the timeout to be applied, in milliseconds. 0=none. 
  This is the overall aggregate timeout, not per sample.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Client</td>
<td>Which client implementation to use.
  Both of them create connections which can read messages. However they use a different strategy, as described below:
  
<ul>

  
<li>
MessageConsumer.receive() - calls receive() for every requested message. 
  Retains the connection between samples, but does not fetch messages unless the sampler is active.
  This is best suited to Queue subscriptions. 
  
</li>

  
<li>
MessageListener.onMessage() - establishes a Listener that stores all incoming messages on a queue. 
  The listener remains active after the sampler completes.
  This is best suited to Topic subscriptions.
</li>

  
</ul>

  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Stop between samples?</td>
<td>
  If selected, then JMeter calls Connection.stop() at the end of each 
sample (and calls start() before each sample).
  This may be useful in some cases where multiple samples/threads have 
connections to the same queue.
  If not selected, JMeter calls Connection.start() at the start of the 
thread, and does not call stop() until the end of the thread.
  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Separator</td>
<td>
  Separator used to separate messages when there is more than one (related to setting Number of samples to aggregate).
  Note that \n, \r, \t are accepted.
  
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
<p>


<b>
NOTE:
</b>
 JMeter 2.3.4 and earlier used a different strategy for the MessageConsumer.receive() client. 
Previously this started a background thread which polled for messages. This thread continued when the sampler
completed, so the net effect was similar to the MessageListener.onMessage() strategy.

</p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="JMS_Point-to-Point">18.1.15 JMS Point-to-Point</a>
<a class="sectionlink" href="#JMS_Point-to-Point" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>BETA CODE - the code is still subject to change
</td></tr>
</tbody></table>
<p></p>

		
<p>

		This sampler sends and optionally receives JMS Messages through point-to-point connections (queues).
        It is different from pub/sub messages and is generally used for handling transactions.
		
</p>

		
<p>

		
<b>
Request Only
</b>
 will typically used to put load on a JMS System.
<br>


        
<b>
Request Response
</b>
 will be used when you want to test response time of a JMS service that 
processes messages sent to the Request Queue as this mode will wait for 
the response on the Reply queue sent by this service.
<br>

		
		
</p>

		
<p>

		Versions of JMeter after 2.3.2 use the properties java.naming.security.[principal|credentials] - if present -
		when creating the Queue Connection. If this behaviour is not desired, set the JMeter property
		
<b>
JMSSampler.useSecurity.properties=false
</b>

		
</p>

		
<br>



<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>JMeter does not include any JMS implementation jar; this must be downloaded from the JMS provider and put in the lib directory
</td></tr>
</tbody></table>
<p></p>

	
<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/JMS_Point-to-Point.png" height="662" width="746"></div>
<p>
<b>Parameters</b>
<a name="JMS_Point-to-Point_parms">
</a><a class="sectionlink" href="#JMS_Point-to-Point_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this element that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>QueueConnection Factory</td>
<td>
    The JNDI name of the queue connection factory to use for connecting to the messaging system.
  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>JNDI Name Request queue</td>
<td>
    This is the JNDI name of the queue to which the messages are sent.
  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>JNDI Name Reply queue</td>
<td>
    The JNDI name of the receiving queue. If a value is provided here and the communication style is Request Response
    this queue will be monitored for responses to the requests sent.
  
</td>
<td>
No
</td>
</tr>
<tr>
<td>JMS Selector</td>
<td>
    Message Selector as defined by JMS specification to extract only 
    messages that respect the Selector condition. Syntax uses subpart of SQL 92.
  
</td>
<td>
No
</td>
</tr>
<tr>
<td>Communication style</td>
<td>
    The Communication style can be 
<b>
Request Only
</b>
 (also known as Fire and Forget) or 
<b>
Request Response
</b>
:
    
<ul>

    
<li>
<b>
Request Only
</b>
 will only send messages and will not monitor replies. As such it can be used to put load on a system.
</li>

    
<li>
<b>
Request Response
</b>
 will send messages and monitor the replies it receives. Behaviour 
depends on the value of the JNDI Name Reply Queue.
    If JNDI Name Reply Queue has a value, this queue is used to monitor 
the results. Matching of request and reply is done with
    the message id of the request and the correlation id of the reply. 
If the JNDI Name Reply Queue is empty, then
    temporary queues will be used for the communication between the 
requestor and the server.
    This is very different from the fixed reply queue. With temporary 
queues the sending thread will block until the reply message has been 
received.
    With 
<b>
Request Response
</b>
 mode, you need to have a Server that listens to messages sent to Request Queue and sends replies to 
    queue referenced by 
<code>
message.getJMSReplyTo()
</code>
.
</li>

    
</ul>

  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Use alternate fields for message correlation</td>
<td>
    These check-boxes select the fields which will be used for matching the response message with the original request.
    
<ul>

    
<li>
<b>
Use Request Message Id
</b>
 - if selected, the request JMSMessageID will be used, 
    otherwise the request JMSCorrelationID will be used. 
    In the latter case the correlation id must be specified in the request.
</li>

    
<li>
<b>
Use Response Message Id
</b>
 - if selected, the response JMSMessageID will be used, 
    otherwise the response JMSCorrelationID will be used.
    
</li>

    
</ul>

    There are two frequently used JMS Correlation patterns:
    
<ul>

    
<li>
JMS Correlation ID Pattern - 
    i.e. match request and response on their correlation Ids
    =&gt; deselect both checkboxes, and provide a correlation id.
</li>

    
<li>
JMS Message ID Pattern -
    i.e. match request message id with response correlation id
    =&gt; select "Use Request Message Id" only.
    
</li>

    
</ul>

    In both cases the JMS application is responsible for populating the correlation ID as necessary.
    
<p>
</p><table bgcolor="#bbbb00" border="1" cellpadding="2" cellspacing="0" width="50%">
<tbody><tr><td>if the same queue is used to send and receive messages, 
    then the response message will be the same as the request message.
    In which case, either provide a correlation id and clear both checkboxes;
    or select both checkboxes to use the message Id for correlation.
    This can be useful for checking raw JMS throughput.
</td></tr>
</tbody></table>
<p></p>

  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Timeout</td>
<td>
      The timeout in milliseconds for the reply-messages. If a reply has not been received within the specified
      time, the specific testcase failes and the specific reply message received after the timeout is discarded.
  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Use non-persistent delivery mode?</td>
<td>
      Whether to set DeliveryMode.NON_PERSISTENT.
  
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Content</td>
<td>
      The content of the message.
  
</td>
<td>
No
</td>
</tr>
<tr>
<td>JMS Properties</td>
<td>
      The JMS Properties are properties specific for the underlying messaging system.
      For example: for WebSphere 5.1 web services you will need to set the JMS Property targetService to test
      webservices through JMS.
  
</td>
<td>
No
</td>
</tr>
<tr>
<td>Initial Context Factory</td>
<td>
    The Initial Context Factory is the factory to be used to look up the JMS Resources.
  
</td>
<td>
No
</td>
</tr>
<tr>
<td>JNDI properties</td>
<td>
     The JNDI Properties are the specific properties for the underlying JNDI implementation.
  
</td>
<td>
No
</td>
</tr>
<tr>
<td>Provider URL</td>
<td>
    The URL for the jms provider.
  
</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="JUnit_Request">18.1.16 JUnit Request</a>
<a class="sectionlink" href="#JUnit_Request" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>

The current implementation supports standard Junit convention and extensions. It also
includes extensions like oneTimeSetUp and oneTimeTearDown. The sampler works like the
JavaSampler with some differences.

<br>

1. rather than use Jmeter's test interface, it scans the jar files for 
classes extending junit's TestCase class. That includes any class or 
subclass.

<br>

2. Junit test jar files should be placed in jmeter/lib/junit instead of 
/lib directory.
In versions of JMeter after 2.3.1, you can also use the "user.classpath"
 property to specify where to look for TestCase classes. 

<br>

3. Junit sampler does not use name/value pairs for configuration like 
the JavaSampler. The sampler assumes setUp and tearDown will configure 
the test correctly.

<br>

4. The sampler measures the elapsed time only for the test method and does not include setUp and tearDown.

<br>

5. Each time the test method is called, Jmeter will pass the result to the listeners.

<br>

6. Support for oneTimeSetUp and oneTimeTearDown is done as a method. 
Since Jmeter is multi-threaded, we cannot call 
oneTimeSetUp/oneTimeTearDown the same way Maven does it.

<br>

7. The sampler reports unexpected exceptions as errors.
There are some important differences between standard JUnit test runners and JMeter's
implementation. Rather than make a new instance of the class for each test, JMeter
creates 1 instance per sampler and reuses it.
This can be changed with checkbox "Create a new instance per sample".
<br>


The current implementation of the sampler will try to create an instance
 using the string constructor first. If the test class does not declare a
 string constructor, the sampler will look for an empty constructor. 
Example below:<br>
<br>
Empty Constructor:<br>
public class myTestCase {<br>
  public myTestCase() {}<br>
}<br>
<br>
String Constructor:<br>
public class myTestCase {<br>
  public myTestCase(String text) {<br>
    super(text);<br>
  }<br>
}<br>
By default, Jmeter will provide some default values for the 
success/failure code and message. Users should define a set of unique 
success and failure codes and use them uniformly across all tests.<br>
General Guidelines
<br>


If you use setUp and tearDown, make sure the methods are declared public. If you do not, the test may not run properly.

<br>


Here are some general guidelines for writing Junit tests so they work 
well with Jmeter. Since Jmeter runs multi-threaded, it is important to 
keep certain things in mind.<br>
<br>
1. Write the setUp and tearDown methods so they are thread safe. This generally means avoid using static memebers.<br>
2. Make the test methods discrete units of work and not long sequences 
of actions. By keeping the test method to a descrete operation, it makes
 it easier to combine test methods to create new test plans.<br>
3. Avoid making test methods depend on each other. Since Jmeter allows 
arbitrary sequencing of test methods, the runtime behavior is different 
than the default Junit behavior.<br>
4. If a test method is configurable, be careful about where the 
properties are stored. Reading the properties from the Jar file is 
recommended.<br>
5. Each sampler creates an instance of the test class, so write your 
test so the setup happens in oneTimeSetUp and oneTimeTearDown.

<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/junit_sampler.png" height="536" width="397"></div>
<p>
<b>Parameters</b>
<a name="JUnit_Request_parms">
</a><a class="sectionlink" href="#JUnit_Request_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this element that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Search for JUnit4 annotations</td>
<td>Select this to search for JUnit 4 tests (@Test annotations)
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Package filter</td>
<td>Comma separated list of packages to show. Example, org.apache.jmeter,junit.framework.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Class name</td>
<td>Fully qualified name of the JUnit test class.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Constructor string</td>
<td>String pass to the string constructor. If a string is set, the sampler will use the
   string constructor instead of the empty constructor.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Test method</td>
<td>The method to test.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Success message</td>
<td>A descriptive message indicating what success means.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Success code</td>
<td>An unique code indicating the test was successful.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Failure message</td>
<td>A descriptive message indicating what failure means.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Failure code</td>
<td>An unique code indicating the test failed.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Error message</td>
<td>A description for errors.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Error code</td>
<td>Some code for errors. Does not need to be unique.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Do not call setUp and tearDown</td>
<td>Set the sampler not to call setUp and tearDown.
   By default, setUp and tearDown should be called. Not calling those 
methods could affect the test and make it inaccurate.
    This option should only be used with calling oneTimeSetUp and 
oneTimeTearDown. If the selected method is oneTimeSetUp or 
oneTimeTearDown,
     this option should be checked.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Append assertion errors</td>
<td>Whether or not to append assertion errors to the response message.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Append runtime exceptions</td>
<td>Whether or not to append runtime exceptions to the response message. Only applies if "Append assertion errors" is not selected.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Create a new Instance per sample</td>
<td>Whether or not to create a new JUnit instance for each sample. 
Defaults to false, meaning JUnit TestCase is created one and reused.
</td>
<td>
Yes
</td>
</tr>
</tbody></table>
<p></p>
<p>

The following JUnit4 annotations are recognised:

</p><ul>


<li>
@Test - used to find test methods and classes. The "expected" and "timeout" attributes are supported.
</li>


<li>
@Before - treated the same as setUp() in JUnit3
</li>


<li>
@After - treated the same as tearDown() in JUnit3
</li>


<li>
@BeforeClass, @AfterClass - treated as test methods so they can be run independently as required
</li>


</ul>


<p></p>
<p>

Note that JMeter currently runs the test methods directly, rather than leaving it to JUnit.
This is to allow the setUp/tearDown methods to be excluded from the sample time. 

</p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>
<table border="0" cellpadding="2" cellspacing="0">
<tbody><tr><td>
<font face="arial,helvetica,sanserif">
<h3>
<a name="Mail_Reader_Sampler">18.1.17 Mail Reader Sampler</a>
<a class="sectionlink" href="#Mail_Reader_Sampler" title="Link to here">¶</a></h3>
</font>
</td></tr>
<tr><td>


<p>

The Mail Reader Sampler can read (and optionally delete) mail messages using POP3(S) or IMAP(S) protocols.

</p>


<p><b>Control Panel</b></p>
<div align="center"><img src="page_fichiers/mailreader_sampler.png" height="409" width="547"></div>
<p>
<b>Parameters</b>
<a name="Mail_Reader_Sampler_parms">
</a><a class="sectionlink" href="#Mail_Reader_Sampler_parms" title="Link to here">¶</a></p><table border="1" cellpadding="2" cellspacing="0">
<tbody><tr><th>Attribute</th><th>Description</th><th>Required</th></tr>
<tr>
<td>Name</td>
<td>Descriptive name for this element that is shown in the tree.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Server Type</td>
<td>The protocol used by the provider: e.g. pop3, pop3s, imap, imaps.
or another string representing the server protocol.
For example 
<code>
file
</code>
 for use with the read-only mail file provider.
The actual provider names for POP3 and IMAP are 
<code>
pop3
</code>
 and 
<code>
imap
</code>


</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Server</td>
<td>Hostname or IP address of the server. See below for use with 
<code>
file
</code>
 protocol.
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Port</td>
<td>Port to be used to connect to the server (optional)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Username</td>
<td>User login name
</td>
<td>
No
</td>
</tr>
<tr>
<td>Password</td>
<td>User login password (N.B. this is stored unencrypted in the test plan)
</td>
<td>
No
</td>
</tr>
<tr>
<td>Folder</td>
<td>The IMAP(S) folder to use. See below for use with 
<code>
file
</code>
 protocol.
</td>
<td>
Yes, if using IMAP(S)
</td>
</tr>
<tr>
<td>Number of messages to retrieve</td>
<td>Set this to retrieve all or some messages
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Delete messages from the server</td>
<td>If set, messages will be deleted after retrieval
</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Store the message using MIME</td>
<td>Whether to store the message as MIME. 
If so, then the entire raw message is stored in the Response Data; the 
headers are not stored as they are available in the data. 
If not, the message headers are stored as Response Headers. 
A few headers are stored (Date, To, From, Subject) in the body.

</td>
<td>
Yes
</td>
</tr>
<tr>
<td>Use no security features</td>
<td>Indicates that the connection to the server does not use any security protocol.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use SSL</td>
<td>Indicates that the connection to the server must use the SSL protocol.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use StartTLS</td>
<td>Indicates that the connection to the server should attempt to start the TLS protocol.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Enforce StartTLS</td>
<td>If the server does not start the TLS protocol the connection will be terminated.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Trust All Certificates</td>
<td>When selected it will accept all certificates independent of the CA.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Use local truststore</td>
<td>When selected it will only accept certificates that are locally trusted.
</td>
<td>
No
</td>
</tr>
<tr>
<td>Local truststore</td>
<td>Path to file containing the trusted certificates.
Relative paths are resolved against the current directory.

<br>

Failing that, against the directory containing the test script (JMX file).

</td>
<td>
No
</td>
</tr>
</tbody></table>
<p></p>
<p>

Messages are stored as subsamples of the main sampler.
In versions of JMeter after 2.3.4, multipart message parts are stored as subsamples of the message.

</p>
<p>


<b>
Special handling for "file" protocol:
</b>
<br>


The 
<code>
file
</code>
 JavaMail provider can be used to read raw messages from files.
The 
<code>
server
</code>
 field is used to specify the path to the parent of the 
<code>
folder
</code>
.
Individual message files should be stored with the name 
<code>
n.msg
</code>
,
where 
<code>
n
</code>
 is the message number.
Alternatively, the 
<code>
server
</code>
 field can be the name of a file which contains a single message.
The current implementation is quite basic, and is mainly intended for debugging purposes. 

</p>
</td></tr>
<tr><td><br></td></tr>
</tbody></table>
<hr>

<br>
</td>
</tr>
<tr><td colspan="2">
<hr noshade="noshade" size="1">
</td></tr>
<tr><td colspan="2">
<div align="center"><font color="#525D76" size="-1"><em>
Copyright © 1999-2012, Apache Software Foundation
</em></font></div>
</td></tr>
<tr><td colspan="2">
<div align="center"><font color="#525D76" size="-1">
Apache, Apache JMeter, JMeter, the Apache feather, and the Apache JMeter logo are
trademarks of the Apache Software Foundation.
</font>
</div>
</td></tr>
</tbody></table>

































</body></html>
<!-- end the processing -->