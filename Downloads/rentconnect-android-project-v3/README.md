# RentConnect - Nền Tảng Hỗ Trợ Người Thuê và Cho Thuê Nhà (Android Native - Java)

Dự án Android Studio được nâng cấp toàn diện dựa trên **Bài Tập Lớn RentConnect** (`AMP-DDNC_BTL_2609_G18_Chuong3_hoan_thien.docx`) và giáo trình **Lập Trình Di Động** (`LẬP TRÌNH DI ĐỘNG.docx`).

---

## 🌟 TÍNH NĂNG NỔI BẬT V3: PHÂN TÁCH GIAO DIỆN TENANT & LANDLORD HOÀN CHỈNH

Dự án đã tách biệt **2 giao diện hoàn toàn riêng biệt** phục vụ trải nghiệm người dùng tối ưu theo vai trò:

### 1. 🏢 Giao Diện Cho Chủ Trọ (Landlord Interface - `LandlordMainActivity`):
- **Bảng Quản Lý Dashboard**: Theo dõi danh sách phòng trọ, trạng thái phòng (**Còn trống / Đã thuê**).
- **[BM01 / FR02] Đăng Tin Phòng Trọ Mới (`AddRoomActivity`)**: Khai báo tiêu đề, địa chỉ, diện tích, sức chứa, giá thuê, tiền cọc, đơn giá điện/nước, phí dịch vụ chung và tiện ích.
- **[BM02 / FR04] Quản Lý Lịch Hẹn Xem Phòng (`LandlordBookingActivity`)**: Tiếp nhận danh sách yêu cầu đặt lịch xem phòng từ Người thuê, xác nhận (Confirm) hoặc từ chối (Cancel).
- **[BM04 / FR07] Chốt Số Điện Nước Ngoại Tuyến (`UtilityRecordActivity`)**: Nhập chỉ số điện/nước cũ & mới, chụp ảnh công tơ qua **Camera Intent**. Lưu trữ **Offline Storage (SQLite)** giúp làm việc tại vùng mất sóng.
- **[BM05 / FR08, FR09] Duyệt Hóa Đơn & Đối Soát VietQR (`LandlordInvoiceActivity`)**: Hệ thống tự động tính tổng tiền (Tiền phòng + Điện + Nước + Dịch vụ), Chủ trọ rà soát và nhấn **Xác nhận đã nhận tiền**.
- **[BM06 / FR11] Xử Lý Sự Cố Ticket (`LandlordTicketActivity`)**: Nhận phiếu báo hỏng từ Người thuê, cập nhật trạng thái từ `PENDING` -> `IN_PROGRESS` -> `RESOLVED`.

---

### 2. 🏠 Giao Diện Cho Người Thuê (Tenant Interface - `TenantMainActivity`):
- **[FR03] Danh Sách & Tìm Kiếm Phòng Trọ**: Hiển thị phòng trọ dưới dạng thẻ CardView.
- **[FR03] Xem Chi Tiết Phòng Trọ (`RoomDetailActivity`)**: **Nhấn vào bất kỳ phòng trọ hoặc Tag trạng thái** để mở trang chi tiết hiển thị đầy đủ thông số (tiêu đề, giá, cọc, diện tích, sức chứa, điện nước, tiện ích).
- **[BM02 / FR04] Đặt Lịch Xem Phòng (`BookingActivity`)**: Chọn ngày xem, khung giờ, SĐT liên hệ và gửi ghi chú cho chủ trọ.
- **[BM03 / FR06] Xem & Xác Nhận Hợp Đồng Số (`TenantContractActivity`)**: Rà soát các điều khoản thuê nhà, nhấn **Xác nhận hợp đồng** để kích hoạt trạng thái **Chỉ đọc (Read-Only)** bảo vệ tính pháp lý.
- **[BM05 / FR08, FR09] Hóa Đơn & Thanh Toán VietQR (`TenantInvoiceActivity`)**: Xem bảng kê chi tiết hóa đơn hàng tháng và quét/xem chuỗi mã **VietQR 1-touch** để thanh toán chuyển khoản nhanh.
- **[BM06 / FR10] Báo Cáo Sự Cố Khẩn Cấp (`TenantTicketActivity`)**: Chọn loại sự cố (Điện, Nước, Nội thất...), mức độ ưu tiên (Bình thường, Gấp, Rất gấp), đính kèm ảnh hiện trường.
- **Tích Hợp Cảm Biến Hardware**:
  - **Cảm biến ánh sáng (`Sensor.TYPE_LIGHT`)**: Nhận diện độ sáng phòng trọ để tự động điều chỉnh Dark/Light Theme khi đọc hợp đồng.
  - **Cảm biến gia tốc (`Sensor.TYPE_ACCELEROMETER`)**: Cử chỉ **Lắc thiết bị (Shake)** để tự động làm mới danh sách phòng và mở nhanh cửa sổ báo cáo sự cố khẩn cấp.

