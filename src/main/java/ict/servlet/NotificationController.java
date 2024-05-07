package ict.servlet;

import ict.bean.NotificationBean;
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

@WebServlet(name = "NotificationController", urlPatterns = {"/NotificationController"})
public class NotificationController extends HttpServlet {

    private AsmDB db;

    public void init() {
        String url = getServletContext().getInitParameter("dbUrl");
        String user = getServletContext().getInitParameter("dbUser");
        String password = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(url, user, password);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");

        String dashboardURL = getDashboardURL(request);
        request.setAttribute("dashboardURL", dashboardURL);
        try {
            List<NotificationBean> notifications = db.getNotifications(user.getUserID());
            request.setAttribute("notifications", notifications);
            request.getRequestDispatcher("/userNotifications.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error retrieving notifications.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userBean");
        String action = request.getParameter("action");

        try {
            if ("markAllRead".equals(action)) {
                if (db.markAllNotificationsAsRead(user.getUserID())) {
                    request.setAttribute("message", "All notifications marked as read.");
                }
                response.sendRedirect("NotificationController");
            } else if ("markRead".equals(action)) {
                int notificationID = Integer.parseInt(request.getParameter("notificationID"));
                if (db.markAsRead(notificationID)) {
                    request.setAttribute("message", "Notification marked as read.");
                }
                response.sendRedirect("NotificationController");
            } else {

                response.sendRedirect("errorPage.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error when marking notifications as read.");
        }

    }

    private String getDashboardURL(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserBean user = (UserBean) session.getAttribute("userBean");
            if (user != null) {
                switch (user.getRole()) {
                    case "AdminTechnician":
                        return "/AdminController";
                    case "Technician":
                        return "/TechDashboard";
                    case "Courier":
                        return "/CourierControllor";
                    case "Staff":
                        return "/StaffDashboard";
                    default:
                        return "/UserDashboard";
                }
            }
        }
        return "";
    }

}
