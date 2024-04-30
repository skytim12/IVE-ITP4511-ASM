<%-- 
    Document   : equipment_list
    Created on : 2024年4月8日, 上午6:32:22
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Equipment List</title>
    </head>
    <body>
        <h1>Equipment List</h1>
        <nav>
            <ul>
               
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="/EquipmentController">All Equipment</a></li>
                <li><a href="borrowing_records.jsp">My Reservations</a></li>
                <li><a href="profile.jsp">Profile</a></li>
            </ul>
        </nav>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Available</th>
                    <th>Campus</th>
                    <th>Condition</th>
                    <th>Description</th> 
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="equipment" items="${equipmentList}">
                    <tr>
                        <td>${equipment.name}</td>
                        <td>${equipment.available}</td>
                        <td>${equipment.campusName}</td>
                        <td>${equipment.condition}</td>
                        <td>${equipment.description}</td>
                        <td>${equipment.totalQuantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
