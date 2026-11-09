package com.example.rentconnect.models;

public class Room {
    private int id;
    private int landlordId;
    private String title;
    private String addressCity;
    private String addressDetail;
    private double area;
    private int capacity;
    private double price;
    private double deposit;
    private double elecPrice;
    private double waterPrice;
    private double serviceFee;
    private String amenities;
    private String status; // AVAILABLE, RENTED, PENDING
    private String imageUrl;

    public Room(int id, int landlordId, String title, String addressCity, String addressDetail,
                double area, int capacity, double price, double deposit, double elecPrice,
                double waterPrice, double serviceFee, String amenities, String status, String imageUrl) {
        this.id = id;
        this.landlordId = landlordId;
        this.title = title;
        this.addressCity = addressCity;
        this.addressDetail = addressDetail;
        this.area = area;
        this.capacity = capacity;
        this.price = price;
        this.deposit = deposit;
        this.elecPrice = elecPrice;
        this.waterPrice = waterPrice;
        this.serviceFee = serviceFee;
        this.amenities = amenities;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public int getLandlordId() { return landlordId; }
    public String getTitle() { return title; }
    public String getAddressCity() { return addressCity; }
    public String getAddressDetail() { return addressDetail; }
    public double getArea() { return area; }
    public int getCapacity() { return capacity; }
    public double getPrice() { return price; }
    public double getDeposit() { return deposit; }
    public double getElecPrice() { return elecPrice; }
    public double getWaterPrice() { return waterPrice; }
    public double getServiceFee() { return serviceFee; }
    public String getAmenities() { return amenities; }
    public String getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }
}
