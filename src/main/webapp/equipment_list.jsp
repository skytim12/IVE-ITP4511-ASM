<%-- 
    Document   : equipment_list
    Created on : 2024年4月8日, 上午6:32:22
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Equipment List</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="js/script.js" defer></script>
    </head>
    <body>
        <h1>Equipment List</h1>
        <nav>
            <ul>
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="/EquipmentController">All Equipment</a></li>
                <li><a href="/BorrowingController">My Reservations Record</a></li>
                <li><a href="/WishlistController">My WishList</a></li>
                <li><a href="#">Notification</a></li>
                <li><a href="/ProfileController">Profile</a></li>
            </ul>
        </nav>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Available</th>
                    <th>Initial Campus</th>
                    <th>Current Campus</th>
                    <th>Condition</th>
                    <th>Description</th> 
                    <th>Total</th>
                    <th>Staff Only</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="equipment" items="${equipmentList}">
                    <tr>
                        <td>${equipment.name}</td>
                        <td>${equipment.available}</td>
                        <td>${equipment.campusName}</td>
                        <td>${equipment.currentCampus}</td>
                        <td>${equipment.condition}</td>
                        <td>${equipment.description}</td>
                        <td>${equipment.totalQuantity}</td>
                        <td>${equipment.exclusiveForStaff == 'Yes' ? 'Yes' : 'No'}</td>
                        <td>
                            <c:choose>
                                <c:when test="${equipment.available == 'Yes'}">
                                    <!-- Add to Cart Button -->
                                    <button type="button" class="cartBtn"
                                            data-name="${equipment.name}"
                                            data-available="${equipment.available}"
                                            data-campus="${equipment.campusName}"
                                            data-condition="${equipment.condition}"
                                            data-description="${equipment.description}"
                                            data-total="${equipment.totalQuantity}"
                                            data-exclusive="${equipment.exclusiveForStaff}"
                                            data-quantity="1">Add to Cart</button>
                                </c:when>
                                <c:otherwise>
                                    <!-- Add to Wishlist Button -->
                                    <button type="button" class="wishlistBtn"
                                            data-id="${equipment.equipmentIDs}"
                                            data-name="${equipment.name}"
                                            data-available="${equipment.available}"
                                            data-campus="${equipment.campusName}"
                                            data-currentcampus="${equipment.currentCampus}"
                                            data-condition="${equipment.condition}"
                                            data-exclusive="${equipment.exclusiveForStaff}"
                                            data-description="${equipment.description}">Add to Wishlist</button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <h2>Your Cart</h2>
            <c:if test="${not empty cart}">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Campus Name</th>
                            <th>Condition</th>
                            <th>Reserved From</th>  
                            <th>Reserved To</th>    
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cart}">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.campusName}</td>
                                <td>${item.condition}</td>
                                <td>${item.reservedFrom}</td>  
                                <td>${item.reservedTo}</td>    
                                <td>${item.quantity}</td>
                                <td>
                                    <form action="EquipmentController" method="post">
                                        <input type="hidden" name="action" value="removeFromCart"/>
                                        <input type="hidden" name="name" value="${item.name}"/>
                                        <input type="hidden" name="campus" value="${item.campusName}"/>
                                        <input type="hidden" name="condition" value="${item.condition}"/>
                                        <button type="submit">Remove</button>
                                    </form>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <form action="EquipmentController" method="post">
                    <input type="hidden" name="action" value="confirmCart" />
                    <button type="submit">Confirm</button>
                </form>
            </c:if>
            <c:if test="${empty cart}">
                <p>Your cart is empty.</p>
            </c:if>
        </div>

        <div id="wishlistModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <form action="EquipmentController" method="post">
                    <input type="hidden" name="action" value="addToWishlist" />
                    <label for="equipmentID">Equipment ID:</label>
                    <select name="equipmentID" id="equipmentID"></select><br>
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" readonly><br>
                    <label for="available">Available:</label>
                    <input type="text" id="available" name="available" readonly><br>
                    <label for="campusName">Campus Name:</label>
                    <input type="text" id="campusName" name="campusName" readonly><br>
                    <label for="cartCurrentCampus">Current Campus:</label>
                    <input type="text" id="cartCurrentCampus" name="currentCampus" readonly><br>
                    <label for="condition">Condition:</label>
                    <input type="text" id="condition" name="condition" readonly><br>
                    <label for="exclusiveForStaff">Exclusive for Staff:</label>
                    <input type="text" id="exclusiveForStaff" name="exclusiveForStaff" readonly><br>
                    <label for="description">Description:</label>
                    <input type="text" id="description" name="description" readonly><br>
                    <button type="submit">Add to Wishlist</button>
                </form>
            </div>
        </div>

        <div id="cartModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <form id="cartForm" action="EquipmentController" method="post">
                    <input type="hidden" name="action" value="addToCart" />

                    <label for="cartName">Name:</label>
                    <input type="text" id="cartName" name="name" readonly><br>

                    <label for="cartAvailable">Available:</label>
                    <input type="text" id="cartAvailable" name="available" readonly><br>

                    <label for="cartCampus">Campus:</label>
                    <input type="text" id="cartCampus" name="campus" readonly><br>

                    <label for="cartCondition">Condition:</label>
                    <input type="text" id="cartCondition" name="condition" readonly><br>

                    <label for="cartDescription">Description:</label>
                    <input type="text" id="cartDescription" name="description" readonly><br>

                    <label for="cartReservedFrom">Reserved From:</label>
                    <input type="date" id="cartReservedFrom" name="reservedFrom" min="${todayDate}" value="${todayDate}" required><br>

                    <label for="cartReservedTo">Reserved To:</label>
                    <input type="date" id="cartReservedTo" name="reservedTo" min="${todayDate}" value="${todayDate}" required><br>


                    <label for="cartTotal">Total Available:</label>
                    <input type="text" id="cartTotal" name="total" readonly><br>

                    <label for="cartExclusiveForStaff">Exclusive for Staff:</label>
                    <input type="text" id="cartExclusiveForStaff" name="exclusiveForStaff" readonly><br>

                    <label for="cartQuantity">Quantity:</label>
                    <input type="number" id="cartQuantity" name="quantity" min="1"><br>

                    <button type="submit">Add to Cart</button>
                </form>
            </div>
        </div>






        <script>
            // Function to show a success message
            function showSuccessMessage(message) {
                alert(message);
            }

            // Function to show an error message
            function showErrorMessage(message) {
                alert(message);
            }

            // Check for success message from server and display
            <% if (request.getAttribute("successMessage") != null) { %>
            showSuccessMessage("<%= request.getAttribute("successMessage") %>");
            <% } %>

            // Check for error message from server and display
            <% if (request.getAttribute("errorMessage") != null) { %>
            showErrorMessage("<%= request.getAttribute("errorMessage") %>");
            <% } %>
        </script>


    </body>
</html>
