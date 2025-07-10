<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="dao.MessagesDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Message Details</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f9fafb;
            margin: 20px;
            padding: 0 15px;
            color: #333;
        }
        h2 {
            color: #2a7ae2;
        }
        p {
            font-size: 1.1rem;
        }
        form {
            display: inline-block;
            margin-right: 10px;
            margin-top: 10px;
        }
        button {
            background-color: #2a7ae2;
            color: white;
            border: none;
            padding: 8px 18px;
            border-radius: 6px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #1a5ac7;
        }
        .message-container {
            background-color: #fff;
            padding: 20px 25px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.07);
            max-width: 600px;
        }
    </style>
</head>
<body>

<div class="message-container">
    <%
        String type = request.getParameter("messageType");
        MessagesDAO ms = (MessagesDAO) session.getAttribute("messages");

        if ("Friend Request".equals(type)) {
            String from = request.getParameter("from");
            String s = request.getParameter("message");
    %>
    <h2>You Have A New Friend Request From <%= from %></h2>

    <form action="friend" method="post">
        <input type="hidden" name="from" value="<%= from %>" />
        <input type="hidden" name="message" value="<%= s %>" />
        <input type="hidden" name="action" value="accept" />
        <button type="submit">Accept</button>
    </form>

    <form action="friend" method="post">
        <input type="hidden" name="from" value="<%= from %>" />
        <input type="hidden" name="message" value="<%= s %>" />
        <input type="hidden" name="action" value="reject" />
        <button type="submit">Reject</button>
    </form>

    <%
    } else {
        String from = request.getParameter("from");
        String message = request.getParameter("message");
    %>
    <p><strong>Message from <%= from %>:</strong></p>
    <p><%= message %></p>

    <form action="TextArea.jsp" method="get">
        <input type="hidden" name="from" value="<%= from %>" />
        <input type="hidden" name="action" value="Reply" />
        <input type="hidden" name="message" value="<%= message %>" />
        <button type="submit">Reply</button>
    </form>

    <form action="textMessage" method="post">
        <input type="hidden" name="from" value="<%= from %>" />
        <input type="hidden" name="message" value="<%= message %>" />
        <input type="hidden" name="action" value="Delete" />
        <button type="submit">Delete</button>
    </form>
    <%
        }
    %>
</div>

</body>
</html>
