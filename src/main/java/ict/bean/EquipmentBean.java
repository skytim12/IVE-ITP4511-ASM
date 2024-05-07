/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Soman
 */
public class EquipmentBean {

    private String equipmentID;
    private String equipmentIDs;
    private String name;
    private String description;
    private String available;
    private String CampusName;
    private String condition;
    private int TotalQuantity;
    private String exclusiveForStaff;
    private String currentCampus;
    private String status;

    public EquipmentBean() {
    }

    public EquipmentBean(String equipmentID, String name, String description, String available, String CampusName, String condition) {
        this.equipmentID = equipmentID;
        this.name = name;
        this.description = description;
        this.available = available;
        this.CampusName = CampusName;
        this.condition = condition;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getEquipmentIDs() {
        return equipmentIDs;
    }

    public void setEquipmentIDs(String equipmentIDs) {
        this.equipmentIDs = equipmentIDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCampusName() {
        return CampusName;
    }

    public void setCampusName(String CampusName) {
        this.CampusName = CampusName;
    }

    public String getCurrentCampus() {
        return currentCampus;
    }

    public void setCurrentCampus(String currentCampus) {
        this.currentCampus = currentCampus;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(int TotalQuantity) {
        this.TotalQuantity = TotalQuantity;
    }

    public String getExclusiveForStaff() {
        return exclusiveForStaff;
    }

    public void setExclusiveForStaff(String exclusiveForStaff) {
        this.exclusiveForStaff = exclusiveForStaff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
