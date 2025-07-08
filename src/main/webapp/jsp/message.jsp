<%@ page import="models.AccountManagerDAO" %>
<%@ page import="models.MessagesDAO" %><%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/17/2025
  Time: 9:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
   String type =  request.getParameter("messageType");

    MessagesDAO ms = (MessagesDAO) session.getAttribute("messages");
    if ("Friend Request".equals(type)) {
        String from = request.getParameter("from");
        String s = request.getParameter("message");
        System.out.println(from);
%>

<h2>You Have A New Friend Request From <%=from%></h2>

<form action="friend" method="post" style="display:inline;">
    <input type="hidden" name="from" value="<%=from%>"/>
    <input type="hidden" name="message" value="<%=s%>"/>
    <input type="hidden" name="action" value="accept"/>
    <button type="submit">Accept</button>
</form>



<form action="HomePage.jsp" method="post" style="display:inline;">
    <input type="hidden" name="from" value="<%=from%>"/>
<form action="friend" method="post" style="display:inline;">
    <input type="hidden" name="from" value="<%=from%>"/>
    <input type="hidden" name="message" value="<%=s%>"/>
    <input type="hidden" name="action" value="reject"/>
    <button type="submit">Reject</button>
</form>



<%
} else {
%>

<p style="margin: 0;">Message from <strong><%= request.getParameter("from") %></strong>:</p>
<p><%= request.getParameter("message") %></p>
<form action="TextArea.jsp" method="get" style="display:inline;">
    <input type="hidden" name="from" value="<%=request.getParameter("from")%>"/>
    <input type="hidden" name="action" value="Reply"/>
    <input type="hidden" name="message" value="<%=request.getParameter("message")%>"/>
    <button type="submit">Reply</button>
</form>
<form action="textMessage" method="post" style="display:inline;">
    <input type="hidden" name="from" value="<%=request.getParameter("from")%>"/>
    <input type="hidden" name="message" value="<%=request.getParameter("message")%>"/>
    <input type="hidden" name="action" value="Delete"/>
    <button type="submit">Delete</button>
</form>

<%
    }
%>
</body>
</html>
