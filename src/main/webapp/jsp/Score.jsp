<%--
  Created by IntelliJ IDEA.
  User: giorgi
  Date: 06-07-25
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  int maxscore = (Integer)request.getAttribute("maxScore");
  int userscore = (Integer)request.getAttribute("score");


%>
<html>
<head>
    <title>Score</title>
</head>
<body>
<div align="center">
    <p>Your Score</p>
    <p><%= userscore %> / <%= maxscore %></p>


<form action="HomePage.jsp" method="get" >
    <button type="submit">Go to Homepage</button>
</form>
    <form action="Quizzes" method="get" >
        <button type="submit">Go to Quizzes</button>
    </form>
</div>

</body>
</html>
