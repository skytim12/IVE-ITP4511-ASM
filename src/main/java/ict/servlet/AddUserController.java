package ict.servlet;

import ict.bean.UserBean;
import ict.db.AsmDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddUserController", urlPatterns = {"/AddUserController"})
public class AddUserController extends HttpServlet {

    private AsmDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addUser".equals(action)) {
            addUser(request, response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
        UserBean user = new UserBean();
        user.setUserID(request.getParameter("userID"));
        user.setUsername(request.getParameter("username"));
        String hashedPassword = db.hashPassword(request.getParameter("password"));
        user.setPassword(hashedPassword);
        user.setFullName(request.getParameter("fullName"));
        user.setRole(request.getParameter("role"));
        user.setCampusName(request.getParameter("campusName"));

        try {

            boolean success = db.addUser(user);
            if (success) {
                request.setAttribute("successMessage", "User added successfully.");
                request.getRequestDispatcher("/userAddedSuccess.jsp").forward(request, response); // Redirect to a success page
            } else {
                request.setAttribute("errorMessage", "Failed to add user.");
                request.getRequestDispatcher("/userAddedFail.jsp").forward(request, response); // Redirect to a failure page
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/userAddedFail.jsp").forward(request, response);
        }
    }
}
