<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.MessagesDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Filtered Users</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f7f9fc;
            margin: 0;
            padding: 30px 20px;
            color: #333;
        }
        nav {
            display: flex;
            gap: 12px;
            margin-bottom: 25px;
            justify-content: center;
        }
        nav a button, nav button {
            background-color: #66a6ff;
            color: white;
            border: none;
            padding: 10px 22px;
            font-size: 1rem;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        nav a button:hover, nav button:hover {
            background-color: #4a8fdc;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
            font-weight: 700;
            color: #444;
        }
        .user-card {
            background: white;
            padding: 15px 20px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 12px auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 15px;
        }
        .user-name {
            font-size: 1.1rem;
            font-weight: 600;
            color: #222;
        }
        .status {
            font-style: italic;
            color: #888;
            margin-right: 15px;
        }
        form {
            margin: 0;
        }
        form button {
            background-color: #66a6ff;
            color: white;
            border: none;
            padding: 8px 14px;
            font-size: 0.95rem;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        form button:hover {
            background-color: #4a8fdc;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
        }
        p.no-users {
            text-align: center;
            color: #666;
            font-size: 1.1rem;
            margin-top: 40px;
        }
    </style>
</head>
<body>

<nav>
    <a href="HomePage.jsp"><button>Home</button></a>
    <a href="index.jsp"><button>Logout</button></a>
</nav>

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

    if (people != null && !people.isEmpty()) {
        for (String user : people) {
            if (!user.equals(currentUser)) {
%>
<div class="user-card">
    <div class="user-name"><%= user %></div>
    <div>
        <% if (am.isFriend(currentUser, user)) { %>
        <span class="status">Friends</span>
        <% } else if (am.hasPendingFriendRequest(currentUser, user)) { %>
        <span class="status">Friend request sent</span>
        <% } else if (am.hasPendingFriendRequest(user, currentUser)) { %>
        <div class="action-buttons">
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
        </div>
        <% } else { %>
        <form action="filteredUsers.jsp" method="get">
            <input type="hidden" name="query" value="<%= query %>" />
            <input type="hidden" name="friendName" value="<%= user %>" />
            <button type="submit">Add Friend</button>
        </form>
        <% } %>
    </div>
</div>
<%
        }
    }
} else {
%>
<p class="no-users">No users found.</p>
<%
    }
%>

</body>
</html>
