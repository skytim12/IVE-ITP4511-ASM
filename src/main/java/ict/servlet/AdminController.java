package ict.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "logout":
                    handleLogout(request, response);
                    break;
                default:
                    response.sendRedirect("admin_dashboard.jsp");
                    break;
            }
        } else {
            // Default action can redirect to the dashboard or handle other general admin cases
            response.sendRedirect("admin_dashboard.jsp");
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Get session if it exists
        if (session != null) {
            session.invalidate(); // Invalidate session if exists
        }
        response.sendRedirect("login.jsp"); // Redirect to login page after logout
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Post request handling if necessary, for now, just redirect to doGet
        doGet(request, response);
    }
}
