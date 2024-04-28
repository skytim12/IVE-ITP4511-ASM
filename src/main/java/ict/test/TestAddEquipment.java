/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;

import ict.bean.EquipmentBean;
import ict.db.AsmDB;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Soman
 */
public class TestAddEquipment {

    public static void main(String[] args) throws IOException {
        String url = "jdbc:mysql://localhost:3306/itp4511_asm";
        String username = "root";
        String password = "";

        AsmDB asmDB = new AsmDB(url, username, password);
        try {
            String equipmentID = asmDB.generateUniqueEquipmentID();

            EquipmentBean equipment = new EquipmentBean();
            equipment.setEquipmentID(equipmentID);
            equipment.setName("Laptop");
            equipment.setDescription("Brand new laptop");
            equipment.setAvailable("Yes");
            equipment.setCampusName("Chai Wan");
            equipment.setCondition("New");

            boolean success = asmDB.addEquipment(equipment);

            if (success) {
                System.out.println("Equipment added successfully.");
            } else {
                System.out.println("Failed to add equipment.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
