/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;

import ict.db.AsmDB;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Soman
 */
public class TestAddCampus {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/itp4511_asm";
        String username = "root";
        String password = "";

        AsmDB asmDB = new AsmDB(url, username, password);

        String[][] campusData = {
            {"Tsing Yi", "Tsing Yi Road, Tsing Yi Island, New Territories"},
            {"Tuen Mun", "Tsing Wun Road, Tuen Mun, New Territories"},
            {"Sha Tin", "Yuen Wo Road, Sha Tin, New Territories"},
            {"Lee Wai Lee", "King Ling Road, Tseung Kwan O, New Territories"},
            {"Chai Wan", "Shing Tai Road, Chai Wan, Hong Kong"}
        };

        try {

            for (String[] campus : campusData) {
                boolean success = asmDB.addCampus(campus[0], campus[1]);
                if (success) {
                    System.out.println("Campus '" + campus[0] + "' added successfully.");
                } else {
                    System.out.println("Failed to add campus '" + campus[0] + "'.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error adding campuses: " + e.getMessage());
        }
    }
}
