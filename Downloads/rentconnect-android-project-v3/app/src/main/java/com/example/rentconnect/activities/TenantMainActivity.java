package com.example.rentconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.RoomAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Room;
import com.example.rentconnect.utils.SensorHelper;
import com.example.rentconnect.utils.SharedPrefManager;
import java.util.List;

public class TenantMainActivity extends AppCompatActivity {

    private TextView tvTenantHeader, tvSensorStatus;
    private RecyclerView rvRooms;
    private Button btnContract, btnUtilityInvoice, btnTicket, btnLogout, btnMyBookings;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;
    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);

        tvTenantHeader = findViewById(R.id.tvTenantHeader);
        tvSensorStatus = findViewById(R.id.tvSensorStatus);
        rvRooms = findViewById(R.id.rvTenantRooms);
        btnContract = findViewById(R.id.btnTenantContract);
        btnUtilityInvoice = findViewById(R.id.btnTenantUtilityInvoice);
        btnTicket = findViewById(R.id.btnTenantTicket);
        btnLogout = findViewById(R.id.btnTenantLogout);
        btnMyBookings = findViewById(R.id.btnMyBookings);

        tvTenantHeader.setText("Xin chào Người Thuê: " + prefManager.getUserName());

        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        loadRooms();

        btnContract.setOnClickListener(v -> startActivity(new Intent(this, TenantContractActivity.class)));
        btnUtilityInvoice.setOnClickListener(v -> startActivity(new Intent(this, TenantInvoiceActivity.class)));
        btnTicket.setOnClickListener(v -> startActivity(new Intent(this, TenantTicketActivity.class)));
        btnMyBookings.setOnClickListener(v -> {
            Intent intent = new Intent(TenantMainActivity.this, TenantBookingListActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        setupSensors();
    }

    private void loadRooms() {
        List<Room> roomList = dbHelper.getAllRooms();
        rvRooms.setAdapter(new RoomAdapter(this, roomList));
    }

    private void setupSensors() {
        sensorHelper = new SensorHelper(this);

        sensorHelper.setOnLightChangeListener((isDark, luxValue) -> {
            if (isDark) {
                tvSensorStatus.setText("🌙 Phòng tối (" + luxValue + " Lux) - Đã bật Dark Mode đọc hợp đồng");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                tvSensorStatus.setText("☀️ Môi trường sáng (" + luxValue + " Lux) - Chế độ Sáng");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        sensorHelper.setOnShakeListener(() -> {
            loadRooms();
            Toast.makeText(this, "📱 Đã lắc thiết bị! Đã làm mới danh sách phòng trọ.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, TenantTicketActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorHelper != null) sensorHelper.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorHelper != null) sensorHelper.unregister();
    }
}
