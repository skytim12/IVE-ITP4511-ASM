/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.NotificationBean;
import ict.bean.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ict.db.AsmDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Soman
 */
@WebServlet(name = "TechController", urlPatterns = {"/TechDashboard"})
public class TechController extends HttpServlet {

    private AsmDB db;

    public void init() throws ServletException {
        super.init();
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        try {
            List<NotificationBean> notifications = db.getNotifications(user.getUserID());
            request.setAttribute("notifications", notifications);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error fetching equipment data: " + e.getMessage());
        }
        request.getRequestDispatcher("/technician_dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        switch (action) {

            case "logout":
                doLogout(request, response);
                break;
            case "markAllRead": {
                try {
                    db.markAllNotificationsAsRead(user.getUserID());
                    request.setAttribute("message", "All notifications marked as read.");
                    response.sendRedirect("TechDashboard");
                } catch (SQLException ex) {
                    Logger.getLogger(TechController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "markRead":
                int notificationID = Integer.parseInt(request.getParameter("notificationID"));
                 {
                    try {
                        db.markAsRead(notificationID);
                        request.setAttribute("message", "Notification marked as read.");
                        response.sendRedirect("TechDashboard");
                    } catch (SQLException ex) {
                        Logger.getLogger(TechController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            default:
                response.sendRedirect("login.jsp");
                break;
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}
