package ict.bean;

public class EquipmentStats {
    private String equipmentID;
    private String name;
    private String campusName;
    private String condition;
    private int checkOutCount;

    // Constructors
    public EquipmentStats() {
    }

    public EquipmentStats(String equipmentID, String name, String campusName, String condition, int checkOutCount) {
        this.equipmentID = equipmentID;
        this.name = name;
        this.campusName = campusName;
        this.condition = condition;
        this.checkOutCount = checkOutCount;
    }

    // Getters and Setters
    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getCheckOutCount() {
        return checkOutCount;
    }

    public void setCheckOutCount(int checkOutCount) {
        this.checkOutCount = checkOutCount;
    }
}
