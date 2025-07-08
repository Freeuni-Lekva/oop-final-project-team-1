<%@ page import="models.AccountManagerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.MessagesDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%
    String curr = (String) session.getAttribute("userName");
    AccountManagerDAO acc = (AccountManagerDAO) application.getAttribute("accountManager");
    String currentUser = (String) session.getAttribute("userName");
    MessagesDAO ms = (MessagesDAO) application.getAttribute("messages");
    ArrayList<MessagesDAO.Message> messages = ms.getMessages(currentUser);

%>




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
    <%
        if(acc.isAdmin(curr)){
    %>
    <a href="AdminControlPanelServlet">
        <button>Admin Control Panel</button>
    </a>
    <%
        }
    %>
</div>
<%
    session.setAttribute("User", request.getParameter("name") );
%>
<form action="filteredUsers.jsp" method="get">
    <input type="search" name="query" placeholder="Search..." />
    <button type="submit">Search</button>
</form>

<%

    if (messages.isEmpty()) {
%>
<p>No messages</p>
<%
} else {
    for(MessagesDAO.Message temp : messages) {
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