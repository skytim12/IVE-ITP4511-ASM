/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Soman
 */
public class DamageReportsBean {

    private int reportID;
    private int equipmentID;
    private String reportedBy;
    private java.sql.Timestamp reportDate;
    private String description;
    private String status;

    public DamageReportsBean() {
    }

    public DamageReportsBean(int reportID, int equipmentID, String reportedBy, java.sql.Timestamp reportDate, String description, String status) {
        this.reportID = reportID;
        this.equipmentID = equipmentID;
        this.reportedBy = reportedBy;
        this.reportDate = reportDate;
        this.description = description;
        this.status = status;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public java.sql.Timestamp getReportDate() {
        return reportDate;
    }

    public void setReportDate(java.sql.Timestamp reportDate) {
        this.reportDate = reportDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
