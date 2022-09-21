<html>
<body>
<%
int time = Integer.parseInt(request.getParameter("ms"));
Thread.sleep(time);
%>
<p>Waited <%=time%>ms</p>
</body>
</html>
