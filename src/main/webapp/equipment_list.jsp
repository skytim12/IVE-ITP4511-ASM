<%-- 
    Document   : equipment_list
    Created on : 2024年4月8日, 上午6:32:22
    Author     : Soman 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Equipment List</title>
    </head>
    <body>
        <h1>Equipment List</h1>
        <ul>
            <ict:errorMessage message="An error occurred while fetching equipment data." />
            <ict:equipmentListTag />
        </ul>
    </body>
</html>
