/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.UserBean;
import ict.db.AsmDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Soman
 */
@WebServlet(name = "LoginController", urlPatterns = {"/main"})
public class LoginController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new AsmDB(dbUrl, dbUser, dbPassword);  // Initialize AsmDB with context parameters
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("authenticate".equals(action)) {
            doAuthenticate(request, response);
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthenticated(request)) {
            response.sendRedirect(getTargetURL(((UserBean) request.getSession().getAttribute("userBean")).getRole()));
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (db.isValidUser(username, password)) {
                UserBean user = db.getUser(username, password);
                if (user != null) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userBean", user);
                    response.sendRedirect(getTargetURL(user.getRole()));
                } else {
                    request.setAttribute("loginError", "User does not exist.");
                    doLogin(request, response);
                }
            } else {
                request.setAttribute("loginError", "Invalid username or password.");
                doLogin(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during authentication", e);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userBean") != null;
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
        rd.forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }

    private String getTargetURL(String role) {
        switch (role) {
            case "Admin":
                return "/admin_dashboard.jsp";
            case "Technician":
                return "/technician_dashboard.jsp";
            case "Courier":
                return "/courier_dashboard.jsp";
            case "Staff":
                return "/staff_dashboard.jsp";
            default:
                return "/user_dashboard.jsp";
        }
    }
}