---

## 📂 2. CẤU TRÚC MÃ NGUỒN CHUẨN ANDROID STUDIO

```text
RentConnect/
├── .gitignore
├── build.gradle
├── settings.gradle
├── README.md
└── app/
    ├── build.gradle
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/example/rentconnect/
        │   ├── activities/
        │   │   ├── LoginActivity.java            # [FR01] Đăng nhập Email/Pass & Vân tay/FaceID -> Phân luồng Role
        │   │   ├── RegisterActivity.java         # [FR01] Đăng ký & Chọn Role (Tenant / Landlord)
        │   │   ├── TenantMainActivity.java       # Giao diện chính Người thuê (Tích hợp Sensors)
        │   │   ├── LandlordMainActivity.java     # Giao diện chính Chủ trọ (Dashboard Quản lý)
        │   │   ├── RoomDetailActivity.java       # Chi tiết thông số phòng trọ khi nhấn vào Tag/Card
        │   │   ├── BookingActivity.java          # [BM02 / FR04] Đặt lịch xem phòng
        │   │   ├── AddRoomActivity.java          # [BM01 / FR02] Đăng tin phòng trọ mới
        │   │   ├── TenantContractActivity.java   # [BM03 / FR06] Xem & Xác nhận hợp đồng
        │   │   ├── UtilityRecordActivity.java    # [BM04 / FR07] Chốt điện nước Offline & Chụp công tơ
        │   │   ├── TenantInvoiceActivity.java    # [BM05 / FR08, FR09] Xem hóa đơn & Thanh toán VietQR
        │   │   ├── LandlordInvoiceActivity.java  # [BM05 / FR08, FR09] Duyệt hóa đơn & Xác nhận nhận tiền
        │   │   ├── TenantTicketActivity.java     # [BM06 / FR10] Gửi báo cáo sự cố khẩn cấp
        │   │   ├── LandlordTicketActivity.java   # [FR11] Tiếp nhận & Xử lý sự cố Ticket
        │   │   └── LandlordBookingActivity.java  # Duyệt lịch hẹn xem phòng
        │   ├── adapters/
        │   │   ├── RoomAdapter.java              # Adapter phòng trọ (Sự kiện click Tag/Card -> RoomDetail)
        │   │   ├── BookingAdapter.java           # Adapter lịch hẹn
        │   │   ├── InvoiceAdapter.java           # Adapter hóa đơn & VietQR
        │   │   └── TicketAdapter.java            # Adapter sự cố
        │   ├── database/
        │   │   └── RentConnectDbHelper.java      # CSDL SQLite (bảng users, rooms, bookings, contracts, utility, invoices, tickets)
        │   ├── models/                           # Các lớp đối tượng DTO
        │   └── utils/
        │       ├── SharedPrefManager.java        # Lưu phiên làm việc & Role
        │       ├── SensorHelper.java             # Quản lý Cảm biến Ánh sáng & Gia tốc
        │       └── VietQRHelper.java             # Sinh mã VietQR
        └── res/
            ├── layout/                           # Các file giao diện XML
            └── values/                           # colors, strings, arrays, themes
```

---

## 🚀 3. HƯỚNG DẪN PUSH CODE LÊN GITHUB

Mở Terminal tại thư mục `RentConnect` sau khi giải nén và thực hiện:

```bash
git init
git add .
git commit -m "Feat: Completed RentConnect v3 with dedicated Tenant & Landlord interfaces, Room Detail tag click, and 11 FRs"
git branch -M main
git remote add origin git@github.com:YOUR_USERNAME/RentConnect-Android.git
git push -u origin main
```
