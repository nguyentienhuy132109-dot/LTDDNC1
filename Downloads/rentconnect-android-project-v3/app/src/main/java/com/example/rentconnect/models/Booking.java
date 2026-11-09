package com.example.rentconnect.models;

public class Booking {
    private int id;
    private int roomId;
    private String roomTitle;
    private int tenantId;
    private String bookingDate;
    private String timeSlot;
    private String tenantPhone;
    private String notes;
    private String status; // PENDING, CONFIRMED, CANCELLED

    // Constructor chuẩn 9 tham số khớp 100% với RentConnectDbHelper
    public Booking(int id, int roomId, String roomTitle, int tenantId, String bookingDate, String timeSlot, String tenantPhone, String notes, String status) {
        this.id = id;
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.tenantId = tenantId;
        this.bookingDate = bookingDate;
        this.timeSlot = timeSlot;
        this.tenantPhone = tenantPhone;
        this.notes = notes;
        this.status = status;
    }

    public int getId() { return id; }
    public int getRoomId() { return roomId; }
    public String getRoomTitle() { return roomTitle != null ? roomTitle : "Phòng #" + roomId; }
    public int getTenantId() { return tenantId; }
    public String getBookingDate() { return bookingDate; }
    public String getTimeSlot() { return timeSlot; }
    public String getTenantPhone() { return tenantPhone; }
    public String getNotes() { return notes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}