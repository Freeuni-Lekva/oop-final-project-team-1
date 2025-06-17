<%@ page import="models.AccountManager" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/17/2025
  Time: 6:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Filtered Users</title>
</head>
<div style="display: flex; gap: 10px;">
    <button>Take a Quiz</button>
    <button>Create a Quiz</button>
    <a href="index.jsp">
        <button>Logout</button>
    </a>
</div>

<body>
<h1>Search Result:</h1>
<%
    AccountManager am = (AccountManager) application.getAttribute("accountManager");
    ArrayList<String> people = am.getPeople(request.getParameter("query"));
    if (people != null) {
        for (String p : people) {
%>
<div style="display: flex; align-items: center; gap: 10px; margin-bottom: 5px;">
    <p style="margin: 0;"><%= p %></p>
    <form action="AddFriendServlet" method="post" style="margin: 0;">
        <input type="hidden" name="friendName" value="<%= p %>" />
        <button type="submit">Add Friend</button>
    </form>
</div>
<%
    }
} else {
%>
<p>No users found.</p>
<%
    }

%>

</body>
</html>
