<%@ page import="models.AccountManagerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.MessagesDAO" %>
<%@ page import="java.sql.SQLException" %><%--
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
    AccountManagerDAO am = (AccountManagerDAO) application.getAttribute("accountManager");
    String query = request.getParameter("query");

    String newFriend = request.getParameter("friendName");
    String currentUser = (String) session.getAttribute("userName");

    MessagesDAO ms = (MessagesDAO) application.getAttribute("messages");

    if (newFriend != null && !newFriend.equals(currentUser)) {

        try {
            am.sendFriendRequest(currentUser, newFriend);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ms.addMessage(new MessagesDAO.Message(newFriend, currentUser,
                "You have a new friend request from " + currentUser + ".", true));
    }
    ArrayList<String> people = am.getPeople(query);
    if (people != null) {
        for (String user : people) {
            if (!user.equals(currentUser)) {
%>
<div>
    <p><%= user %></p>
    <% if (am.isFriend(currentUser, user)) { %>
    <span>Friends</span>
    <% } else if (am.hasPendingFriendRequest(currentUser, user)) { %>
    <span>Friend request sent</span>
    <% } else if (am.hasPendingFriendRequest(user, currentUser)) { %>
    <form action="friend" method="post">
        <input type="hidden" name="from" value="<%= user %>" />
        <input type="hidden" name="message" value="You have a new friend request from <%= user %>." />
        <input type="hidden" name="action" value="accept" />
        <input type="hidden" name="source" value="filteredUsers" />
        <button type="submit">Accept</button>
    </form>
    <form action="friend" method="post">
        <input type="hidden" name="from" value="<%= user %>" />
        <input type="hidden" name="message" value="You have a new friend request from <%= user %>." />
        <input type="hidden" name="action" value="reject" />
        <input type="hidden" name="source" value="filteredUsers" />
        <button type="submit">Reject</button>
    </form>
    <% } else { %>
    <form action="filteredUsers.jsp" method="get">
        <input type="hidden" name="query" value="<%= query %>" />
        <input type="hidden" name="friendName" value="<%= user %>" />
        <button type="submit">Add Friend</button>
    </form>
    <% } %>
</div>
<%
        }
    }
} else {
%>
<p>No users found.</p>
<%
    }
%>

</body>
</html>




