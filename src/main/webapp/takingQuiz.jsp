<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.*" %>
<%@ page import="java.util.Map" %>

<%
  List<Questions> questions = (List<Questions>) session.getAttribute("questions");
  List<Map<String, Object>> topScorers = (List<Map<String, Object>>) session.getAttribute("topScorers");
  int quizId = (Integer) session.getAttribute("quizId");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Take Quiz</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(135deg, #89f7fe, #66a6ff);
      margin: 0;
      padding: 30px 20px;
      color: #333;
      min-height: 100vh;
    }
    h3 {
      text-align: center;
      color: #222;
      margin-bottom: 15px;
    }
    ol {
      max-width: 400px;
      margin: 0 auto 40px auto;
      padding-left: 20px;
      background: white;
      border-radius: 10px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.1);
      padding: 20px;
    }
    ol li {
      padding: 6px 0;
      font-weight: 600;
      color: #444;
    }
    p {
      max-width: 400px;
      margin: 0 auto 40px auto;
      text-align: center;
      font-weight: 600;
      font-size: 1.1rem;
      color: #222;
      background: white;
      padding: 15px 20px;
      border-radius: 10px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.1);
    }
    form {
      max-width: 700px;
      margin: 0 auto;
      background: white;
      padding: 30px 30px 40px;
      border-radius: 15px;
      box-shadow: 0 20px 40px rgba(0,0,0,0.15);
      box-sizing: border-box;
    }
    div.question-block {
      margin-bottom: 25px;
      padding-bottom: 15px;
      border-bottom: 1px solid #ddd;
    }
    b {
      color: #4a8fdc;
      font-size: 1.1rem;
    }
    img {
      margin: 10px 0;
      border-radius: 8px;
      box-shadow: 0 5px 15px rgba(102, 166, 255, 0.3);
      max-width: 100%;
      height: auto;
    }
    input[type="text"],
    input[type="number"] {
      width: 100%;
      padding: 10px 12px;
      font-size: 1rem;
      border-radius: 8px;
      border: 1px solid #ccc;
      margin-top: 6px;
      margin-bottom: 15px;
      box-sizing: border-box;
      transition: border-color 0.3s ease;
    }
    input[type="text"]:focus,
    input[type="number"]:focus {
      border-color: #66a6ff;
      outline: none;
      box-shadow: 0 0 6px rgba(102, 166, 255, 0.6);
    }
    input[type="radio"] {
      margin-right: 8px;
      cursor: pointer;
    }
    input[type="submit"] {
      width: 100%;
      padding: 14px 0;
      background: #66a6ff;
      border: none;
      border-radius: 10px;
      color: white;
      font-weight: 700;
      font-size: 1.2rem;
      cursor: pointer;
      transition: background 0.3s ease;
      margin-top: 10px;
    }
    input[type="submit"]:hover {
      background: #4a8fdc;
    }
    .quiz-meta {
      text-align: center;
      margin-bottom: 30px;
      font-weight: 600;
      color: #444;
    }
  </style>
</head>
<body>

<h3>Top 3 Scorers:</h3>
<ol>
  <% if (topScorers != null && !topScorers.isEmpty()) {
    for (Map<String, Object> scorer : topScorers) { %>
  <li><%= scorer.get("username") %> - <%= scorer.get("score") %> pts</li>
  <% }
  } else { %>
  <li>No top scorers available.</li>
  <% } %>
</ol>

<h3>Your Highest Score:</h3>
<p>
  <%
    Integer highestScore = (Integer) request.getAttribute("highestUserScore");
    if (highestScore != null) {
      out.print(highestScore);
    } else {
      out.print("No attempts yet.");
    }
  %>
</p>

<% if ("true".equals(request.getParameter("isSinglePage"))) { %>
<form action="SubmitQuizServlet" method="post">
  <input type="hidden" name="quizId" value="<%= quizId %>">
  <input type="hidden" name="isSinglePage" value="true">
  <% int qNum = 1;
    for (Questions q : questions) { %>
  <div class="question-block">
    <b>Question <%= qNum++ %>: <%= q.getQuestion() %></b><br>
    <% if (q instanceof MultipleChoice) {
      MultipleChoice mc = (MultipleChoice) q;
      for (models.Option opt : mc.getOptions()) { %>
    <input type="radio" name="answer_<%= mc.getId() %>" value="<%= opt.getId() %>" required>
    <%= opt.getOptionText() %><br>
    <%   }
    } else if (q instanceof FillInBlank) {
      FillInBlank fib = (FillInBlank) q; %>
    <input type="text" name="answer_<%= fib.getId() %>" required><br>
    <% } else if (q instanceof QuestionResponse) {
      QuestionResponse resp = (QuestionResponse) q; %>
    <input type="text" name="answer_<%= resp.getId() %>" required><br>
    <% } else if (q instanceof PictureResponse) {
      PictureResponse pic = (PictureResponse) q; %>
    <img src="<%= request.getContextPath() + "/images/" + pic.getImage() %>"><br>
    <input type="text" name="answer_<%= pic.getId() %>" required><br>
    <% } %>
  </div>
  <% } %>
  <input type="submit" value="Submit Quiz">
</form>

<% } else {
  int ind;
  if ((Integer) request.getAttribute("index") == null) {
    ind = Integer.parseInt(request.getParameter("index"));
  } else {
    ind = (Integer) request.getAttribute("index");
  }

  String immediateFeedback = request.getParameter("immediateFeedback");
  Integer currentScore = (Integer) request.getAttribute("score");
%>
<form action="SubmitQuizServlet" method="post">
  <input type="hidden" name="isSinglePage" value="false">

  <% if ("true".equals(immediateFeedback)) {
    int totalQuestions = questions.size(); %>
  <div class="quiz-meta">
    <p><strong>Current Score:</strong> <%= currentScore != null ? currentScore : 0 %></p>
    <p><strong>Question:</strong> <%= ind + 1 %> / <%= totalQuestions %></p>
    <p><strong>Remaining:</strong> <%= totalQuestions - (ind + 1) %></p>
  </div>
  <% } %>

  <% Questions q = questions.get(ind); %>
  <div class="question-block">
    <b>Question : <%= q.getQuestion() %></b><br>
    <% if (q instanceof MultipleChoice) {
      MultipleChoice mc = (MultipleChoice) q;
      for (models.Option opt : mc.getOptions()) { %>
    <input type="radio" name="answer_<%= mc.getId() %>" value="<%= opt.getId() %>" required>
    <%= opt.getOptionText() %><br>
    <%   }
    } else if (q instanceof FillInBlank) {
      FillInBlank fib = (FillInBlank) q; %>
    <input type="text" name="answer_<%= fib.getId() %>" required><br>
    <% } else if (q instanceof QuestionResponse) {
      QuestionResponse resp = (QuestionResponse) q; %>
    <input type="text" name="answer_<%= resp.getId() %>" required><br>
    <% } else if (q instanceof PictureResponse) {
      PictureResponse pic = (PictureResponse) q; %>
    <img src="<%= request.getContextPath() + "/images/" + pic.getImage() %>"><br>
    <input type="text" name="answer_<%= pic.getId() %>" required><br>
    <% } %>
  </div>

  <input type="hidden" name="index" value="<%= ind + 1 %>">
  <input type="hidden" name="immediateFeedback" value="<%= immediateFeedback %>">
  <input type="hidden" name="score" value="<%= (currentScore == null) ? 0 : currentScore %>">

  <input type="submit" value="Next">
</form>
<% } %>

</body>
</html>
