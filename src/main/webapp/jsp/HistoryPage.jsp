<%@ page import="java.util.List" %>
<%@ page import="models.QuizAttempt" %>
<%@ page import="models.Quiz" %>
<%
    List<QuizAttempt> fullHistory = (List<QuizAttempt>) request.getAttribute("fullHistory");
%>

<html>
<head><title>Quiz History</title></head>
<body>
<h2>Your Full Quiz History</h2>

<%
    if (fullHistory == null || fullHistory.isEmpty()) {
%>
<p>You have no quiz attempts yet.</p>
<%
} else {
%>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Quiz Title</th>
        <th>Score</th>

    </tr>
    <%
        for (QuizAttempt attempt : fullHistory) {
            Quiz quiz = attempt.getQuiz();
    %>
    <tr>
        <td><%= quiz.getTitle() %></td>
        <td><%= attempt.getScore() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>

<form action="HomePage.jsp" method="get">
    <button type="submit">Back to Home</button>
</form>

</body>
</html>
