package com.example.rentconnect.models;

public class Ticket {
    private int id;
    private int roomId;
    private int tenantId;
    private String roomTitle;
    private String category;
    private String priority;
    private String description;
    private String imagePath;
    private String status; // PENDING, IN_PROGRESS, RESOLVED

    public Ticket(int id, int roomId, int tenantId, String roomTitle, String category,
                  String priority, String description, String imagePath, String status) {
        this.id = id;
        this.roomId = roomId;
        this.tenantId = tenantId;
        this.roomTitle = roomTitle;
        this.category = category;
        this.priority = priority;
        this.description = description;
        this.imagePath = imagePath;
        this.status = status;
    }

    public int getId() { return id; }
    public int getRoomId() { return roomId; }
    public int getTenantId() { return tenantId; }
    public String getRoomTitle() { return roomTitle; }
    public String getCategory() { return category; }
    public String getPriority() { return priority; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getStatus() { return status; }
}
