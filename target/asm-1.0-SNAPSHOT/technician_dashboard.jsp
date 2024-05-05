<%-- 
    Document   : technician_dashboard
    Created on : 2024年4月28日, 下午2:53:27
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Technician Dashboard</title>
    </head>
    <body>
        <h1>Welcome, Technician!</h1>
        <nav>
            <ul>
                <li><a href="/InventoryController">Manage Inventory</a></li>
                <li><a href="/BookingController">Manage Bookings</a></li>
                <li><a href="/ArrangeDeliveryController">Arrange Delivery</a></li>
                <li><a href="/CheckInController">Check In</a></li>
                <li><a href="/TechController?action=reportDamage">Report Equipment Damage</a></li>
                <li><a href="/ProfileController">Profile</a></li>
                <li><a href="/TechController?action=logout">Logout</a></li>
            </ul>
        </nav>
    </body>
</html>