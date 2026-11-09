package com.example.rentconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Room;
import java.util.Locale;

public class RoomDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAddress, tvPrice, tvDeposit, tvAreaCap, tvElecWater, tvAmenities;
    private Button btnBookViewing;
    private RentConnectDbHelper dbHelper;
    private int roomId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        dbHelper = new RentConnectDbHelper(this);
        roomId = getIntent().getIntExtra("ROOM_ID", 1);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAddress = findViewById(R.id.tvDetailAddress);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvDeposit = findViewById(R.id.tvDetailDeposit);
        tvAreaCap = findViewById(R.id.tvDetailAreaCap);
        tvElecWater = findViewById(R.id.tvDetailElecWater);
        tvAmenities = findViewById(R.id.tvDetailAmenities);
        btnBookViewing = findViewById(R.id.btnBookViewing);

        Room room = dbHelper.getRoomById(roomId);
        if (room != null) {
            tvTitle.setText(room.getTitle());
            tvAddress.setText("Địa chỉ: " + room.getAddressCity() + ", " + room.getAddressDetail());
            tvPrice.setText(String.format(Locale.getDefault(), "Giá thuê: %,.0f VNĐ/tháng", room.getPrice()));
            tvDeposit.setText(String.format(Locale.getDefault(), "Tiền cọc yêu cầu: %,.0f VNĐ", room.getDeposit()));
            tvAreaCap.setText(String.format(Locale.getDefault(), "Diện tích: %.1f m² | Sức chứa: Tối đa %d người", room.getArea(), room.getCapacity()));
            tvElecWater.setText(String.format(Locale.getDefault(), "Đơn giá Điện: %,.0f đ/kWh | Nước: %,.0f đ/m³ | Dịch vụ: %,.0f đ/tháng", room.getElecPrice(), room.getWaterPrice(), room.getServiceFee()));
            tvAmenities.setText("Tiện ích kèm theo: " + room.getAmenities());
        }

        btnBookViewing.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("ROOM_ID", roomId);
            startActivity(intent);
        });
    }
}
