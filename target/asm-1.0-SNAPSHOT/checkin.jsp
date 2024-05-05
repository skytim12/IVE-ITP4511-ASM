<%-- 
    Document   : checkin
    Created on : 2024年5月5日, 下午9:34:54
    Author     : Soman 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check In</title>
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

            function openReportModal(equipmentID, equipmentName) {
                document.getElementById('modalEquipmentID').value = equipmentID;
                document.getElementById('modalEquipmentName').value = equipmentName;
                document.getElementById('reportDamageModal').style.display = 'block';
            }

            function closeReportModal() {
                document.getElementById('reportDamageModal').style.display = 'none';
            }

            window.onclick = function (event) {
                var modal = document.getElementById('reportDamageModal');
                if (event.target === modal) {
                    closeReportModal();
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

        <h1>Check In</h1>

        <nav>
            <ul>
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="inventory_management.jsp">Inventory Management</a></li>
                <li><a href="damage_report_management.jsp">Damage Reports</a></li>
            </ul>
        </nav>

        <table>
            <thead>
                <tr>
                    <th>Delivery ID</th>
                    <th>User Name</th>
                    <th>From Campus</th>
                    <th>To Campus </th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="delivery" items="${deliveries}" varStatus="status">
                    <tr onclick="toggleDetails('details${status.index}')">
                        <td>${delivery.reservationID}</td>
                        <td>${delivery.userName}</td>
                        <td>${delivery.fromCampus}</td>
                        <td>${delivery.toCampus}</td>
                        <td>${delivery.status}</td>
                        <td>
                            <form action="/CheckInController" method="post">
                                <input type="hidden" name="deliveryID" value="${delivery.deliveryID}">
                                <input type="hidden" name="action" value="checkin">
                                <input type="submit" value="checkin">
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
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="equipment" items="${delivery.equipmentList}">
                                        <tr>
                                            <td>${equipment.name}</td>
                                            <td>${equipment.description}</td>
                                            <td>${equipment.campusName}</td>
                                            <td>${equipment.currentCampus}</td>
                                            <td>${equipment.condition}</td>
                                            <td>${equipment.exclusiveForStaff}</td>
                                            <td>
                                                <button onclick="openReportModal('${equipment.equipmentID}', '${equipment.name}')">Report Damage</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <!-- Report Damage Modal -->
        <div id="reportDamageModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeReportModal()">&times;</span>
                <h2>Report Damage</h2>
                <form action="/CheckInController" method="post">
                    <input type="hidden" name="action" value="report">
                    <label for="modalEquipmentID">Equipment ID:</label>
                    <input type="text" id="modalEquipmentID" name="equipmentID" readonly><br>
                    <label>Equipment Name:</label>
                    <input type="text" id="modalEquipmentName" readonly><br>
                    <label for="description">Description of Damage:</label>
                    <textarea id="description" name="description" rows="4" required></textarea>
                    <input type="submit" value="Submit Report">
                </form>
            </div>
        </div>
    </body>
</html>
