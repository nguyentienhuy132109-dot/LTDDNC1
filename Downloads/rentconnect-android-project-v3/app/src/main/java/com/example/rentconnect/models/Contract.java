package com.example.rentconnect.models;

public class Contract {
    private int id;
    private int roomId;
    private int landlordId;
    private int tenantId;
    private String roomTitle;
    private String startDate;
    private String endDate;
    private double rentPrice;
    private double deposit;
    private String paymentCycle;
    private int invoiceDay;
    private String terms;
    private String status; // DRAFT, ACTIVE

    public Contract(int id, int roomId, int landlordId, int tenantId, String roomTitle,
                    String startDate, String endDate, double rentPrice, double deposit,
                    String paymentCycle, int invoiceDay, String terms, String status) {
        this.id = id;
        this.roomId = roomId;
        this.landlordId = landlordId;
        this.tenantId = tenantId;
        this.roomTitle = roomTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentPrice = rentPrice;
        this.deposit = deposit;
        this.paymentCycle = paymentCycle;
        this.invoiceDay = invoiceDay;
        this.terms = terms;
        this.status = status;
    }

    public int getId() { return id; }
    public int getRoomId() { return roomId; }
    public int getLandlordId() { return landlordId; }
    public int getTenantId() { return tenantId; }
    public String getRoomTitle() { return roomTitle; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public double getRentPrice() { return rentPrice; }
    public double getDeposit() { return deposit; }
    public String getPaymentCycle() { return paymentCycle; }
    public int getInvoiceDay() { return invoiceDay; }
    public String getTerms() { return terms; }
    public String getStatus() { return status; }
}
