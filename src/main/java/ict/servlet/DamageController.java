package ict.servlet;

import ict.bean.DamageReportsBean;
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

@WebServlet(name = "DamageController", urlPatterns = {"/DamageController"})
public class DamageController extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("confirmDamage".equals(action)) {
                handleConfirmDamage(request);
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
        }
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        try {
            List<DamageReportsBean> damageReports = db.getAllDamageReports(user.getCampusName());
            request.setAttribute("damageReports", damageReports);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Error retrieving damage reports: " + ex.getMessage());
        }
        request.getRequestDispatcher("/damage_report_management.jsp").forward(request, response);
    }

    private void handleConfirmDamage(HttpServletRequest request) throws SQLException, IOException {
        String reportID = request.getParameter("reportID");
        String condition = request.getParameter("condition");
        String available = request.getParameter("available");
        String equipmentID = request.getParameter("equipmentID");

        boolean success = db.updateDamageReport(reportID, condition, available, equipmentID);

        if (!success) {
            request.setAttribute("errorMessage", "Failed to confirm damage.");
        } else {
            request.setAttribute("successMessage", "Damage confirmed and equipment updated successfully.");
        }
    }
}
