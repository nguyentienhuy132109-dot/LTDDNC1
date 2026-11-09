package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.BookingAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Booking;
import com.example.rentconnect.utils.SharedPrefManager;
import java.util.List;

public class TenantBookingListActivity extends AppCompatActivity {

    private RecyclerView rvTenantBookings;
    private Button btnBack;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_booking_list);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);

        rvTenantBookings = findViewById(R.id.rvTenantBookings);
        btnBack = findViewById(R.id.btnBackTenantBookings);

        rvTenantBookings.setLayoutManager(new LinearLayoutManager(this));
        loadBookings();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadBookings() {
        int tenantId = prefManager.getUserId();
        List<Booking> list = dbHelper.getBookingsByTenant(tenantId);
        rvTenantBookings.setAdapter(new BookingAdapter(this, list, false));
        if (list.isEmpty()) {
            Toast.makeText(this, "Bạn chưa có lịch hẹn xem phòng nào!", Toast.LENGTH_SHORT).show();
        }
    }
}