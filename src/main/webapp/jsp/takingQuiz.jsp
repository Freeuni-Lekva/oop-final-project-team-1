<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.*" %>

<%
  List<Questions> questions = (List<Questions>) session.getAttribute("questions");

   int quizId = (Integer) session.getAttribute("quizId");


%>

<html>
<head><title>Take Quiz</title></head>
<body>

<% if ("true".equals(request.getParameter("isSinglePage"))) { %>
<form action="SubmitQuiz" method="post">
  <input type="hidden" name="quizId" value="<%= quizId %>">

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
    <input type="text" name="answer_<%= fib.getId() %>"><br>
  </div>
  <%
  } else if (q instanceof QuestionResponse) {
    QuestionResponse resp = (QuestionResponse) q;
  %>
  <div>
    <b>Question <%= qNum++ %>: <%= resp.getQuestion() %></b><br>
    <input type="text" name="answer_<%= resp.getId() %>"><br>
  </div>
  <%
  } else if (q instanceof PictureResponse) {
    PictureResponse pic = (PictureResponse) q;
  %>
  <div>
    <b>Question <%= qNum++ %>: <%= pic.getQuestion() %></b><br>
    <img src="<%= pic.getImage() %>" alt="Question Image" width="300"><br>
    <input type="text" name="answer_<%= pic.getId() %>"><br>
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
<form action="takingQuiz.jsp" method="get">
  <input type="hidden" name="isSinglePage" value="false">

  <%
    int ind;
    if((Integer)request.getAttribute("index") == null){
      ind = Integer.parseInt(request.getParameter("index"));
    }else{
      ind = (Integer)request.getAttribute("index");
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
    <input type="text" name="answer_<%= fib.getId() %>"><br>
  </div>
  <%
  } else if (q instanceof QuestionResponse) {
    QuestionResponse resp = (QuestionResponse) q;
  %>
  <div>
    <b>Question : <%= resp.getQuestion() %></b><br>
    <input type="text" name="answer_<%= resp.getId() %>"><br>
  </div>
  <%
  } else if (q instanceof PictureResponse) {
    PictureResponse pic = (PictureResponse) q;
  %>
  <div>
    <b>Question : <%= pic.getQuestion() %></b><br>
    <img src="<%= pic.getImage() %>" alt="Question Image" width="300"><br>
    <input type="text" name="answer_<%= pic.getId() %>"><br>
  </div>
  <%
    }

    if (ind+1 < questions.size()) {
  %>
  <input type="hidden" name="index" value="<%= ind+1 %>">
  <input type="submit" value="Next">
</form>
  <%
  } else {
  %>
<form action="SubmitQuiz" method="post">
  <input type="submit" value="Submit Quiz">
</form>
<%
    }
  }
%>

</body>
</html>
