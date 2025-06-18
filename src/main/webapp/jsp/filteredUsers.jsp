<%@ page import="models.AccountManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Messages" %><%--
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
    <a href="HomePage.jsp" >
        <button>Home</button>
    </a>

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
    String query = request.getParameter("query");
    ArrayList<String> people = am.getPeople(query);
    String newFriend = request.getParameter("friendName");
    String CurrentUser = (String) session.getAttribute("userName");
    ArrayList<String> sentFriends = (ArrayList<String>) session.getAttribute(CurrentUser+"SendFriends");
    Messages ms = (Messages) application.getAttribute("messages");
    if (sentFriends == null) {
        sentFriends = new ArrayList<>();
        session.setAttribute(CurrentUser+"SendFriends", sentFriends);
    }else{
        sentFriends.add(newFriend);
       Messages.Message m = new Messages.Message(newFriend,CurrentUser,"You Have A New Friend Suggestion From "+CurrentUser+".",true);
        ms.addMessage(m);
    }

    if (people != null) {
        for (String p : people) {
            if(!p.equals(CurrentUser)){
%>
<div style="display: flex; align-items: center; gap: 10px; margin-bottom: 5px;">
    <p style="margin: 0;"><%= p %></p>
    <% if (sentFriends.contains(p)) { %>
    <span style="color: green;">Friend Request sent</span>
    <% }else{%>
    <form action="filteredUsers.jsp" method="get">
        <input type="hidden" name="query" value="<%= query %>" />
        <input type="hidden" name="friendName" value="<%= p %>" />
        <button type="submit">Add Friend</button>
    </form> <%
    }%>

</div>
<%

    }
}} else {
%>
<p>No users found.</p>
<%
    }

%>

</body>
</html>
