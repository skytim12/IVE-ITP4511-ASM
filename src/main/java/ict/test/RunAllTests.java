/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;

/**
 *
 * @author Soman
 */
public class RunAllTests {

    public static void main(String[] args) {
        try {

            createdb.main(args);

            createTable.main(args);
            
            TestAddCampus.main(args);

            TestAddUser.main(args);

            TestAddEquipment.main(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
