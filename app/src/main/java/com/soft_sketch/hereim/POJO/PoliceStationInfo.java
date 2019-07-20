package com.soft_sketch.hereim.POJO;

public class PoliceStationInfo {
    private String stationName;
    private String stationID;
    private String stationNumber;
    private double stationLati;
    private double stationLong;
    private int stationVibrationCode;

    public PoliceStationInfo() {
    }

    public PoliceStationInfo(String stationName, String stationID, String stationNumber, double stationLati, double stationLong, int stationVibrationCode) {
        this.stationName = stationName;
        this.stationID = stationID;
        this.stationNumber = stationNumber;
        this.stationLati = stationLati;
        this.stationLong = stationLong;
        this.stationVibrationCode = stationVibrationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationID() {
        return stationID;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public double getStationLati() {
        return stationLati;
    }

    public double getStationLong() {
        return stationLong;
    }

    public int getStationVibrationCode() {
        return stationVibrationCode;
    }
}
