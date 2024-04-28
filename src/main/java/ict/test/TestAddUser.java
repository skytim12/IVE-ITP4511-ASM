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
        UserBean newUser = new UserBean();
        newUser.setUserID("220014863");
        newUser.setUsername("Soman");
        newUser.setPassword("123456");
        newUser.setRole("GeneralUser");
        newUser.setFullName("Soaman Tsang");
        newUser.setCampusName("Sha Tin");

        try {
           
            boolean success = asmDB.addUser(newUser);
            if (success) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

}
