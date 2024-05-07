<%-- 
    Document   : check_out_stats
    Created on : 2024年5月6日, 上午2:08:39
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Check-Out Statistics</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-6">
                    <h2 class="text-center">By Equipment</h2>
                    <table class="table table-striped">
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
                </div>
                <div class="col-md-6">
                    <h2 class="text-center">By Campus</h2>
                    <table class="table table-striped">
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
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
