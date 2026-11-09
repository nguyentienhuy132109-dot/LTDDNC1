package com.example.rentconnect.models;

public class Invoice {
    private int id;
    private int roomId;
    private int tenantId;
    private String roomTitle;
    private String monthYear;
    private double roomFee;
    private double elecFee;
    private double waterFee;
    private double serviceFee;
    private double extraFee;
    private String extraReason;
    private double totalAmount;
    private String qrContent;
    private String status; // UNPAID, PAID

    public Invoice(int id, int roomId, int tenantId, String roomTitle, String monthYear,
                   double roomFee, double elecFee, double waterFee, double serviceFee,
                   double extraFee, String extraReason, double totalAmount, String qrContent, String status) {
        this.id = id;
        this.roomId = roomId;
        this.tenantId = tenantId;
        this.roomTitle = roomTitle;
        this.monthYear = monthYear;
        this.roomFee = roomFee;
        this.elecFee = elecFee;
        this.waterFee = waterFee;
        this.serviceFee = serviceFee;
        this.extraFee = extraFee;
        this.extraReason = extraReason;
        this.totalAmount = totalAmount;
        this.qrContent = qrContent;
        this.status = status;
    }

    public int getId() { return id; }
    public int getRoomId() { return roomId; }
    public int getTenantId() { return tenantId; }
    public String getRoomTitle() { return roomTitle; }
    public String getMonthYear() { return monthYear; }
    public double getRoomFee() { return roomFee; }
    public double getElecFee() { return elecFee; }
    public double getWaterFee() { return waterFee; }
    public double getServiceFee() { return serviceFee; }
    public double getExtraFee() { return extraFee; }
    public String getExtraReason() { return extraReason; }
    public double getTotalAmount() { return totalAmount; }
    public String getQrContent() { return qrContent; }
    public String getStatus() { return status; }
}
