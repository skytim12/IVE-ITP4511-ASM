package ict.bean;

import java.sql.Date;
import java.util.List;

/**
 * Bean class for handling borrowing records.
 */
public class BorrowingRecordsBean {

    private int recordID;
    private int reservationID;
    private Date borrowDate;
    private Date returnDate;
    private String status;
    private String equipmentNames;
    private int totalQuantity;
    private List<EquipmentBean> equipmentList;

    public BorrowingRecordsBean() {
    }

    public BorrowingRecordsBean(int recordID, int reservationID, Date borrowDate, Date returnDate, String status) {
        this.recordID = recordID;
        this.reservationID = reservationID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;

    }

    // Getters and Setters
    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEquipmentNames() {
        return equipmentNames;
    }

    public void setEquipmentNames(String equipmentNames) {
        this.equipmentNames = equipmentNames;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<EquipmentBean> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentBean> equipmentList) {
        this.equipmentList = equipmentList;
    }
}
