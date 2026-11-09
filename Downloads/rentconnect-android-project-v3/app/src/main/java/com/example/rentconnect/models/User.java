package com.example.rentconnect.models;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String role; // Tenant or Landlord

    public User(int id, String fullName, String email, String password, String phone, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
}
