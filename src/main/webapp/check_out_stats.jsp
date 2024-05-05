<%-- 
    Document   : check_out_stats
    Created on : 2024年5月6日, 上午2:08:39
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Check-Out Statistics</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>Check-Out Statistics</h1>
    <h2>By Equipment</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Equipment ID</th>
                <th>Name</th>
                <th>Campus Name</th>
                <th>Equipment Condition</th>
                <th>Total Check-Outs</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${equipmentStats}">
                <tr>
                    <td>${item.equipmentID}</td>
                    <td>${item.name}</td>
                    <td>${item.campusName}</td>
                    <td>${item.condition}</td>
                    <td>${item.checkOutCount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <h2>By Campus</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Campus Name</th>
                <th>Total Check-Outs</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="campus" items="${campusStats}">
                <tr>
                    <td>${campus.campusName}</td>
                    <td>${campus.totalCheckOuts}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
