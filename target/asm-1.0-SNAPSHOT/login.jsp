<%-- 
    Document   : login
    Created on : 2024年4月8日, 上午6:32:11
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <div id="loginContainer">
            <h2>Login</h2>
            <ict:errorMessage message="${loginError}" />
            <form action="main" method="post">
                <input type="hidden" name="action" value="authenticate">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <button type="submit">Login</button>
                </div>
            </form>
        </div>
    </body>
</html>
