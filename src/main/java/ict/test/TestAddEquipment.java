package ict.test;

import ict.bean.EquipmentBean;
import ict.db.AsmDB;
import java.io.IOException;
import java.sql.SQLException;

public class TestAddEquipment {

    public static void main(String[] args) throws IOException {
        String url = "jdbc:mysql://localhost:3306/itp4511_asm";
        String username = "root";
        String password = "";

        AsmDB asmDB = new AsmDB(url, username, password);
        try {
            String[] equipmentTypes = {"Laptop", "Desktop", "Printer", "Projector", "Scanner"};

            for (int i = 0; i < 30; i++) {
                String equipmentID = asmDB.generateUniqueEquipmentID();

                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentID(equipmentID);

                int typeIndex = (int) (Math.random() * equipmentTypes.length);
                String equipmentType = equipmentTypes[typeIndex];
                equipment.setName(equipmentType);

                String description = "";
                switch (equipmentType) {
                    case "Laptop":
                        description = "Brand new laptop";
                        break;
                    case "Desktop":
                        description = "High-performance desktop computer";
                        break;
                    case "Printer":
                        description = "Color laser printer";
                        break;
                    case "Projector":
                        description = "HD projector with HDMI input";
                        break;
                    case "Scanner":
                        description = "Flatbed scanner with document feeder";
                        break;
                }
                equipment.setDescription(description);

                
                int randomAvailable = (int) (Math.random() * 2); 
                equipment.setAvailable(randomAvailable == 1 ? "Yes" : "No");

                String[] campusNames = {"Chai Wan", "Lee Wai Lee", "Sha Tin", "Tuen Mun", "Tsing Yi"};
                int campusIndex = (int) (Math.random() * campusNames.length);
                equipment.setCampusName(campusNames[campusIndex]);

                String[] conditions = {"New", "Good", "Fair", "Poor", "Out of Service"};
                int conditionIndex = (int) (Math.random() * conditions.length);
                equipment.setCondition(conditions[conditionIndex]);
                
                int randomExclusive = (int) (Math.random() * 2); 
                equipment.setExclusiveForStaff(randomExclusive == 1 ? "Yes" : "No");

                boolean success = asmDB.addEquipment(equipment);

                if (success) {
                    System.out.println("Equipment added successfully.");
                } else {
                    System.out.println("Failed to add equipment.");
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
