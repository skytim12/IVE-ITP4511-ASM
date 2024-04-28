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

    private int equipmentID;
    private String name;
    private String description;
    private int availableQuantity;
    private int totalQuantity;
    private String campus;
    private String condition;

    
    public EquipmentBean() {
    }

    public EquipmentBean(int equipmentID, String name, String description, int availableQuantity, int totalQuantity, String campus, String condition) {
        this.equipmentID = equipmentID;
        this.name = name;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.totalQuantity = totalQuantity;
        this.campus = campus;
        this.condition = condition;
    }


    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
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

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
