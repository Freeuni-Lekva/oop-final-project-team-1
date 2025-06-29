<%@ page import="models.AccountManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Messages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>HomePage</title>
</head>
 <h1>User:  <%=(String) session.getAttribute("userName")%>  </h1>
<div style="display: flex; gap: 10px;">
    <a href="Quizzes">
    <button>Quizzes</button>
    </a>
    <a href="createQuiz.jsp">
    <button>Create a Quiz</button>
    </a>
    <a href="index.jsp">
        <button>Logout</button>
    </a>
    <a href="FriendList.jsp">
        <button>Friends</button>
    </a>

</div>
<%
    session.setAttribute("User", request.getParameter("name") );
%>
<form action="filteredUsers.jsp" method="get">
    <input type="search" name="query" placeholder="Search..." />
    <button type="submit">Search</button>
</form>

<%
    String curr = (String) session.getAttribute("userName");
    AccountManager acc = (AccountManager) application.getAttribute("accountManager");
    String currentUser = (String) session.getAttribute("userName");
    Messages ms = (Messages) application.getAttribute("messages");
    ArrayList<Messages.Message> messages = ms.getMessages(currentUser);
    if (messages.isEmpty()) {
%>
<p>No messages</p>
<%
} else {
    for(Messages.Message temp : messages) {
        String type="message";
        if(temp.friendReq)type="Friend Request";

%>
<div>
    <a href="message.jsp?messageType=<%=type%>&from=<%=temp.from%>&message=<%=temp.message%>"><%=temp.from + " Sent you a " + type %>
    </a><br>
</div>
<%

        }

    }


%>


<body>
<br/>
</body>
</html>