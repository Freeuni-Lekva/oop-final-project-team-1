<%@ page import="models.AccountManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friends</title>
</head>
<body>
<%
    AccountManager am = (AccountManager) application.getAttribute("accountManager");
    ArrayList<String> friends = am.getFriends((String) session.getAttribute("userName"));
    if (friends != null) {
        for (String friend : friends) {
%>
<div style="display: inline-flex; align-items: center; gap: 10px; margin-bottom: 8px;">
    <p style="margin: 0;"><%= friend %></p>

    <form action="TextArea.jsp" method="get" style="margin: 0;">
        <input type="hidden" name="from" value=<%= friend %> />
        <button type="submit">Send Message</button>
    </form>
</div>
<%
    }
}






    else {
%>
<p>No friends found.</p>
<%
    }
%>
</body>
</html>
