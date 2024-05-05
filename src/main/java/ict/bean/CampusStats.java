package ict.bean;

public class CampusStats {

    private String campusName;
    private int totalCheckOuts;

    // Constructors
    public CampusStats() {
    }

    public CampusStats(String campusName, int totalCheckOuts) {
        this.campusName = campusName;
        this.totalCheckOuts = totalCheckOuts;
    }

    // Getters and Setters
    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public int getTotalCheckOuts() {
        return totalCheckOuts;
    }

    public void setTotalCheckOuts(int totalCheckOuts) {
        this.totalCheckOuts = totalCheckOuts;
    }
}
