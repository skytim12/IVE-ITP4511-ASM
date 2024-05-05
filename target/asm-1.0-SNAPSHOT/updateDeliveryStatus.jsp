<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Delivery Status</title>
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

            function showSuccessMessage(message) {
                alert(message);
            }

            function showErrorMessage(message) {
                alert(message);
            }

            <% if (request.getAttribute("successMessage") != null) { %>
            showSuccessMessage("<%= request.getAttribute("successMessage") %>");
            <% } %>

            <% if (request.getAttribute("errorMessage") != null) { %>
            showErrorMessage("<%= request.getAttribute("errorMessage") %>");
            <% } %>
        </script>
    </head>
    <body>
        <h1>Delivery Management</h1>
        <table>
            <thead>
                <tr>
                    <th>Delivery ID</th>
                    <th>From Campus</th>
                    <th>To Campus</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="delivery" items="${deliveries}" varStatus="status">
                    <tr onclick="toggleDetails('details${status.index}')">
                        <td>${delivery.deliveryID}</td>
                        <td>${delivery.fromCampus}</td>
                        <td>${delivery.toCampus}</td>
                        <td>${delivery.status}</td>
                        <td>
                            <form action="UpdateDeliveryStatusController" method="post">
                                <input type="hidden" name="deliveryID" value="${delivery.deliveryID}">
                                <input type="hidden" name="action" value="updateStatus">
                                <button type="submit">Update Status</button>
                            </form>
                        </td>
                    </tr>
                    <!-- Hidden row for displaying detailed equipment info -->
                    <tr id="details${status.index}" style="display:none;">
                        <td colspan="5">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Equipment ID</th>
                                        <th>Name</th>
                                        <th>Description</th>
                                        <th>Condition</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${delivery.equipmentList}">
                                        <tr>
                                            <td>${item.equipmentID}</td>
                                            <td>${item.name}</td>
                                            <td>${item.description}</td>
                                            <td>${item.condition}</td>
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
