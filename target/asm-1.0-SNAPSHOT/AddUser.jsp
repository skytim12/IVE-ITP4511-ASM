<%-- 
    Document   : AddUser
    Created on : 2024年5月6日, 上午2:38:16
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add User</title>
    </head>
    <body>
        <h1>Add New User</h1>
        <form action="/UserController" method="post">
            <input type="hidden" name="action" value="addUser">

            <label for="userID">User ID:</label>
            <input type="text" id="userID" name="userID" required><br>

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" required><br>

            <label for="role">Role:</label>
            <select id="role" name="role">
                <option value="GeneralUser">User</option>
                <option value="Technician">Technician</option>
                <option value="Courier">Courier</option>
                <option value="AdminTechnician">Admin Technician</option>
            </select><br>

            <label for="campusName">Campus:</label>
            <select id="campusName" name="campusName">
                <option value="Chai Wan">Chai Wan</option>
                <option value="Lee Wai Lee">Lee Wai Lee</option>
                <option value="Sha Tin">Sha Tin</option>
                <option value="Tuen Mun">Tuen Mun</option>
                <option value="Tsing Yi">Tsing Yi</option>
            </select><br>

            <input type="submit" value="Add User">
        </form>
    </body>
</html>