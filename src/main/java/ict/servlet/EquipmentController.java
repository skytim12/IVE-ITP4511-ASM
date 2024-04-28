/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ict.db.AsmDB;
import ict.bean.EquipmentBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Soman
 */
@WebServlet(name = "EquipmentController", urlPatterns = {"/EquipmentController"})
public class EquipmentController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        // Fetch database connection parameters from servlet context
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new AsmDB(dbUrl, dbUser, dbPassword);  // Initialize AsmDB with context parameters
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EquipmentBean> equipmentList = db.fetchEquipmentList();  
            request.setAttribute("equipmentList", equipmentList);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error fetching equipment data: " + e.getMessage());
        }
        request.getRequestDispatcher("/equipment_list.jsp").forward(request, response);
    }
}