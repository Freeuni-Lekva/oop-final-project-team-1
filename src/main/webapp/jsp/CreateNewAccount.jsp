<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Create New Account</title>
</head>
<body>
<h1>Create New Account<h1>
<p>Please enter proposed name and passwrod</p>
<form action="RegisterServlet" method="post">
  User Name: <input type="text" name="name"/> <br/>
  Password: <input type="password" name="password"/>
    <input type="submit" value="Login"/>
 </form>
<br/>
</body>
</html>