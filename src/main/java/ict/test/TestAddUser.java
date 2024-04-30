/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;

import ict.bean.UserBean;
import ict.db.AsmDB;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Soman
 */
public class TestAddUser {

    public static void main(String[] args) throws IOException {
        String url = "jdbc:mysql://localhost:3306/itp4511_asm";
        String username = "root";
        String password = "";

        AsmDB asmDB = new AsmDB(url, username, password);

        // Adding a GeneralUser
        UserBean generalUser = createUser("220014863", "Soman", "123456", "GeneralUser", "Soaman Tsang", "Sha Tin");
        addUser(asmDB, generalUser);

        // Adding a Technician
        UserBean technician = createUser("220014864", "Tech", "password123", "Technician", "Technician 1", "Chai Wan");
        addUser(asmDB, technician);

        // Adding a Courier
        UserBean courier = createUser("220014865", "Courier", "password456", "Courier", "Courier 1", "Lee Wai Lee");
        addUser(asmDB, courier);

        // Adding a Staff
        UserBean staff = createUser("220014866", "Staff", "password789", "Staff", "Staff 1", "Tuen Mun");
        addUser(asmDB, staff);

        // Adding an AdminTechnician
        UserBean adminTechnician = createUser("220014867", "Admin", "admin123", "AdminTechnician", "Admin Technician 1", "Tsing Yi");
        addUser(asmDB, adminTechnician);
    }

    private static UserBean createUser(String userID, String username, String password, String role, String fullName, String campusName) {
        UserBean user = new UserBean();
        user.setUserID(userID);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setFullName(fullName);
        user.setCampusName(campusName);
        return user;
    }

    private static void addUser(AsmDB asmDB, UserBean user) {
        try {
            boolean success = asmDB.addUser(user);
            if (success) {
                System.out.println("User added successfully: " + user.getUsername());
            } else {
                System.out.println("Failed to add user: " + user.getUsername());
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
