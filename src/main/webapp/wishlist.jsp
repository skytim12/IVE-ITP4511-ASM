<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <title>My Wishlist</title>
    </head>
    <body>
        <h1>My Wishlist</h1>
        <nav>
            <ul>
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="/EquipmentController">All Equipment</a></li>
                <li><a href="/BorrowingController">Borrowing Records</a></li>
                <li><a href="/WishlistController">My Wishlist</a></li>
                <li><a href="profile.jsp">Profile</a></li>
            </ul>
        </nav>
        <table>
            <thead>
                <tr>
                    <th>Equipment ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${wishlist}">
                    <tr>
                        <td>${item.equipmentID}</td>
                        <td>${item.name}</td>
                        <td>${item.description}</td>
                        <td>
                            <form action="/WishlistController" method="post">
                                <input type="hidden" name="action" value="removeFromWishlist"/>
                                <input type="hidden" name="equipmentID" value="${item.equipmentID}"/>
                                <button type="submit">Remove</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
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
