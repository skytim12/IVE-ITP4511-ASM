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
    private String name;
    private String description;
    private String available;
    private String campus;
    private String condition;
    private int TotalQuantity;

    public EquipmentBean() {  
    }

    public EquipmentBean(String equipmentID, String name, String description, String available, String campus, String condition) {
        this.equipmentID = equipmentID;
        this.name = name;
        this.description = description;
        this.available = available;
        this.campus = campus;
        this.condition = condition;
    }



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
    
     public int getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(int TotalQuantity) {
        this.TotalQuantity = TotalQuantity;
    }
}
