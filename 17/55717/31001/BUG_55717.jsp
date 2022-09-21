<%@ page session="false"  contentType="text/html"  buffer="8kb" %>
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
<html>
<body>

<%
//response.sendRedirect("http://stackoverflow.com/search?[jmeter]");
//response.sendRedirect("http://localhost:8080/toto?titi=[]!@$%^*()#");
//response.sendRedirect("http://localhost:8080/%5B%5D");
// http://localhost:8080/?%5B%5D%21%40%24%25%5E*%28%29
//response.sendRedirect("http://localhost:8080/?%25%5B%5D!@$%^*()#");
//response.sendRedirect("http://localhost:8080/?toto=titi&trte=titi");
//response.sendRedirect("http://localhost:8080/%5B%5D?[]!@$%^*()#");
//response.sendRedirect("http://localhost:8080/[]");
//response.sendRedirect("/Pub/Login.aspx");
response.setStatus(302);
response.setHeader("Location", "Pub/Login.aspx");

%>

</body>
</html>