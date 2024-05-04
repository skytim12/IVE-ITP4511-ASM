<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Borrowing Records</title>
    </head>
    <body>
        <h1>My Borrowing Records</h1>
        <nav>
            <ul>
                <c:if test="${not empty dashboardURL}">
                    <li><a href="${dashboardURL}">DashBoard</a></li>
                    </c:if>
                <li><a href="/EquipmentController">All Equipment</a></li>
                <li><a href="/BorrowingController">My Reservations Record</a></li>
                <li><a href="/WishlistController">My WishList</a></li>
                <li><a href="#">Notification</a></li>
                <li><a href="profile.jsp">Profile</a></li>
            </ul>
        </nav>
        <table>
            <thead>
                <tr>
                    <th>Record ID</th>
                    <th>Equipment Name</th>
                    <th>Quantity</th>
                    <th>Borrow Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="record" items="${borrowingRecords}">
                    <tr>
                        <td>${record.recordID}</td>
                        <td>${record.equipmentNames}</td>
                        <td>${record.totalQuantity}</td>
                        <td><fmt:formatDate value="${record.borrowDate}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${record.returnDate != null}">
                                    <fmt:formatDate value="${record.returnDate}" pattern="yyyy-MM-dd"/>
                                </c:when>
                                <c:otherwise>
                                    Not Returned
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${record.status}</td>
                        <td>
                            <c:if test="${record.status eq 'success'}">
                                <form action="/BorrowingController" method="post">
                                    <input type="hidden" name="action" value="returnEquipment"/>
                                    <input type="hidden" name="recordID" value="${record.recordID}"/>
                                    <button type="submit">Return Equipment</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
