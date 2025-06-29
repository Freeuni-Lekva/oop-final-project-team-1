<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Quiz" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%
    ArrayList<models.Quiz> quizzes = (ArrayList<models.Quiz>) request.getAttribute("quizzes");
    ArrayList<models.Quiz> popularQuizzes = (ArrayList<models.Quiz>) request.getAttribute("popularQuizzes");
    ArrayList<models.Quiz> recentlyTakenQuizzes = (ArrayList<models.Quiz>) request.getAttribute("recentlyTakenQuizzes");
    ArrayList<models.Quiz> recentlyCreatedQuizzes = (ArrayList<models.Quiz>) request.getAttribute("recentlyCreatedQuizzes");

%>
<html>
<head>
    <title></title>
</head>
<div style="display: flex; gap: 10px;">
    <a href="HomePage.jsp">
        <button>Home Page</button>
    </a>
    <button>Create a Quiz</button>
    <a href="index.jsp">
        <button>Logout</button>
    </a>
    <a href="FriendList.jsp">
        <button>Friends</button>
    </a>

</div>
<body>
<h5>List Of Popular Quizzes: </h5>
<table>
    <tr>
        <%
            for (Quiz q : popularQuizzes) {
        %>
        <td style="padding: 15px; border: 1px solid lightgray; vertical-align: top;">
            <a href="TakeQuizServlet?quizId=<%= q.getQuizID() %>">
                <b><%= q.getTitle() %></b>
            </a><br>
            Created by: <%= q.getCreator() %><br>
            Times taken: <%= q.getTimesTaken() %>
        </td>
        <%
            }
        %>
    </tr>
</table>
<h5>List Of recently created Quizzes</h5>
<table>
    <tr>
        <%
            for (Quiz q : recentlyCreatedQuizzes) {
        %>
        <td style="padding: 15px; border: 1px solid lightgray; vertical-align: top;">
            <a href="TakeQuizServlet?quizId=<%= q.getQuizID() %>">
                <b><%= q.getTitle() %></b>
            </a><br>
            Created by: <%= q.getCreator() %><br>
            Times taken: <%= q.getTimesTaken() %>
        </td>
        <%
            }
        %>
    </tr>
</table>
<h5>Recently Taken Quizzes: </h5>
<table>
    <tr>
        <%
            for (Quiz q : recentlyTakenQuizzes) {
        %>
        <td style="padding: 15px; border: 1px solid lightgray; vertical-align: top;">
            <a href="TakeQuizServlet?quizId=<%= q.getQuizID() %>">
                <b><%= q.getTitle() %></b>
            </a><br>
            Created by: <%= q.getCreator() %><br>
            Times taken: <%= q.getTimesTaken() %>
        </td>
        <%
            }
        %>
    </tr>
</table>
<h5>Available Quizzes: </h5>
<table>
    <tr>
        <%
            for (Quiz q : quizzes) {
        %>
        <td style="padding: 15px; border: 1px solid lightgray; vertical-align: top;">
            <a href="TakeQuizServlet?quizId=<%= q.getQuizID() %>">
                <b><%= q.getTitle() %></b>
            </a><br>
            Created by: <%= q.getCreator() %><br>
            Times taken: <%= q.getTimesTaken() %>
        </td>
        <%
            }
        %>
    </tr>
</table>

<p></p>
</body>
</html>