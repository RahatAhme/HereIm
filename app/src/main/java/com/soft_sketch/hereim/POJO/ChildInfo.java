package com.soft_sketch.hereim.POJO;

public class ChildInfo {
    private String childID;
    private String childName;
    private String childNumber;
    private boolean vibrationCode;
    private boolean recodingCode;
    private double currentLocLati;
    private double currentLocLong;
    private double fencingLati;
    private double felcingLong;

    public ChildInfo() {
    }

    public ChildInfo(String childID, String childName, String childNumber, boolean vibrationCode, boolean recodingCode, double currentLocLati, double currentLocLong, double fencingLati, double felcingLong) {
        this.childID = childID;
        this.childName = childName;
        this.childNumber = childNumber;
        this.vibrationCode = vibrationCode;
        this.recodingCode = recodingCode;
        this.currentLocLati = currentLocLati;
        this.currentLocLong = currentLocLong;
        this.fencingLati = fencingLati;
        this.felcingLong = felcingLong;
    }

    public String getChildID() {
        return childID;
    }

    public String getChildName() {
        return childName;
    }

    public String getChildNumber() {
        return childNumber;
    }

    public boolean getVibrationCode() {
        return vibrationCode;
    }

    public boolean getRecodingCode() {
        return recodingCode;
    }

    public double getCurrentLocLati() {
        return currentLocLati;
    }

    public double getCurrentLocLong() {
        return currentLocLong;
    }

    public double getFencingLati() {
        return fencingLati;
    }

    public double getFelcingLong() {
        return felcingLong;
    }
}
