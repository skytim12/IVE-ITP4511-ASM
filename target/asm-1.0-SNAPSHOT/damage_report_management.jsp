<%-- 
    Document   : damage_report_management
    Created on : 2024年4月8日, 上午6:33:18
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Damage Report Management</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script>
            function openModal(reportID, equipmentID, equipmentName, reportDate, reportedBy, description, available, condition) {
                document.getElementById('modalReportID').value = reportID;
                document.getElementById('modalEquipmentID').value = equipmentID;
                document.getElementById('modalEquipmentName').value = equipmentName;
                document.getElementById('modalReportDate').value = reportDate;
                document.getElementById('modalReportedBy').value = reportedBy;
                document.getElementById('modalDescription').value = description;

                document.querySelector('input[name="available"][value="' + available + '"]').checked = true;
                document.getElementById('modalEquipmentCondition').value = condition;

                document.getElementById('detailsModal').style.display = 'block';
            }

            function closeModal() {
                document.getElementById('detailsModal').style.display = 'none';
            }

            window.onclick = function (event) {
                if (event.target == document.getElementById('detailsModal')) {
                    closeModal();
                }
            }

            function displayMessage(message, isSuccess) {
                const messageType = isSuccess ? 'successMessage' : 'errorMessage';
                alert(message);
            }

            window.onload = function () {
            <% if (request.getAttribute("successMessage") != null) { %>
                displayMessage("<%= request.getAttribute("successMessage") %>", true);
            <% }
    if (request.getAttribute("errorMessage") != null) { %>
                displayMessage("<%= request.getAttribute("errorMessage") %>", false);
            <% } %>
            }

        </script>
    </head>
    <body>
        <h1>Damage Report Management</h1>

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
                    <th>Report ID</th>
                    <th>Equipment ID</th>
                    <th>Equipment Name</th>
                    <th>Report Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="report" items="${damageReports}" varStatus="status">
                    <tr>
                        <td>${report.reportID}</td>
                        <td>${report.equipmentID}</td>
                        <td>${report.equipmentName}</td>
                        <td>${report.reportDate}</td>
                        <td>
                            <button onclick="openModal('${report.reportID}', '${report.equipmentID}', '${report.equipmentName}', '${report.reportDate}', '${report.reportedByName}', '${report.description}', '${report.available}', '${report.condition}')">Details and Confirm</button>

                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Single Modal for Report Details and Confirmation -->
        <div id="detailsModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal('detailsModal')">&times;</span>
                <h2>Report Details</h2>
                <form action="/DamageController" method="post">
                    <input type="hidden" name="action" value="confirmDamage">
                    <label>Report ID:</label>
                    <input type="text" id="modalReportID" name="reportID" readonly><br>
                    <label>Equipment ID:</label>
                    <input type="text" id="modalEquipmentID" name="equipmentID" readonly><br>
                    <label>Equipment Name:</label>
                    <input type="text" id="modalEquipmentName" name="equipmentName" readonly><br>
                    <label>Report Date:</label>
                    <input type="text" id="modalReportDate" name="reportDate" readonly><br>
                    <label>Reported By:</label>
                    <input type="text" id="modalReportedBy" name="reportedBy" readonly><br>
                    <label>Description:</label>
                    <textarea id="modalDescription" name="description" readonly></textarea><br>

                    <label>Available:</label>
                    <div>
                        <input type="radio" id="availableYes" name="available" value="Yes">
                        <label for="availableYes">Yes</label>
                        <input type="radio" id="availableNo" name="available" value="No">
                        <label for="availableNo">No</label>
                    </div>

                    <label>Equipment Condition:</label>
                    <select id="modalEquipmentCondition" name="condition">
                        <option value="New">New</option>
                        <option value="Good">Good</option>
                        <option value="Fair">Fair</option>
                        <option value="Poor">Poor</option>
                        <option value="Out of Service">Out of Service</option>
                    </select>
                    <br>
                    <button type="submit">Confirm Damage</button>
                </form>
            </div>
        </div>

    </body>
</html>