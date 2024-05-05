<%-- 
    Document   : arrange_delivery
    Created on : 2024年5月10日
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arrange Delivery</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        <h1>Arrange Delivery</h1>
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
                            <button type="button" onclick="arrangeDelivery('${reservation.reservationID}', '${reservation.destinationCampus}', '${reservation.userID}');">Arrange Delivery</button>
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



        <!-- Arrange Delivery Modal -->
        <div id="arrangeDeliveryModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Arrange Delivery</h2>
                <form action="ArrangeDeliveryController" method="post">
                    <input type="hidden" name="action" value="addDelivery">
                    <label for="modalReservationID">Reservation ID:</label>
                    <input type="text" id="modalReservationID" name="reservationID" readonly><br>
                    <label for="toCampus">To Campus:</label>
                    <input type="text" id="toCampus" name="toCampus" readonly><br>
                    <label for="courier">Courier (User ID):</label>
                    <input type="text" id="courier" name="courierID" readonly><br>
                    <input type="hidden" name="status" value="Scheduled">
                    <input type="submit" value="Confirm Delivery">
                </form>
            </div>
        </div>

        <script>
            // Function to open the Arrange Delivery Modal and set the values
            function arrangeDelivery(reservationID, toCampus, courierID) {
                // Setting up the form fields in the modal with the respective values
                document.getElementById('modalReservationID').value = reservationID;
                document.getElementById('toCampus').value = toCampus;
                document.getElementById('courier').value = courierID;

                // Display the modal
                var modal = document.getElementById('arrangeDeliveryModal');
                modal.style.display = 'block';

                // Function to close the modal if the close span is clicked
                modal.querySelector('.close').onclick = function () {
                    modal.style.display = "none";
                }
            }

            // Close the modal on click outside
            window.onclick = function (event) {
                var modal = document.getElementById('arrangeDeliveryModal');
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
        </script>

    </body>
</html>