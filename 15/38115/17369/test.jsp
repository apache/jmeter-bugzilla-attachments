<%@ page language="java" pageEncoding="Windows-31J" contentType="text/html;charset=Windows-31J" %>
<%
  String str = null;
  String query = null;

  if (request.getMethod().equals("GET")) {
    query = request.getQueryString();
    if (query != null) {
      str = java.net.URLDecoder.decode(query.substring(query.indexOf("=") + 1), "Windows-31j");
    } else {
      str = "“o˜^";
    }
  } else if (request.getMethod().equals("POST")) {
    request.setCharacterEncoding("Windows-31J");
    str = request.getParameter("str");
  }

%>
<html>
<head>
<title>Encoding Test JSP</title>
<script type="text/javascript">
<!--
//document.write("Hello");
function doGet() {
  document.F1.method="get";
  document.F1.submit();
}
// -->
</script>
<noscript>
Please turn on JavaScript!
</noscript>
</head>
<body>
<p>This JSP tests decoding methods.</p>
<p>If you push post button, string in textbox will be decoded by org.apache.catalina.util.RequestUtil.</p>
<p>If you push get button, string in textbox will be decoded by java.util.URLDecoder.<p>
<p>Some characters aren't decoded correctly. It is caused by mismatch between IE encoding and URLDecoder decoding.</p>
<hr/>
<p>You can copy&paste following Japanese Characters and push button!</p>
<dt>“o˜^</dt>
<dd>(URLEncoder.encode result=<%=java.net.URLEncoder.encode("“o˜^", "Windows-31J")%>)</dd>
<dt>–¼‘O</dt>
<dd>(URLEncoder.encode result=<%=java.net.URLEncoder.encode("–¼‘O", "Windows-31J")%>)</dd>
<hr>
<form name="F1" method="post" action="test.jsp">
String  <input type="text" name="str" value="<%=str%>"/>
<input type="submit" value="post"/>
<input type="button" value="get" onclick="doGet()"/>
</form>
<p> getQueryString: <%=query%></p>
<p> Decoded String: <%=str%></p>
</body>
</html>