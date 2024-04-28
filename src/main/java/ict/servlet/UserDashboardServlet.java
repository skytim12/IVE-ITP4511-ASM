/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import ict.db.AsmDB;
import ict.bean.EquipmentBean;
import ict.bean.ReservationBean;
import java.util.List;

/**
 *
 * @author Soman
 */
@WebServlet(name = "UserDashboardServlet", urlPatterns = {"/UserDashboard"})
public class UserDashboardServlet extends HttpServlet {

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
            List<EquipmentBean> equipmentList = db.fetchGroupedEquipment();
            request.setAttribute("equipmentList", equipmentList);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error fetching equipment data: " + e.getMessage());
        }
        request.getRequestDispatcher("/user_dashboard.jsp").forward(request, response);
    }

}
