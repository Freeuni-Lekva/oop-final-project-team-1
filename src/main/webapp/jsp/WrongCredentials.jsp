Welcome.jsp<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Wrong Credentials</title>
</head>
<body>
<h1><%= "Please Try Again" %></h1>
<p>Eiter your user name or password is incorrect.Please try again .</p>
<form action="LoginServlet" method="post">
  User Name: <input type="text" name="name"/> <br/>
  Password: <input type="password" name="password"/>
  <input type="submit" value="Login"/>
</form>
<br/>
<a href="CreateNewAccount.jsp">Create New Account</a>
</body>
</html>