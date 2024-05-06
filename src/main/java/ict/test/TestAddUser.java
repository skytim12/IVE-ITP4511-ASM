package ict.test;

import ict.bean.UserBean;
import ict.db.AsmDB;
import java.io.IOException;
import java.sql.SQLException;

public class TestAddUser {

    // Initialize a static variable to keep track of the user ID increment.
    private static int userCount = 0;

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/itp4511_asm";
        String username = "root";
        String password = "";

        AsmDB asmDB = new AsmDB(url, username, password);

        try {
            String[] campusesForGeneralUser = {"Sha Tin", "Lee Wai Lee", "Tuen Mun", "Tsing Yi", "Chai Wan"};
            String[] campusesForTechnician = {"Chai Wan", "Lee Wai Lee", "Sha Tin", "Tuen Mun", "Tsing Yi"};
            String[] campusesForCourier = {"Lee Wai Lee", "Tuen Mun", "Tsing Yi", "Chai Wan", "Sha Tin"};
            String[] campusesForStaff = {"Tuen Mun", "Tsing Yi", "Chai Wan", "Sha Tin", "Lee Wai Lee"};
            String[] campusesForAdminTechnician = {"Tsing Yi", "Chai Wan", "Lee Wai Lee", "Sha Tin", "Tuen Mun"};

            addAccountsForRole(asmDB, "GeneralUser", campusesForGeneralUser);
            addAccountsForRole(asmDB, "Technician", campusesForTechnician);
            addAccountsForRole(asmDB, "Courier", campusesForCourier);
            addAccountsForRole(asmDB, "Staff", campusesForStaff);
            addAccountsForRole(asmDB, "AdminTechnician", campusesForAdminTechnician);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addAccountsForRole(AsmDB asmDB, String role, String[] campuses) throws IOException, SQLException {
        for (int i = 0; i < campuses.length; i++) {
            UserBean user = createUser(asmDB, role, campuses[i]);
            addUser(asmDB, user);
        }
    }

    private static UserBean createUser(AsmDB asmDB, String role, String campus) throws IOException, SQLException {
        String userID = String.format("2200%06d", 2000 + userCount++); 
        String username = role.toLowerCase() + userCount;
        String password = "123456"; 
        String fullName = role + " " + userCount;

        UserBean user = new UserBean();
        user.setUserID(userID);
        user.setUsername(username);
        user.setPassword(asmDB.hashPassword(password));
        user.setRole(role);
        user.setFullName(fullName);
        user.setCampusName(campus);
        return user;
    }

    private static void addUser(AsmDB asmDB, UserBean user) throws SQLException, IOException {
        boolean success = asmDB.addUser(user);
        if (success) {
            System.out.println("User added successfully: " + user.getUsername());
        } else {
            System.out.println("Failed to add user: " + user.getUsername());
        }
    }
}
