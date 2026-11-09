package com.example.rentconnect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rentconnect.models.*;

import java.util.ArrayList;
import java.util.List;

public class RentConnectDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rentconnect.db";
    private static final int DATABASE_VERSION = 4; // Đã nâng version để nạp lại DB chuẩn

    public RentConnectDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "full_name TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "phone TEXT, " +
                "role TEXT)");

        db.execSQL("CREATE TABLE rooms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "landlord_id INTEGER, " +
                "title TEXT, " +
                "address_city TEXT, " +
                "address_detail TEXT, " +
                "area REAL, " +
                "capacity INTEGER, " +
                "price REAL, " +
                "deposit REAL, " +
                "elec_price REAL, " +
                "water_price REAL, " +
                "service_fee REAL, " +
                "amenities TEXT, " +
                "status TEXT, " +
                "image_url TEXT)");

        db.execSQL("CREATE TABLE bookings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "room_id INTEGER, " +
                "tenant_id INTEGER, " +
                "booking_date TEXT, " +
                "time_slot TEXT, " +
                "phone TEXT, " +
                "note TEXT, " +
                "status TEXT)");

        db.execSQL("CREATE TABLE contracts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "room_id INTEGER, " +
                "landlord_id INTEGER, " +
                "tenant_id INTEGER, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "rent_price REAL, " +
                "deposit REAL, " +
                "payment_cycle TEXT, " +
                "invoice_day INTEGER, " +
                "terms TEXT, " +
                "status TEXT)");

        db.execSQL("CREATE TABLE utility_records (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "room_id INTEGER, " +
                "month_year TEXT, " +
                "old_electric REAL, " +
                "new_electric REAL, " +
                "old_water REAL, " +
                "new_water REAL, " +
                "elec_photo TEXT, " +
                "water_photo TEXT, " +
                "is_synced INTEGER)");

        db.execSQL("CREATE TABLE invoices (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "room_id INTEGER, " +
                "tenant_id INTEGER, " +
                "month_year TEXT, " +
                "room_fee REAL, " +
                "elec_fee REAL, " +
                "water_fee REAL, " +
                "service_fee REAL, " +
                "extra_fee REAL, " +
                "extra_reason TEXT, " +
                "total_amount REAL, " +
                "qr_content TEXT, " +
                "status TEXT)");

        db.execSQL("CREATE TABLE tickets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "room_id INTEGER, " +
                "tenant_id INTEGER, " +
                "category TEXT, " +
                "priority TEXT, " +
                "description TEXT, " +
                "image_path TEXT, " +
                "status TEXT)");

        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS bookings");
        db.execSQL("DROP TABLE IF EXISTS contracts");
        db.execSQL("DROP TABLE IF EXISTS utility_records");
        db.execSQL("DROP TABLE IF EXISTS invoices");
        db.execSQL("DROP TABLE IF EXISTS tickets");
        onCreate(db);
    }

    private void seedData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO users VALUES (1, 'Bùi Văn Trung', 'landlord@rentconnect.vn', '123456', '0988123456', 'Landlord')");
        db.execSQL("INSERT INTO users VALUES (2, 'Nguyễn Tiến Huy', 'tenant@rentconnect.vn', '123456', '0977654321', 'Tenant')");

        db.execSQL("INSERT INTO rooms VALUES (1, 1, 'Phòng 101 - Khép Kín Cao Cấp', 'Hà Nội', 'Số 15 Ngõ 123 Cầu Giấy', 28.5, 2, 3500000, 3500000, 3800, 25000, 100000, 'Điều hòa, Nóng lạnh, Wifi, Thang máy, Khép kín', 'RENTED', 'room101.jpg')");
        db.execSQL("INSERT INTO rooms VALUES (2, 1, 'Phòng 202 - Ban Công Thoáng Mát', 'Hà Nội', 'Số 15 Ngõ 123 Cầu Giấy', 32.0, 3, 4200000, 4200000, 3800, 25000, 100000, 'Điều hòa, Nóng lạnh, Máy giặt, Gác xép, Ban công', 'AVAILABLE', 'room202.jpg')");
        db.execSQL("INSERT INTO rooms VALUES (3, 1, 'Phòng 301 - Studio Đầy Đủ Nội Thất', 'Hà Nội', 'Số 8 Ngách 45 Lê Thanh Nghị', 25.0, 2, 3800000, 3800000, 4000, 30000, 120000, 'Full nội thất, Giường tủ, Điều hòa, Bếp ăn riêng', 'AVAILABLE', 'room301.jpg')");

        db.execSQL("INSERT INTO bookings VALUES (1, 2, 2, '2026-11-05', '14:30 - 15:30', '0977654321', 'Muốn xem phòng ban công tầng 2', 'PENDING')");

        db.execSQL("INSERT INTO contracts VALUES (1, 1, 1, 2, '01/10/2026', '01/10/2027', 3500000, 3500000, '1 tháng/lần', 5, 'Bên B thanh toán tiền nhà trước ngày 05 hằng tháng. Tiền điện 3.8k/kWh, nước 25k/m3.', 'ACTIVE')");

        db.execSQL("INSERT INTO utility_records VALUES (1, 1, '10/2026', 1250, 1380, 45, 52, 'elec_101.jpg', 'water_101.jpg', 1)");

        db.execSQL("INSERT INTO invoices VALUES (1, 1, 2, '10/2026', 3500000, 494000, 175000, 100000, 0, 'Không', 4269000, 'VIETQR|RentConnect|INV0001|AMT:4269000|ACC:1903456789', 'UNPAID')");

        db.execSQL("INSERT INTO tickets VALUES (1, 1, 2, 'Điện', 'Gấp', 'Hỏng công tắc đèn phòng khách và rò rỉ nước vòi hoa sen', 'ticket_001.jpg', 'PENDING')");
    }

    // AUTH METHODS
    public User checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    public boolean registerUser(String fullName, String email, String password, String phone, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("full_name", fullName);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("phone", phone);
        cv.put("role", role);
        return db.insert("users", null, cv) != -1;
    }

    // ROOM METHODS
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM rooms ORDER BY id DESC", null);
        while (c.moveToNext()) {
            list.add(new Room(
                    c.getInt(0), c.getInt(1), c.getString(2), c.getString(3),
                    c.getString(4), c.getDouble(5), c.getInt(6), c.getDouble(7),
                    c.getDouble(8), c.getDouble(9), c.getDouble(10), c.getDouble(11),
                    c.getString(12), c.getString(13), c.getString(14)
            ));
        }
        c.close();
        return list;
    }

    public Room getRoomById(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM rooms WHERE id=?", new String[]{String.valueOf(roomId)});
        if (c.moveToFirst()) {
            Room room = new Room(
                    c.getInt(0), c.getInt(1), c.getString(2), c.getString(3),
                    c.getString(4), c.getDouble(5), c.getInt(6), c.getDouble(7),
                    c.getDouble(8), c.getDouble(9), c.getDouble(10), c.getDouble(11),
                    c.getString(12), c.getString(13), c.getString(14)
            );
            c.close();
            return room;
        }
        c.close();
        return null;
    }

    public boolean insertRoom(int landlordId, String title, String city, String detail, double area,
                              int capacity, double price, double deposit, double elec, double water,
                              double service, String amenities, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("landlord_id", landlordId);
        cv.put("title", title);
        cv.put("address_city", city);
        cv.put("address_detail", detail);
        cv.put("area", area);
        cv.put("capacity", capacity);
        cv.put("price", price);
        cv.put("deposit", deposit);
        cv.put("elec_price", elec);
        cv.put("water_price", water);
        cv.put("service_fee", service);
        cv.put("amenities", amenities);
        cv.put("status", "AVAILABLE");
        cv.put("image_url", imageUrl);
        return db.insert("rooms", null, cv) != -1;
    }

    // BOOKING METHODS
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT b.id, b.room_id, r.title, b.tenant_id, b.booking_date, b.time_slot, b.phone, b.note, b.status " +
                "FROM bookings b " +
                "LEFT JOIN rooms r ON b.room_id = r.id " +
                "ORDER BY b.id DESC";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            list.add(new Booking(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8)
            ));
        }
        c.close();
        return list;
    }

    public List<Booking> getBookingsByTenant(int tenantId) {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT b.id, b.room_id, r.title, b.tenant_id, b.booking_date, b.time_slot, b.phone, b.note, b.status " +
                "FROM bookings b " +
                "LEFT JOIN rooms r ON b.room_id = r.id " +
                "WHERE b.tenant_id = ? ORDER BY b.id DESC";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(tenantId)});
        while (c.moveToNext()) {
            list.add(new Booking(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8)
            ));
        }
        c.close();
        return list;
    }

    public boolean insertBooking(int roomId, int tenantId, String date, String timeSlot, String phone, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("room_id", roomId);
        cv.put("tenant_id", tenantId);
        cv.put("booking_date", date);
        cv.put("time_slot", timeSlot);
        cv.put("phone", phone);
        cv.put("note", note);
        cv.put("status", "PENDING");
        return db.insert("bookings", null, cv) != -1;
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        return db.update("bookings", cv, "id=?", new String[]{String.valueOf(bookingId)}) > 0;
    }

    // CONTRACT METHODS
    public Contract getContractByTenant(int tenantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT c.*, r.title FROM contracts c JOIN rooms r ON c.room_id = r.id WHERE c.tenant_id=? ORDER BY c.id DESC LIMIT 1", new String[]{String.valueOf(tenantId)});
        if (c.moveToFirst()) {
            Contract contract = new Contract(
                    c.getInt(0),      // id
                    c.getInt(1),      // room_id
                    c.getInt(2),      // landlord_id
                    c.getInt(3),      // tenant_id
                    c.getString(12),  // roomTitle (từ JOIN r.title ở cột 12)
                    c.getString(4),   // start_date
                    c.getString(5),   // end_date
                    c.getDouble(6),   // rent_price
                    c.getDouble(7),   // deposit
                    c.getString(8),   // payment_cycle
                    c.getInt(9),      // invoice_day
                    c.getString(10),  // terms
                    c.getString(11)   // status
            );
            c.close();
            return contract;
        }
        c.close();
        return null;
    }

    public boolean insertContract(int roomId, int landlordId, int tenantId, String startDate, String endDate,
                                  double rent, double deposit, String cycle, int invoiceDay, String terms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("room_id", roomId);
        cv.put("landlord_id", landlordId);
        cv.put("tenant_id", tenantId);
        cv.put("start_date", startDate);
        cv.put("end_date", endDate);
        cv.put("rent_price", rent);
        cv.put("deposit", deposit);
        cv.put("payment_cycle", cycle);
        cv.put("invoice_day", invoiceDay);
        cv.put("terms", terms);
        cv.put("status", "DRAFT");
        return db.insert("contracts", null, cv) != -1;
    }

    public boolean confirmContract(int contractId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", "ACTIVE");
        return db.update("contracts", cv, "id=?", new String[]{String.valueOf(contractId)}) > 0;
    }

    // UTILITY METHODS
    public boolean insertUtilityRecord(int roomId, String monthYear, double oldE, double newE,
                                       double oldW, double newW, String ePhoto, String wPhoto, int isSynced) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("room_id", roomId);
        cv.put("month_year", monthYear);
        cv.put("old_electric", oldE);
        cv.put("new_electric", newE);
        cv.put("old_water", oldW);
        cv.put("new_water", newW);
        cv.put("elec_photo", ePhoto);
        cv.put("water_photo", wPhoto);
        cv.put("is_synced", isSynced);
        return db.insert("utility_records", null, cv) != -1;
    }

    public UtilityRecord getLatestUtility(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT u.*, r.title FROM utility_records u JOIN rooms r ON u.room_id = r.id WHERE u.room_id=? ORDER BY u.id DESC LIMIT 1", new String[]{String.valueOf(roomId)});
        if (c.moveToFirst()) {
            UtilityRecord record = new UtilityRecord(
                    c.getInt(0),      // id
                    c.getInt(1),      // room_id
                    c.getString(10),  // roomTitle (từ JOIN r.title ở cột 10)
                    c.getString(2),   // month_year
                    c.getDouble(3),   // old_electric
                    c.getDouble(4),   // new_electric
                    c.getDouble(5),   // old_water
                    c.getDouble(6),   // new_water
                    c.getString(7),   // elec_photo
                    c.getString(8),   // water_photo
                    c.getInt(9)       // is_synced
            );
            c.close();
            return record;
        }
        c.close();
        return null;
    }

    // INVOICE METHODS
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT i.*, r.title FROM invoices i JOIN rooms r ON i.room_id = r.id ORDER BY i.id DESC", null);
        while (c.moveToNext()) {
            list.add(new Invoice(
                    c.getInt(0),      // id
                    c.getInt(1),      // room_id
                    c.getInt(2),      // tenant_id
                    c.getString(13),  // roomTitle (từ JOIN r.title ở cột 13)
                    c.getString(3),   // month_year
                    c.getDouble(4),   // room_fee
                    c.getDouble(5),   // elec_fee
                    c.getDouble(6),   // water_fee
                    c.getDouble(7),   // service_fee
                    c.getDouble(8),   // extra_fee
                    c.getString(9),   // extra_reason
                    c.getDouble(10),  // total_amount
                    c.getString(11),  // qr_content
                    c.getString(12)   // status
            ));
        }
        c.close();
        return list;
    }

    public boolean updateInvoiceStatus(int invoiceId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        return db.update("invoices", cv, "id=?", new String[]{String.valueOf(invoiceId)}) > 0;
    }

    // TICKET METHODS
    public List<Ticket> getAllTickets() {
        List<Ticket> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT t.*, r.title FROM tickets t JOIN rooms r ON t.room_id = r.id ORDER BY t.id DESC", null);
        while (c.moveToNext()) {
            list.add(new Ticket(
                    c.getInt(0),      // id
                    c.getInt(1),      // room_id
                    c.getInt(2),      // tenant_id
                    c.getString(8),   // roomTitle (từ JOIN r.title ở cột 8)
                    c.getString(3),   // category
                    c.getString(4),   // priority
                    c.getString(5),   // description
                    c.getString(6),   // image_path
                    c.getString(7)    // status
            ));
        }
        c.close();
        return list;
    }

    public boolean createTicket(int tenantId, int roomId, String category, String priority, String desc, String imgPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tenant_id", tenantId);
        cv.put("room_id", roomId);
        cv.put("category", category);
        cv.put("priority", priority);
        cv.put("description", desc);
        cv.put("image_path", imgPath);
        cv.put("status", "PENDING");
        return db.insert("tickets", null, cv) != -1;
    }

    public boolean updateTicketStatus(int ticketId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        return db.update("tickets", cv, "id=?", new String[]{String.valueOf(ticketId)}) > 0;
    }
}