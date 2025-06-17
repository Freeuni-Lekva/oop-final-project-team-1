<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>LoginPage</title>
</head>
<body>
<h1>Welcome To QuizWebsite </h1>
    <p>Please log in.</p>
    <form action="login" method="post">
        User Name: <input type="text" name="name"/> <br/>
        Password: <input type="password" name="password"/>
        <input type="submit" value="Login"/>
    </form>
    <br/>
    <a href="CreateNewAccount.jsp">Create New Account</a>
</body>
</html>