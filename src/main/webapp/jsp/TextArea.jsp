<%--
  Created by IntelliJ IDEA.
  User: giorgi
  Date: 19-06-25
  Time: 23:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Messenger</title>


</head>
<body>
<form action="textMessage" method="post">

    <label for="messageText">Message:</label><br/>
    <textarea id="messageText" name="messageText" rows="5" cols="40" required></textarea><br/><br/>
    <input type="hidden" name="from" value="<%=request.getParameter("from")%>"/>
    <input type="hidden" name="action" value="<%=request.getParameter("action")%>"/>
    <input type="hidden" name="message" value="<%=request.getParameter("message")%>"/>
    <button type="submit">Send</button>
</form>
</body>
</html>
