<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friends</title>
</head>
<body>
<%
    AccountManagerDAO am = (AccountManagerDAO) application.getAttribute("accountManager");
    ArrayList<String> friends = am.getFriends((String) session.getAttribute("userName"));
    if (friends != null || friends.isEmpty()) {
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
<div style="margin-bottom: 20px; display: flex; gap: 10px;">
    <form action="HomePage.jsp" method="get" style="display: inline;">
        <button type="submit">Home</button>
    </form>
    <form action="Quizzes" method="get" style="display: inline;">
        <button type="submit">Quizzes</button>
    </form>
    <form action="LogoutServlet" method="post" style="display: inline;">
        <button type="submit">Logout</button>
    </form>
</div>
</body>
</html>
