<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>NotesKeepr Login</h1>
        <form action="login" method="post">
            Username<br> 
            <input type="text" name="username"><br>
            Password<br>
            <input type="password" name="password"><br>
            <input type="submit" value="Login">
        </form>
        <p><b><a href="forgot">Forgot password</a></b></p>
        <p><b><a href="reset">Reset password</a></b></p>
        <p>${errorMessage}</p>
    </body>
</html>
