<%-- 
    Document   : booking_management
    Created on : 2024年4月8日, 上午6:33:00
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Booking Management</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script>
            function toggleDetails(rowId) {
                var detailsRow = document.getElementById(rowId);
                if (detailsRow.style.display === 'none') {
                    detailsRow.style.display = 'table-row';
                } else {
                    detailsRow.style.display = 'none';
                }
            }
        </script>
    </head>
    <body>
        <h1>Booking Management</h1>
        <nav>
            <ul>
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="inventory_management.jsp">Inventory Management</a></li>
                <li><a href="booking_details.jsp">All Booking Record</a></li>
                <li><a href="/ArrangeDeliveryController">Arrange Delivery</a></li>
                <li><a href="damage_report_management.jsp">Damage Reports</a></li>
            </ul>
        </nav>

        <table>
            <thead>
                <tr>
                    <th>Reservation ID</th>
                    <th>User ID</th>
                    <th>User Name</th>
                    <th>Reserved From</th>
                    <th>Reserved To</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="reservation" items="${reservations}" varStatus="status">
                    <tr onclick="toggleDetails('details${status.index}')">
                        <td>${reservation.reservationID}</td>
                        <td>${reservation.userID}</td>
                        <td>${reservation.userName}</td>
                        <td>${reservation.reservedFrom}</td>
                        <td>${reservation.reservedTo}</td>
                        <td>${reservation.status}</td>
                        <td>
                            <form action="BookingController" method="post">
                                <input type="hidden" name="reservationID" value="${reservation.reservationID}" />
                                <input type="submit" name="action" value="Accept" />
                                <input type="submit" name="action" value="Decline" />
                            </form>
                        </td>
                    </tr>
                    <tr id="details${status.index}" style="display:none;">
                        <td colspan="7">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Description</th>
                                        <th>Campus Name</th>
                                        <th>Current Campus</th>
                                        <th>Condition</th>
                                        <th>Exclusive For Staff</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="equipment" items="${reservation.equipmentList}">
                                        <tr>
                                            <td>${equipment.name}</td>
                                            <td>${equipment.description}</td>
                                            <td>${equipment.campusName}</td>
                                            <td>${equipment.currentCampus}</td>
                                            <td>${equipment.condition}</td>
                                            <td>${equipment.exclusiveForStaff}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>

