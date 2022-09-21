<%@ page contentType="text/html; charset=Windows-31J" pageEncoding="UTF-8" %>
<%
  String search = "新規";
  if (request.getParameter("search") != null) {
  	search = new String(request.getParameter("search").getBytes("8859_1"), "Windows-31J");
  }
%>
<html>
<head>
  <meta charset="Windows-31J">
</head>
<body>
<h1>Please click on submit button to send the request</h1><br><br>
<h2>1.新規</h2> 
<h2>2.てすと</h2> 
<form method="post" action="index.jsp">
<input type="text" name="search" value="<%= search %>" size="80">
<input type="submit" value="　Submit　　">
</form>
<a href="index.jsp"> Back to page </a>
</body>
</html>
