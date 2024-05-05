/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.EquipmentStats;
import ict.bean.CampusStats;
import ict.bean.UserBean;
import ict.db.AsmDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CheckOutStatsController", urlPatterns = {"/CheckOutStatsController"})
public class CheckOutStatsController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        try {
            List<EquipmentStats> equipmentStats = db.getEquipmentStats(user.getCampusName());
            List<CampusStats> campusStats = db.getCampusStats(user.getCampusName());
            request.setAttribute("equipmentStats", equipmentStats);
            request.setAttribute("campusStats", campusStats);
            request.getRequestDispatcher("/check_out_stats.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/check_out_stats.jsp").forward(request, response);
        }
    }
}
