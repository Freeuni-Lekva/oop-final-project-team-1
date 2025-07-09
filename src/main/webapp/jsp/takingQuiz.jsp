<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.*" %>
<%@ page import="java.util.Map" %>

<%
  List<Questions> questions = (List<Questions>) session.getAttribute("questions");
  List<Map<String, Object>> topScorers = (List<Map<String, Object>>) session.getAttribute("topScorers");

   int quizId = (Integer) session.getAttribute("quizId");


%>

<html>
<head><title>Take Quiz</title></head>
<body>

<h3>Top 3 Scorers:</h3>

<ol>
  <% if (topScorers != null) {
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
  <%
    int qNum = 1;
    for (Questions q : questions) {
      if (q instanceof MultipleChoice) {
        MultipleChoice mc = (MultipleChoice) q;
  %>
  <div>
    <b>Question <%= qNum++ %>: <%= mc.getQuestion() %></b><br>
    <% for (models.Option opt : mc.getOptions()) { %>
    <input type="radio"
           name="answer_<%= mc.getId() %>"
           value="<%= opt.getId() %>">
    <%= opt.getOptionText() %><br>
    <% } %>
  </div>
  <%
  } else if (q instanceof FillInBlank) {
    FillInBlank fib = (FillInBlank) q;
  %>
  <div>
    <b>Question <%= qNum++ %>: <%= fib.getQuestion() %></b><br>
    <input type="text" name="answer_<%= fib.getId() %>"> required <br>
  </div>
  <%
  } else if (q instanceof QuestionResponse) {
    QuestionResponse resp = (QuestionResponse) q;
  %>
  <div>
    <b>Question <%= qNum++ %>: <%= resp.getQuestion() %></b><br>
    <input type="text" name="answer_<%= resp.getId() %>"> required<br>
  </div>
  <%
  } else if (q instanceof PictureResponse) {
    PictureResponse pic = (PictureResponse) q;
  %>
  <div>
    <b>Question <%= qNum++ %>: <%= pic.getQuestion() %></b><br>
    <img src="<%= pic.getImage() %>" alt="Question Image" width="300"><br>
    <input type="text" name="answer_<%= pic.getId() %>">required<br>
  </div>
  <%
      }
    }
  %>

  <input type="submit" value="Submit Quiz">
</form>

<%
} else {

%>
<form action="SubmitQuizServlet" method="post">
  <input type="hidden" name="isSinglePage" value="false">

  <%
    int ind;
    if((Integer)request.getAttribute("index") == null){
      ind = Integer.parseInt(request.getParameter("index"));
    }else{
      ind = (Integer)request.getAttribute("index");
    }

    String immediateFeedback = request.getParameter("immediateFeedback");
    Integer  currentScore = (Integer) request.getAttribute("score");;
    if ("true".equals(immediateFeedback)) {
      int totalQuestions = questions.size();
  %>
  <div style="text-align:center;">
    <p><strong>Current Score:</strong> <%= currentScore != null ? currentScore : 0 %></p>
    <p><strong>Question:</strong> <%= ind + 1 %> / <%= totalQuestions %></p>
    <p><strong>Remaining:</strong> <%= totalQuestions - (ind + 1) %></p>
  </div>
  <%
    }




    Questions q = questions.get(ind);
    if (q instanceof MultipleChoice) {
      MultipleChoice mc = (MultipleChoice) q;
  %>
  <div>
    <b>Question : <%= mc.getQuestion() %></b><br>
    <% for (models.Option opt : mc.getOptions()) { %>
    <input type="radio"
           name="answer_<%= mc.getId() %>"
           value="<%= opt.getId() %>">
    <%= opt.getOptionText() %><br>
    <% } %>
  </div>
  <%
  } else if (q instanceof FillInBlank) {
    FillInBlank fib = (FillInBlank) q;
  %>
  <div>
    <b>Question : <%= fib.getQuestion() %></b><br>
    <input type="text" name="answer_<%= fib.getId() %>">required<br>
  </div>
  <%
  } else if (q instanceof QuestionResponse) {
    QuestionResponse resp = (QuestionResponse) q;
  %>
  <div>
    <b>Question : <%= resp.getQuestion() %></b><br>
    <input type="text" name="answer_<%= resp.getId() %>">required<br>
  </div>
  <%
  } else if (q instanceof PictureResponse) {
    PictureResponse pic = (PictureResponse) q;
  %>
  <div>
    <b>Question : <%= pic.getQuestion() %></b><br>
    <img src="<%= pic.getImage() %>" alt="Question Image" width="300"><br>
    <input type="text" name="answer_<%= pic.getId() %>">required<br>
  </div>
  <%
    }
  %>
  <input type="hidden" name="index" value="<%= ind+1 %>">
  <input type="hidden" name="immediateFeedback"   value ="<%= request.getParameter("immediateFeedback") %>">
  <input type="hidden" name="score" value="<%= (currentScore==null)? 0: currentScore%>">
  <input type="submit" value="Next">

  <%
  }
%>

</body>
</html>
