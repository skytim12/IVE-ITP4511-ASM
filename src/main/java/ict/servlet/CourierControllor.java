/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.EquipmentBean;
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

/**
 *
 * @author Soman
 */
@WebServlet(name = "CourierControllor", urlPatterns = {"/CourierControllor"})
public class CourierControllor extends HttpServlet {

    private AsmDB db;

    public void init() throws ServletException {
        super.init();
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        db = new AsmDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EquipmentBean> equipmentList = db.UserfetchReservableEquipmentList();
            request.setAttribute("equipmentList", equipmentList);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error fetching equipment data: " + e.getMessage());
        }
        request.getRequestDispatcher("/courier_dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {

            case "logout":
                doLogout(request, response);
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
