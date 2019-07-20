package com.soft_sketch.hereim.POJO;

import java.sql.Time;

public class GeneralDairy {
    private String gdID;
    private String gd_parentName;
    private String gd_childName;
    private Time gd_issueTime;
    private double gd_locationLati;
    private double gd_locationLong;


    public GeneralDairy() {
    }

    public GeneralDairy(String gdID, String gd_parentName, String gd_childName, Time gd_issueTime, double gd_locationLati, double gd_locationLong) {
        this.gdID = gdID;
        this.gd_parentName = gd_parentName;
        this.gd_childName = gd_childName;
        this.gd_issueTime = gd_issueTime;
        this.gd_locationLati = gd_locationLati;
        this.gd_locationLong = gd_locationLong;
    }

    public String getGdID() {
        return gdID;
    }

    public String getGd_parentName() {
        return gd_parentName;
    }

    public String getGd_childName() {
        return gd_childName;
    }

    public Time getGd_issueTime() {
        return gd_issueTime;
    }

    public double getGd_locationLati() {
        return gd_locationLati;
    }

    public double getGd_locationLong() {
        return gd_locationLong;
    }
}
