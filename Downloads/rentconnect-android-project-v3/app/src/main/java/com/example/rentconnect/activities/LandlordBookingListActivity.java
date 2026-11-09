
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
import java.util.List;

public class LandlordBookingListActivity extends AppCompatActivity {

    private RecyclerView rvLandlordBookings;
    private Button btnBack;
    private RentConnectDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_booking_list);

        dbHelper = new RentConnectDbHelper(this);

        rvLandlordBookings = findViewById(R.id.rvLandlordBookings);
        btnBack = findViewById(R.id.btnBackLandlordBookings);

        rvLandlordBookings.setLayoutManager(new LinearLayoutManager(this));
        loadBookings();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadBookings() {
        List<Booking> list = dbHelper.getAllBookings();
        rvLandlordBookings.setAdapter(new BookingAdapter(this, list, true));
        if (list.isEmpty()) {
            Toast.makeText(this, "Hiện chưa có lịch hẹn xem phòng nào!", Toast.LENGTH_SHORT).show();
        }
    }
}