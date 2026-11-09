package com.example.rentconnect.models;

public class UtilityRecord {
    private int id;
    private int roomId;
    private String roomTitle;
    private String monthYear;
    private double oldElectric;
    private double newElectric;
    private double oldWater;
    private double newWater;
    private String elecPhoto;
    private String waterPhoto;
    private int isSynced;

    public UtilityRecord(int id, int roomId, String roomTitle, String monthYear,
                         double oldElectric, double newElectric, double oldWater, double newWater,
                         String elecPhoto, String waterPhoto, int isSynced) {
        this.id = id;
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.monthYear = monthYear;
        this.oldElectric = oldElectric;
        this.newElectric = newElectric;
        this.oldWater = oldWater;
        this.newWater = newWater;
        this.elecPhoto = elecPhoto;
        this.waterPhoto = waterPhoto;
        this.isSynced = isSynced;
    }

    public int getId() { return id; }
    public int getRoomId() { return roomId; }
    public String getRoomTitle() { return roomTitle; }
    public String getMonthYear() { return monthYear; }
    public double getOldElectric() { return oldElectric; }
    public double getNewElectric() { return newElectric; }
    public double getOldWater() { return oldWater; }
    public double getNewWater() { return newWater; }
    public String getElecPhoto() { return elecPhoto; }
    public String getWaterPhoto() { return waterPhoto; }
    public int getIsSynced() { return isSynced; }
}
