<%@ page import="java.util.List" %>
<%@ page import="models.QuizAttempt" %>
<%@ page import="models.Quiz" %>
<%
    List<QuizAttempt> fullHistory = (List<QuizAttempt>) request.getAttribute("fullHistory");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Quiz History</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f9f9f9;
            margin: 0;
            padding: 40px 20px;
            color: #333;
        }
        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #444;
        }
        table {
            width: 100%;
            max-width: 700px;
            margin: 0 auto 40px auto;
            border-collapse: collapse;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }
        th, td {
            padding: 15px 20px;
            text-align: left;
        }
        th {
            background-color: #66a6ff;
            color: white;
            font-weight: 600;
            letter-spacing: 0.05em;
        }
        tr:nth-child(even) {
            background-color: #f2f7ff;
        }
        tr:hover {
            background-color: #dbe9ff;
        }
        p {
            text-align: center;
            font-size: 1.1rem;
            color: #555;
        }
        form {
            text-align: center;
        }
        button {
            background-color: #66a6ff;
            color: white;
            border: none;
            padding: 12px 30px;
            font-size: 1.1rem;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #4a8fdc;
        }
    </style>
</head>
<body>

<h2>Your Full Quiz History</h2>

<%
    if (fullHistory == null || fullHistory.isEmpty()) {
%>
<p>You have no quiz attempts yet.</p>
<%
} else {
%>
<table>
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
