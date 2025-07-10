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
    <title>Quiz List</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 30px;
            background: linear-gradient(135deg, #89f7fe, #66a6ff);
            color: #333;
        }
        .nav-buttons {
            margin-bottom: 30px;
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }
        .nav-buttons a button {
            background: #66a6ff;
            border: none;
            color: white;
            padding: 10px 18px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        .nav-buttons a button:hover {
            background: #4a8fdc;
        }
        h5 {
            color: #222;
            margin-top: 40px;
            margin-bottom: 15px;
            font-weight: 700;
        }
        .quiz-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit,minmax(280px,1fr));
            gap: 20px;
        }
        .quiz-card {
            background: white;
            padding: 20px;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .quiz-card b {
            font-size: 1.2rem;
            margin-bottom: 8px;
            color: #444;
        }
        .quiz-card small {
            color: #666;
            margin-bottom: 12px;
            display: block;
        }
        label {
            font-weight: 600;
            margin-bottom: 6px;
            display: block;
            color: #333;
        }
        input[type="radio"] {
            margin-right: 6px;
            cursor: pointer;
        }
        input[type="submit"] {
            margin-top: 15px;
            padding: 12px;
            background: #66a6ff;
            border: none;
            color: white;
            border-radius: 8px;
            font-weight: 700;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        input[type="submit"]:hover {
            background: #4a8fdc;
        }
        form {
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }
    </style>
</head>
<body>

<div class="nav-buttons">
    <a href="HomePage.jsp"><button>Home Page</button></a>
    <a href="createQuiz.jsp"><button>Create a Quiz</button></a>
    <a href="index.jsp"><button>Logout</button></a>
    <a href="FriendList.jsp"><button>Friends</button></a>
</div>

<h5>List Of Popular Quizzes:</h5>
<div class="quiz-grid">
    <% for (Quiz q : popularQuizzes) { %>
    <div class="quiz-card">
        <form action="QuizDisplay" method="get">
            <b><%= q.getTitle() %></b>
            <small>Created by: <%= q.getCreator() %></small>
            <small>Times taken: <%= q.getTimesTaken() %></small>

            <label>Choose Display Mode:</label>
            <input type="radio" name="isSinglePage" value="true" required> One Page<br>
            <input type="radio" name="isSinglePage" value="false"> Multiple Pages<br>

            <input type="hidden" name="quizId" value="<%= q.getQuizID() %>">
            <input type="submit" value="Start Quiz">
        </form>
    </div>
    <% } %>
</div>

<h5>List Of Recently Created Quizzes:</h5>
<div class="quiz-grid">
    <% for (Quiz q : recentlyCreatedQuizzes) { %>
    <div class="quiz-card">
        <form action="QuizDisplay" method="get">
            <b><%= q.getTitle() %></b>
            <small>Created by: <%= q.getCreator() %></small>
            <small>Times taken: <%= q.getTimesTaken() %></small>

            <label>Choose Display Mode:</label>
            <input type="radio" name="isSinglePage" value="true" required> One Page<br>
            <input type="radio" name="isSinglePage" value="false"> Multiple Pages<br>

            <input type="hidden" name="quizId" value="<%= q.getQuizID() %>">
            <input type="submit" value="Start Quiz">
        </form>
    </div>
    <% } %>
</div>

<h5>Recently Taken Quizzes:</h5>
<div class="quiz-grid">
    <% for (Quiz q : recentlyTakenQuizzes) { %>
    <div class="quiz-card">
        <form action="QuizDisplay" method="get">
            <b><%= q.getTitle() %></b>
            <small>Created by: <%= q.getCreator() %></small>
            <small>Times taken: <%= q.getTimesTaken() %></small>

            <label>Choose Display Mode:</label>
            <input type="radio" name="isSinglePage" value="true" required> One Page<br>
            <input type="radio" name="isSinglePage" value="false"> Multiple Pages<br>

            <input type="hidden" name="quizId" value="<%= q.getQuizID() %>">
            <input type="submit" value="Start Quiz">
        </form>
    </div>
    <% } %>
</div>

<h5>Available Quizzes:</h5>
<div class="quiz-grid">
    <% for (Quiz q : quizzes) { %>
    <div class="quiz-card">
        <form action="QuizDisplay" method="get">
            <b><%= q.getTitle() %></b>
            <small>Created by: <%= q.getCreator() %></small>
            <small>Times taken: <%= q.getTimesTaken() %></small>

            <label>Choose Display Mode:</label>
            <input type="radio" name="isSinglePage" value="true" required> One Page<br>
            <input type="radio" name="isSinglePage" value="false"> Multiple Pages<br>

            <input type="hidden" name="quizId" value="<%= q.getQuizID() %>">
            <input type="submit" value="Start Quiz">
        </form>
    </div>
    <% } %>
</div>

</body>
</html>
